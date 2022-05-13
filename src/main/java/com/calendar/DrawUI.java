package com.calendar;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.Parent;
import javafx.collections.ObservableList;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import javafx.application.Platform;

public class DrawUI {

    private ComboBox monthDD, yearDD;
    private MenuBar  menuBar;
    private HBox buttonLayout;
    private BorderPane layout;
    private DateLogic dateLogic;
    private DrawCalendar drawCalendar;
    private AllData allData;
    private DataIO dataIO;

    public DrawUI(DateLogic dLogic, DrawCalendar dCal, AllData allData, DataIO dataIO){
        this.dateLogic = dLogic;
        this.drawCalendar = dCal;
        this.allData = allData;
        this.dataIO = dataIO;
        menuBar = new MenuBar();
    }

    public Parent getView(){
        ObservableList<String> months = FXCollections.observableArrayList(new DateFormatSymbols().getMonths());
        ObservableList<String> years = FXCollections.observableArrayList("2021","2022","2023","2024","2025","2026");
        ObservableList<String> ioList = FXCollections.observableArrayList("Save to CSV", "Load from CSV","Connect to DB");
        monthDD =new ComboBox(months);
        yearDD = new ComboBox(years);
        buttonLayout = new HBox();
        buttonLayout.getChildren().addAll(monthDD,yearDD);
        buttonLayout.setSpacing(10);

        monthDD.getSelectionModel().select(allData.getWorkingDate().getMonthValue() - 1);
        yearDD.setValue(allData.getWorkingDate().getYear());

        monthDD.setOnAction((event) -> {
            dateDDChange();
        });
        yearDD.setOnAction((event) -> {
            dateDDChange();
        });

        setUpMenus();

        layout = new BorderPane();
        layout.setLeft(buttonLayout);
        layout.setTop(menuBar);
        layout.setMargin(buttonLayout,new Insets(20,10,10,20));
        layout.setStyle("-fx-background-color: linear-gradient(to left, #26d07c, #f5fffa) ;");

        return layout;
    }

    public void dateDDChange(){
        if(!monthDD.getValue().toString().isEmpty() && !yearDD.getValue().toString().isEmpty() ){
            LocalDate newDate = this. dateLogic.buildDateFromStringAndInt(monthDD.getValue().toString(),Integer.valueOf(yearDD.getValue().toString()));
            allData.setWorkingDate(newDate);
            drawCalendar.drawMonth();
        }
    }

    public void setUpMenus(){

        menuBar.setStyle("-fx-background-color: linear-gradient(from 0% 50% to 100% 100%,#A4AFB9, #778899, #2f4f4f);");
        Menu ioMenu = new Menu("Files");
        MenuItem toCsvM = new MenuItem("Save data to csv");
        MenuItem fromCsvM  =new MenuItem("Load data from csv");
        MenuItem connectM = new MenuItem("Connect to database");

        ioMenu.getItems().addAll(toCsvM,fromCsvM,connectM);

        Menu exit = new Menu("Exit");
        MenuItem exitM = new MenuItem("Exit");
        exit.getItems().add(exitM);
        ioMenu.setStyle("-fx-background-color: #AB7F7F;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-color: black;" +
                "-fx-font-size: 15;" +
                "-fx-font-family: monospace;");
        exit.setStyle("-fx-background-color:  #DEC705;"+
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-color: black;"+
                "-fx-font-size: 15;" +
                "-fx-font-family: monospace;");
        menuBar.getMenus().addAll(exit,ioMenu);
        menuBar.setPrefHeight(30);
        menuBar.setPadding(new Insets(10,15,10,15));

        Light.Distant light = new Light.Distant();
        light.setAzimuth(-120);
        Lighting lighting = new Lighting(light);
        lighting.setSurfaceScale(5);
        menuBar.setEffect(lighting);

       toCsvM.setOnAction((event) -> {
            dataIO.outputToCSV(dataIO.getFilePath(true));
        });
        fromCsvM.setOnAction((event) -> {
            dataIO.readFromCSV(dataIO.getFilePath(false));
            dateDDChange();
        });
        exitM.setOnAction((event) -> {
            Platform.exit();
        });

    }

}
