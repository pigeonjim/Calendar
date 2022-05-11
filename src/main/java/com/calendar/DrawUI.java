package com.calendar;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.Parent;
import javafx.collections.ObservableList;
import java.text.DateFormatSymbols;
import java.time.LocalDate;

public class DrawUI {

    private ComboBox monthDD;
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
        monthDD =new ComboBox(months);

        layout = new HBox();
        layout.getChildren().add(monthDD);

        monthDD.setOnAction((event) -> {
            monthDDChange();
        });

        return layout;
    }

    public void monthDDChange(){
        String whatMonth = monthDD.getValue().toString();
        if(!whatMonth.isEmpty()){
            LocalDate newDate = this. dateLogic.buildDateFromStrings(monthDD.getValue().toString(),2022);
            allData.setWorkingDate(newDate);
            drawCalendar.drawMonth();
        }

    }

}
