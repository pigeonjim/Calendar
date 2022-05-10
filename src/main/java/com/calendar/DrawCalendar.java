package com.calendar;

import javafx.scene.Parent;
import java.time.LocalDate;
import java.util.HashMap;
import javafx.scene.layout.GridPane;


public class DrawCalendar {
    //method to draw a calendar for one month
    //the month drawn will be the month of the date provided

    private HashMap<LocalDate, DrawDay> daysOfTheMonth;
    LocalDate thisMonth;
    DateLogic dLogic;
    LocalDate[] dateAry;
    public DrawCalendar(LocalDate date,DateLogic dLogic){
        this.daysOfTheMonth = new HashMap<>();
        this.thisMonth = date;
        this.dLogic = dLogic;
    }

    public Parent getView(){

        GridPane daysLayout = new GridPane();
        createDrawDays();
        dateAry = new LocalDate[daysOfTheMonth.size()];

        int row = 0;
        long column = dLogic.getDayNoFromDate(dLogic.dateBuilderFirstOfMonth(thisMonth));
        putKeysIntoAry();
        for(int i = (dateAry.length -1) ; i >= 0 ;i--){
            if(column > 6){
                column = 0;
                row++;
            }
            daysLayout.add(daysOfTheMonth.get(dateAry[i]).getView(), (int) column ,row);
            if(dLogic.isDateWeekend(dateAry[i])){
                daysOfTheMonth.get(dateAry[i]).setWeekendBackgroundColour();
            }

            column++;
        }
        return daysLayout;

    }

    public void createDrawDays(){
        daysOfTheMonth.clear();
        LocalDate dateToAdd = dLogic.dateBuilderEndOfMonth(thisMonth);
        //create a DrawDay object for each day of the month and add to hashmap
        for(int i = 1; i <= dLogic.getNoDaysInMonth(thisMonth); i++){
            daysOfTheMonth.put(dateToAdd, new DrawDay((dLogic.getWeekDayName(dateToAdd)  + " " + dLogic.getFormattedDate(dateToAdd))));
            dateToAdd= dateToAdd.minusDays(1);
        }
    }

    private void putKeysIntoAry(){
        int index = 0;
        for(LocalDate date: daysOfTheMonth.keySet()){
            dateAry[index] = date;
            index++;
        }
    }
}