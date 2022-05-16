package com.calendar;
import java.util.HashMap;
import java.lang.StringBuilder;
import java.util.Set;

public class DataSingleDay {
    private HashMap<Integer,String> todaysData;
    IndexControl indexControl;
    public DataSingleDay(){
        todaysData = new HashMap();
        indexControl = new IndexControl();
    }

    public String entriesToString(){
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
        if(todaysData.isEmpty()){
            todaysData.put(1,text);
        } else {
            todaysData.put(indexControl.findFirstNotInUse(todaysData.keySet()),text);
        }
    }

    public void removeText(String text){
        while(todaysData.values().remove(text));
    }

    public int getNoOfEntries(){
        return todaysData.size();
    }

    public boolean containsEntry(String text){
        if(todaysData.isEmpty()){
            return false;
        }
        for(Integer index: todaysData.keySet()){
            if(todaysData.get(index).equals(text)){
                return true;
            }
        }
        return false;
    }
    public String getAnEntry(Integer index){
        return todaysData.get(index);
    }
    public Set<Integer> getKeyset(){
        return todaysData.keySet();
    }
}
