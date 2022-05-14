package com.calendar;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class DataSingleDay {
    private ArrayList<String> todaysData;
    public DataSingleDay(){
        todaysData = new ArrayList<>();
    }

    public String toString(){
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

    public int getNoOfEntries(){
        return todaysData.size();
    }

    public ArrayList<String> getTodaysData() {
        return todaysData;
    }
}
