package com.calendar;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class DayData {
    private ArrayList<String> todaysData;
    private DateLogic dLogic;
    public DayData(DateLogic dLogic){
        todaysData = new ArrayList<>();
        this.dLogic = dLogic;
    }

    public String getTodaysData(){
        StringBuilder textToday = new StringBuilder();

        if(todaysData.isEmpty()){
            textToday.append(" ");
            return textToday.toString();
        }

        for(String note:todaysData){
            textToday.append(note + "\n");
        }
        return textToday.toString();
    }

    public void addText(String text){
        todaysData.add(text);
    }

    public void removeText(String text){
        todaysData.remove(text);
    }




}
