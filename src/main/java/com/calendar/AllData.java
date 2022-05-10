package com.calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class AllData {
    private HashMap<LocalDate, DayData> allData;
    private DateLogic dLogic;
    public AllData(DateLogic dLogic){
        allData = new HashMap<>();
        this.dLogic = dLogic;
    }

    public void addNewDayData(LocalDate date, String text){
        if (!allData.containsKey(date)) {
            allData.put(date, new DayData(dLogic));
        }
        allData.get(date).addText(text);
    }

    public void deleteDayData(LocalDate date, String text){
        if(allData.containsKey(date)){
            allData.get(date).removeText(text);
        }
    }

    public void deleteAllDataFromDay(LocalDate date){
        if(allData.containsKey(date)){
            allData.remove(date);
        }
    }


}
