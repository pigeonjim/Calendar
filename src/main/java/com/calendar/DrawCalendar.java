package com.calendar;


import java.time.LocalDate;
import java.util.HashMap;

public class DrawCalendar {
    //method to draw a calendar for one month
    //the month drawn will be the month of the date provided

    private HashMap<LocalDate, DrawDay> daysOfTheMonth;
    LocalDate thisMonth;
    DateLogic dLogic;
    public DrawCalendar(LocalDate date,DateLogic dLogic){
        this.daysOfTheMonth = new HashMap<>();
        this.thisMonth = date;
        this.dLogic = dLogic;
    }

   /* public Parent getView(){


    }*/

    public void createDrawDays(){
        daysOfTheMonth.clear(); //empty hashmap
        LocalDate dateToAdd = dLogic.dateBuilderFirstOfMonth(thisMonth.getMonthValue(), thisMonth.getYear());
        //create a DrawDay object for each day of the month and add to hashmap
        for(int i = 1; i <= dLogic.getNoDaysInMonth(thisMonth); i++){
            daysOfTheMonth.put(dateToAdd, new DrawDay());
            dateToAdd= dateToAdd.plusDays(1);
            System.out.println(dateToAdd.toString());
        }
        System.out.println(daysOfTheMonth.size());
        System.out.println(daysOfTheMonth.keySet().toString());

    }
}