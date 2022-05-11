package com.calendar;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.Parent;
import javafx.collections.ObservableList;
import java.text.DateFormatSymbols;
import java.time.LocalDate;

public class DrawUI {

    private ComboBox monthDD, yearDD;
    private HBox layout;
    private DateLogic dateLogic;
    private DrawCalendar drawCalendar;
    private AllData allData;

    public DrawUI(DateLogic dLogic, DrawCalendar dCal, AllData allData){
        this.dateLogic = dLogic;
        this.drawCalendar = dCal;
        this.allData = allData;
    }

    public Parent getView(){
        ObservableList<String> months = FXCollections.observableArrayList(new DateFormatSymbols().getMonths());
        ObservableList<String> years = FXCollections.observableArrayList("2021","2022","2023","2024","2025","2026");
        monthDD =new ComboBox(months);
        yearDD = new ComboBox(years);
        layout = new HBox();
        layout.getChildren().addAll(monthDD,yearDD);

        monthDD.getSelectionModel().select(allData.getWorkingDate().getMonthValue() - 1);
        yearDD.setValue(allData.getWorkingDate().getYear());

        monthDD.setOnAction((event) -> {
            dateDDChange();
        });
        yearDD.setOnAction((event) -> {
            dateDDChange();
        });

        layout.setStyle( "-fx-border-width: 2;" +
                "-fx-border-insets: 10;" +
                "-fx-border-color: black;");

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
