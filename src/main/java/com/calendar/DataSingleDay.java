package com.calendar;
import java.util.HashMap;
import java.lang.StringBuilder;

public class DataSingleDay {
    private HashMap<Integer,String> todaysData;
    IndexControl indexControl;
    Integer nextIndex;
    public DataSingleDay(){
        todaysData = new HashMap();
        indexControl = new IndexControl();
        nextIndex = 1;
    }

    public String toString(){
        StringBuilder textToday = new StringBuilder();

        if(todaysData.isEmpty()){
            return "";
        }

        for(Integer index:todaysData.keySet()){
            textToday.append(todaysData.get(index) + "\n");
        }
        return textToday.toString();
    }

    public void addText(String text){
        todaysData.put(nextIndex,text);
        nextIndex++; //NEEDS A FUCTION TO AVOID DUPLICATES
    }

    public void removeText(String text){
        while(todaysData.values().remove(text));
    }

    public int getNoOfEntries(){
        return todaysData.size();
    }

    public HashMap<Integer,String> getTodaysData() {
        return todaysData;
    }
}
