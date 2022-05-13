package com.calendar;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
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
        buttonLayout.setPrefSize(250,50);
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
        layout.setMargin(buttonLayout,new Insets(20,10,5,20));
        layout.setStyle("-fx-background-color: linear-gradient(to left, #26d07c, #FFFFFF) ;");

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

        Menu ioMenu = new Menu("Files");
        ioMenu.setStyle("-fx-background-color:  #808B96");
        MenuItem toCsvM = new MenuItem("Save data to csv");
        MenuItem fromCsvM  =new MenuItem("Load data from csv");
        MenuItem connectM = new MenuItem("Connect to database");

        ioMenu.getItems().addAll(toCsvM,fromCsvM,connectM);

        Menu exit = new Menu("Exit");
        MenuItem exitM = new MenuItem("Exit");
        exit.getItems().add(exitM);

        menuBar.getMenus().addAll(exit,ioMenu);

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
