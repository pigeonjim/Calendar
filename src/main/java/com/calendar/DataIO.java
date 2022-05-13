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

    AllData allData;

    public DataIO(AllData allData){
        this.allData = allData;
    }

        public void outputToCSV(String path){
        try{
            PrintWriter csvWriter = new PrintWriter(path);
            for(String entry: allData.allDataInCSV()){
                csvWriter.print(entry);
            }
            csvWriter.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void readFromCSV(String path){
       try(Scanner lineIn = new Scanner(Paths.get(path))){
            while(lineIn.hasNextLine()){
                String row = lineIn.nextLine();
                String[] words = row.split(",");
                LocalDate date = LocalDate.parse(words[0]);
                allData.addNewDayData(date, words[1]);
        }
       } catch(Exception e){
           System.out.println("error - " + e.toString());
        }
    }
    public String getFilePath(boolean loadOrSave){
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
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV","*.csv"));

        if(loadOrSave){
            fileChooser.setTitle("Please choose where to save the file");
            File file = fileChooser.showSaveDialog(fcStage);
            if(file == null){
                return"";
            }
            return file.getPath();
        }
        fileChooser.setTitle("Please choose where to save the file");
        File file = fileChooser.showOpenDialog(fcStage);
        if(file == null){
            return"";
        }
        return file.getPath();
    }

    public void getDataFromAccess(){
        String accessURL = "jdbc:ucanaccess://C:\\Users\\pigeo\\Documents\\CallendarApp.accdb";
        try(Connection connection = DriverManager.getConnection(accessURL) ){

            String SQLQuery = "Select * From Cal_Entries";
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(SQLQuery);
            while(results.next()){
                System.out.println(results.getString("Entry"));
            }

        } catch (Exception e){
            System.out.println("Did not work. Error " + e.toString());
        }
    }

    public void saveDataToAccess(){
        String accessURL = "jdbc:ucanaccess://C:\\Users\\pigeo\\Documents\\CallendarApp.accdb";
        try(Connection connection = DriverManager.getConnection(accessURL) ){

            String SQLQuery = "INSERT INTO Cal_Entries(Entry_Date, Entry)";
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(SQLQuery);
            while(results.next()){
                System.out.println(results.getString("Entry"));
            }

        } catch (Exception e){
            System.out.println("Did not work. Error " + e.toString());
        }
    }
}
