package com.calendar;

import javafx.scene.Parent;
import java.time.LocalDate;
import java.util.HashMap;
import javafx.scene.layout.GridPane;


public class DrawCalendar {

    private HashMap<LocalDate, DrawDay> daysOfTheMonth;
    private LocalDate thisMonth;
    private DateLogic dLogic;
    private LocalDate[] dateAry;
    private GridPane daysLayout;
    public DrawCalendar(LocalDate date,DateLogic dLogic){
        this.daysOfTheMonth = new HashMap<>();
        this.thisMonth = date;
        this.dLogic = dLogic;
        daysLayout = new GridPane();
    }

    public Parent getView(){

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

    public void changeMonth(){

    }
}