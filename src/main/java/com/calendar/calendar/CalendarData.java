package com.calendar.calendar;
import java.time.LocalDate;
import java.util.HashMap;

public class CalendarData {
    private HashMap<LocalDate, DateData> userData;
    private DateLogic dLogic;
    public CalendarData(DateLogic dLogic){
        userData = new HashMap<>();
        this.dLogic = dLogic;
    }

    public void addNewDayData(LocalDate date, DateLogic dLogic){
        //adds a new dateDate object to the CalendarData hashmap
        //parameter is the day's date in LocalDate and the DateLogic object in use

        //first check date is not already userData
        if(userData.containsKey(date)){
            return;
        }
        //create a new object and add to userData
        userData.put(date, new DateData(date,dLogic));
    }

    public DateData getDataForADay(LocalDate date){
        return userData.get(date);
    }

    public void removeDayData(LocalDate date){
        userData.remove(date);
    }



}
