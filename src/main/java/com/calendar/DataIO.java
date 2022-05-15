package com.calendar;

import javafx.stage.FileChooser;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.sql.*;

public class DataIO {

    DataAllDays dataAllDays;
    BlankStage blankStage;

    public DataIO(DataAllDays dataAllDays) {
        this.dataAllDays = dataAllDays;
    }

    public void outputToCSV(String path) {
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

    public void readFromCSV(String path) {
        try (Scanner lineIn = new Scanner(Paths.get(path))) {
            while (lineIn.hasNextLine()) {
                String row = lineIn.nextLine();
                String[] words = row.split(",");

                Integer index = Integer.valueOf(words[0]);
                LocalDate date = LocalDate.parse(words[1]);
                String text = words[2];
                dataAllDays.importDayEntry(index,date, text);
            }

        } catch (Exception e) {
            System.out.println("error - " + e.toString());
        }
    }

    public String getFilePath(boolean loadOrSave) {
        //parameter is true for save file and false for load file
        blankStage = new BlankStage();
        blankStage.getPane().setStyle("-fx-background-color: transparent;");
        blankStage.showStage();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));

        if (loadOrSave) {
            fileChooser.setTitle("Please choose where to save the file");
            File file = fileChooser.showSaveDialog(blankStage.getWindow());
            if (file == null) {
                return "";
            }
            return file.getPath();
        }
        fileChooser.setTitle("Please choose where to save the file");
        File file = fileChooser.showOpenDialog(blankStage.getWindow());
        if (file == null) {
            return "";
        }
        blankStage.closeStage();
        return file.getPath();
    }

   public void getAllAccess() {
        String accessURL = "jdbc:ucanaccess://src/main/resources/com/calendar/CallendarApp.accdb";
        try (Connection connection = DriverManager.getConnection(accessURL)) {
            String SQLQuery = "Select * From Cal_Entries";
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(SQLQuery);
            while (results.next()) {
                LocalDate date = results.getDate("Entry_Date").toLocalDate();
                Integer index = results.getInt("Entry_ID");
                String entry = results.getString("Entry");

                dataAllDays.importDayEntry(index,date, entry);
            }
        } catch (Exception e) {
            System.out.println("Did not work. Error " + e.toString());
        }
    }
    public void saveAllAccess() {
        String accessURL = "jdbc:ucanaccess://src/main/resources/com/calendar/CallendarApp.accdb";
        try (Connection connection = DriverManager.getConnection(accessURL)) {
            //String SQLQuery = "INSERT INTO Cal_Entries(Entry_ID, Entry_Date, Entry) VALUES(?,?,?)";
            String SQLQuery = "INSERT INTO Cal_Entries SELECT * FROM Cal_Entries " +
                    "WHERE NOT EXISTS (SELECT * FROM Cal_Entries AS bd WHERE bd.Entry_ID = ? AND bd.Entry_Date = ? AND " +
                    "bd.Entry = ?)";
            try (PreparedStatement statement = connection.prepareStatement(SQLQuery);) {
                for (LocalDate lDate : dataAllDays.getAllData().keySet()) {
                    for (Integer index : dataAllDays.getAllData().get(lDate).getTodaysData().keySet()) {
                        statement.setInt(1,index);
                        statement.setDate(2, Date.valueOf(lDate));
                        statement.setString(3,
                                dataAllDays.getAllData().get(lDate).getTodaysData().get(index));
                        statement.executeUpdate();
                    }
                }
            } catch (Exception e) {
                System.out.println("Did not write. Error " + e);
            }
        } catch (Exception e) {
            System.out.println("Connection error " + e);
        }
    }

}
