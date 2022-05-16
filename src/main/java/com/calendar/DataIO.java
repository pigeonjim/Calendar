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

public class DataIO {

    private DataAllDays dataAllDays;
    private BlankStage blankStage;
    private DrawCalendar drawCalendar;

    public DataIO(DataAllDays dataAllDays, DrawCalendar drawCalendar) {
        this.dataAllDays = dataAllDays;
        this.drawCalendar = drawCalendar;
    }

    public void outputToCSV() {
        String path =getFilePath(true);
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
        String path =getFilePath(false);

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
                DrawPopupDuplicateEntry drawPopupDuplicateEntry = new DrawPopupDuplicateEntry(duplicates, drawCalendar);
                drawPopupDuplicateEntry.showPopup(dataAllDays);
            }

        } catch (Exception e) {
            System.out.println("error - " + e.toString());
        }
    }

    public String getFilePath(boolean loadOrSave) {
        //parameter is true for save file and false for load file
        blankStage = new BlankStage();
        blankStage.getPane().setStyle("-fx-background-color: transparent;");
        blankStage.buildStage();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));

        if (loadOrSave) {
            fileChooser.setTitle("Please choose where to save the file");
            File file = fileChooser.showSaveDialog(blankStage.getWindow());
            if (file == null) {
                return "";
            }
            blankStage.closeStage();
            return file.getPath();
        }
        fileChooser.setTitle("Please choose which file to import");
        File file = fileChooser.showOpenDialog(blankStage.getWindow());
        if (file == null) {
            return "";
        }
        blankStage.closeStage();
        return file.getPath();
    }

   public void getAllAccess() {
       HashMap<LocalDate,String> duplicates = new HashMap<>();
        String accessURL = "jdbc:ucanaccess://src/main/resources/com/calendar/CallendarApp.accdb";
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
                DrawPopupDuplicateEntry drawPopupDuplicateEntry = new DrawPopupDuplicateEntry(duplicates, drawCalendar);
                drawPopupDuplicateEntry.showPopup(dataAllDays);
                }
        } catch (Exception e) {
            System.out.println("Did not work. Error " + e.toString());
        }
    }
    public void saveAllAccess() {
        String accessURL = "jdbc:ucanaccess://src/main/resources/com/calendar/CallendarApp.accdb";
        try (Connection connection = DriverManager.getConnection(accessURL)) {
            String SQLQuery = "INSERT INTO Cal_Entries(Entry_ID, Entry_Date, Entry) VALUES(?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(SQLQuery);) {
                for (LocalDate lDate : dataAllDays.getAllData().keySet()) {
                    for (Integer index : dataAllDays.getAllData().get(lDate).getKeyset()) {

                        if (!checkIfRowExists(index, lDate,dataAllDays.getAllData().get(lDate).getAnEntry(index))) {
                            statement.setInt(1, index);
                            statement.setDate(2, Date.valueOf(lDate));
                            statement.setString(3,
                                    dataAllDays.getAllData().get(lDate).getAnEntry(index));
                            statement.executeUpdate();
                            System.out.println("attempted update " + lDate + dataAllDays.getAllData().get(lDate).getAnEntry(index));
                        } else {
                            System.out.println("Exists");
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
        String accessURL = "jdbc:ucanaccess://src/main/resources/com/calendar/CallendarApp.accdb";
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

}