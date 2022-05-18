package com.calendar;

import javafx.stage.FileChooser;
import java.io.File;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.sql.*;

public class IOFunctions {

    private DataAllDays dataAllDays;
    private BlankStage blankStage;
    private DrawCalendar drawCalendar;
    private boolean usingDB;
    private String dBPath;

    public IOFunctions(DataAllDays dataAllDays, DrawCalendar drawCalendar) {
        this.dataAllDays = dataAllDays;
        this.drawCalendar = drawCalendar;
        this.usingDB = false;
        this.dBPath = null;
    }

    public void outputToCSV() {
        String path = getFilePath(true,false);
        try {
            PrintWriter csvWriter = new PrintWriter(path);
            for (String entry : dataAllDays.allDataInCSV()) {
                csvWriter.print(entry);
            }
            csvWriter.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void readFromCSV() {
        String path = getFilePath(false,false);

        HashMap<LocalDate,String> duplicates = new HashMap<>();
        try (Scanner lineIn = new Scanner(Paths.get(path))) {
            while (lineIn.hasNextLine()) {
                String row = lineIn.nextLine();
                String[] words = row.split(",");
                Integer index = Integer.valueOf(words[0]);
                words[1] = words[1].replace("/" , "-");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date = LocalDate.parse(words[1],formatter);
                String text = words[2];
                if(dataAllDays.importDayEntry(index,date, text)){
                    duplicates.put(date,text);
                } else {
                    dataAllDays.importDayEntry(index,date, text);
                }
            }
            if(!duplicates.isEmpty()){
                PopupDuplicateImport popupDuplicateImport = new PopupDuplicateImport(duplicates, drawCalendar);
                popupDuplicateImport.showPopup(dataAllDays);
            }

        } catch (Exception e) {
            System.out.println("error - " + e.toString());
        }
    }

    public String getFilePath(boolean loadOrSave, boolean DB) {
        //parameter is true for save file and false for load file
        String path;
        blankStage = new BlankStage();
        blankStage.getPane().setStyle("-fx-background-color: transparent;");
        blankStage.buildStage();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        if(DB){
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MS Access", "*.accdb"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MS Access pre 2007", "*.mdb"));
        } else {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        }
        if (loadOrSave) {
            if (DB) {
                fileChooser.setTitle("Please choose which database to save to");
            } else{
                fileChooser.setTitle("Please choose where to save the file");
        }
            File file = fileChooser.showSaveDialog(blankStage.getWindow().getScene().getWindow());

            if (file == null) {
                blankStage.closeStage();
                return "";
            } else {
                path = file.getPath();
            }
            blankStage.closeStage();
            return path;
        }
        if(DB){
            fileChooser.setTitle("Please choose which database to load from");
        } else{
            fileChooser.setTitle("Please choose which file to import");
        }
        File file = fileChooser.showOpenDialog(blankStage.getWindow().getScene().getWindow());
        if (file == null) {
            blankStage.closeStage();
            return "";
        } else {
            path = file.getPath();
        }
        blankStage.closeStage();
        return path;
    }


