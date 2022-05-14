package com.calendar;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.sql.*;
public class DataIO {

    DataAllDays dataAllDays;

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
                LocalDate date = LocalDate.parse(words[0]);
                dataAllDays.addNewDayData(date, words[1]);
            }
        } catch (Exception e) {
            System.out.println("error - " + e.toString());
        }
    }

    public String getFilePath(boolean loadOrSave) {
        //parameter is true for save file and false for load file
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: transparent;");
        Scene scene = new Scene(borderPane);
        scene.setFill(Color.TRANSPARENT);
        Stage fcStage = new Stage();
        fcStage.initStyle(StageStyle.TRANSPARENT);
        fcStage.setScene(scene);
        fcStage.show();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));

        if (loadOrSave) {
            fileChooser.setTitle("Please choose where to save the file");
            File file = fileChooser.showSaveDialog(fcStage);
            if (file == null) {
                return "";
            }
            return file.getPath();
        }
        fileChooser.setTitle("Please choose where to save the file");
        File file = fileChooser.showOpenDialog(fcStage);
        if (file == null) {
            return "";
        }
        return file.getPath();
    }

    public void getDataFromAccess() {
        String accessURL = "jdbc:ucanaccess://C:\\Users\\pigeo\\Documents\\CallendarApp.accdb";
        try (Connection connection = DriverManager.getConnection(accessURL)) {
            String SQLQuery = "Select * From Cal_Entries";
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(SQLQuery);
            while (results.next()) {
                System.out.println(results.getString("Entry"));
                dataAllDays.addNewDayData(results.getDate("Entry_Date").toLocalDate(), results.getString("Entry"));
            }
        } catch (Exception e) {
            System.out.println("Did not work. Error " + e.toString());
        }
    }
    public void saveDataToAccess() {
        String accessURL = "jdbc:ucanaccess://C:\\Users\\pigeo\\Documents\\CallendarApp.accdb";
        try (Connection connection = DriverManager.getConnection(accessURL)) {
            String SQLQuery = "INSERT INTO Cal_Entries(Entry_ID, Entry_Date, Entry) VALUES(?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(SQLQuery);) {
                for (LocalDate lDate : dataAllDays.getAllData().keySet()) {
                    for (Integer index : dataAllDays.getAllData().get(lDate).getTodaysData().keySet()) {

                        statement.setDate(2, Date.valueOf(lDate));
                        statement.setString(3, index.toString());
                        statement.executeUpdate();
                    }
                }
            } catch (Exception e) {
                System.out.println("Did not write. Error " + e);
                if(e instanceof SQLException){
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Connection error " + e);
        }
    }
}
