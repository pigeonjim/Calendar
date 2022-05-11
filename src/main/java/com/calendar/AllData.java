package com.calendar;

import java.time.LocalDate;
import java.util.HashMap;

public class AllData {
    private HashMap<LocalDate, DayData> allData;
    private LocalDate workingDate = LocalDate.now();
    public AllData(DateLogic dLogic){
        allData = new HashMap<>();
    }

    public AllData(){
        allData = new HashMap<>();
    }

    public void addNewDayData(LocalDate date, String text){
        if (!allData.containsKey(date)) {
            allData.put(date, new DayData());
        }
        allData.get(date).addText(text);
    }

    public void deleteDayData(LocalDate date, String text){
        if(allData.containsKey(date)){
            allData.get(date).removeText(text);
        }
    }

    public void deleteAllDataFromDay(LocalDate date){
            allData.remove(date);
    }

    public LocalDate getWorkingDate() {
        return workingDate;
    }
    public void setWorkingDate(LocalDate date){
        this.workingDate = date;
    }

}