   public void getAllAccess() {

       HashMap<LocalDate,String> duplicates = new HashMap<>();
        String accessURL = "jdbc:ucanaccess://" + dBPath;
        try (Connection connection = DriverManager.getConnection(accessURL)) {
            String SQLQuery = "Select * From Cal_Entries";
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(SQLQuery);
            while (results.next()) {
                LocalDate date = results.getDate("Entry_Date").toLocalDate();
                Integer index = results.getInt("Entry_ID");
                String entry = results.getString("Entry");

                if(dataAllDays.importDayEntry(index,date, entry)){
                    duplicates.put(date,entry);
                } else {
                    dataAllDays.importDayEntry(index,date, entry);
                }
            }
            if(!duplicates.isEmpty()){
                PopupDuplicateImport popupDuplicateImport = new PopupDuplicateImport(duplicates, drawCalendar);
                popupDuplicateImport.showPopup(dataAllDays);
                }
        } catch (Exception e) {
            System.out.println("Did not work. Error " + e.toString());
        }
    }
    public void saveAllAccess() {
        String entry;
        String accessURL = "jdbc:ucanaccess://" + dBPath;
        try (Connection connection = DriverManager.getConnection(accessURL)) {
            String SQLQuery = "INSERT INTO Cal_Entries(Entry_ID, Entry_Date, Entry) VALUES(?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(SQLQuery);) {
                for (LocalDate lDate : dataAllDays.getAllData().keySet()) {
                    for (Integer index : dataAllDays.getAllData().get(lDate).getKeyset()) {
                        entry = dataAllDays.getAllData().get(lDate).getAnEntry(index);
                        if (!checkIfRowExists(index, lDate, entry)) {
                            statement.setInt(1, index);
                            statement.setDate(2, Date.valueOf(lDate));
                            statement.setString(3, entry);
                            statement.executeUpdate();
                            System.out.println("attempted update " + lDate + dataAllDays.getAllData().get(lDate).getAnEntry(index));
                        } else {
                            if(checkIfPairExists(lDate, entry)){
                                //duplicate
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Did not write. Error " + e);
            }
        } catch (Exception e) {
            System.out.println("Connection error " + e);
        }
    }

    public boolean checkIfRowExists(Integer index, LocalDate date, String entry){
    //function that returns true if a row exists in the access DB

        String accessURL = "jdbc:ucanaccess://" + dBPath;
        try (Connection connection = DriverManager.getConnection(accessURL)) {
            String SQLQuery = "SELECT TOP 1 IIF((Select SUM([Entry_ID]) FROM Cal_Entries " +
                    "WHERE [Entry_ID] = "+ index + " AND [Entry] =  \"" +
                     entry + "\" AND Entry_Date = '" + Date.valueOf(date) + "') > 0, 1, 2) AS [Check] FROM Cal_Entries";
            Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(SQLQuery);
                        while (results.next()) {
                            int check = results.getInt("Check");
                            if (check == 1) {
                                return true;
                            } else {
                                return false;
                            }
                        }
        } catch (Exception e) {
            System.out.println("connection did not work. Error " + e);
        }
        return false;
    }

    public boolean checkIfPairExists(LocalDate date, String entry){
        //function that returns true if a date/entry pair exists in the access DB
        String accessURL = "jdbc:ucanaccess://" + dBPath;
        try (Connection connection = DriverManager.getConnection(accessURL)) {
            String SQLQuery = "SELECT TOP 1 IIF((Select SUM([Entry_ID]) FROM Cal_Entries " +
                    " [Entry] =  \"" + entry + "\" AND Entry_Date = '" + Date.valueOf(date) + "') > 0, 1, 2) AS [Check] FROM Cal_Entries";
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(SQLQuery);
            while (results.next()) {
                int check = results.getInt("Check");
                if (check == 1) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println("connection did not work. Error " + e);
        }
        return false;
    }
    public boolean getUsingDB(){
        return usingDB;
    }
    public void setUsingDB(boolean answer){
        this.usingDB = answer;
    }

    public void connectToDB(){
        dBPath = getFilePath(false, true);
        if(dBPath.isEmpty()){
            System.out.println("Unable to connect");
            return;
        }
        setUsingDB(true);
        getAllAccess();
    }
    public void disconnectFromDB(){
        setUsingDB(false);
        saveAllAccess();
        dBPath = "";
    }

    public void updateDBOneRow(Integer index, LocalDate date, String entry){
        String accessURL = "jdbc:ucanaccess://" + dBPath;
        try (Connection connection = DriverManager.getConnection(accessURL)) {

                        if (!checkIfRowExists(index, date, entry)) {
                            String SQLQuery = "INSERT INTO Cal_Entries(Entry_ID, Entry_Date, Entry) VALUES(" + index +
                                    ",'" + date +"',\"" + entry +"\")";
                        } else {
                            if(checkIfPairExists(date, entry)){
                                //duplicate

                            }
                        }
        } catch (Exception e) {
            System.out.println("Connection error " + e);
        }
    }
}