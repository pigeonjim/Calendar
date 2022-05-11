package com.calendar;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.Parent;
import javafx.collections.ObservableList;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import javafx.application.Platform;
import javafx.scene.text.Font;
import javafx.scene.effect.DropShadow;

public class DrawUI {

    private ComboBox monthDD, yearDD;
    private HBox buttonLayout;
    private BorderPane layout;
    private DateLogic dateLogic;
    private DrawCalendar drawCalendar;
    private AllData allData;
    private Button exit;


    public DrawUI(DateLogic dLogic, DrawCalendar dCal, AllData allData){
        this.dateLogic = dLogic;
        this.drawCalendar = dCal;
        this.allData = allData;
        exit = new Button("Exit");
    }

    public Parent getView(){
        ObservableList<String> months = FXCollections.observableArrayList(new DateFormatSymbols().getMonths());
        ObservableList<String> years = FXCollections.observableArrayList("2021","2022","2023","2024","2025","2026");
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

        layout = new BorderPane();
        layout.setLeft(buttonLayout);
        layout.setRight(exit);
        layout.setMargin(exit, new Insets(10,10,5,10));

        exit.setPrefSize(100,20);
        exit.setStyle("-fx-background-color: #ECE146;" +
                "-fx-background-radius: 25;");
        exit.setFont(Font.font("SansSerif", 18));
        exit.setEffect(new DropShadow());
        layout.setMargin(buttonLayout,new Insets(10,10,5,10));


        exit.setOnAction((event) -> {
            Platform.exit();
        } );

        return layout;
    }

    public void dateDDChange(){
        if(!monthDD.getValue().toString().isEmpty() && !yearDD.getValue().toString().isEmpty() ){
            LocalDate newDate = this. dateLogic.buildDateFromStrings(monthDD.getValue().toString(),Integer.valueOf(yearDD.getValue().toString()));
            allData.setWorkingDate(newDate);
            drawCalendar.drawMonth();
        }
    }

}
