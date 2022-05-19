package com.calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class DataAllDays {
    private HashMap<LocalDate, DataSingleDay> allData;
    private LocalDate workingDate = LocalDate.now();
    private IndexControl indexControl;
    private String dBPath;
    private boolean usingDB;

    public DataAllDays() {
        allData = new HashMap<>();
        indexControl = new IndexControl();
        this.dBPath = "";
        this.usingDB = false;
    }

    public void addNewDayData(LocalDate date, String text) {
        if (!allData.containsKey(date)) {
            allData.put(date, new DataSingleDay());
        }
        allData.get(date).addText(text);
    }

    public void addNewDayData(LocalDate date, String text, Integer index) {
        if (!allData.containsKey(date)) {
            allData.put(date, new DataSingleDay());
        }
        allData.get(date).addText(index, text);
    }

    public void deleteDayData(LocalDate date, String text) {
        if (allData.containsKey(date)) {
            allData.get(date).removeText(text);
        }
    }
    public void deleteDayData(LocalDate date, Integer index){
        if (allData.containsKey(date)) {
            allData.get(date).removeText(index);
        }
    }

    public String getDayEntriesToString(LocalDate date) {
        if (allData.containsKey(date)) {
            return this.allData.get(date).entriesToString();
        }
        return "";
    }

    public void deleteAllDataFromDay(LocalDate date) {
        allData.remove(date);
    }

    public LocalDate getWorkingDate() {
        return workingDate;
    }

    public void setWorkingDate(LocalDate date) {
        this.workingDate = date;
    }

    public int getNoDayEntries(LocalDate date) {
        if (allData.containsKey(date)) {
            return allData.get(date).getNoOfEntries();
        }
        return 0;
    }

    public ArrayList<String> allDataInCSV() {
        ArrayList<String> allDataCsv = new ArrayList<>();
        for (LocalDate day : allData.keySet()) {
            for (Integer index : allData.get(day).getKeyset()) {
                allDataCsv.add(index + "," + day.toString() + "," + allData.get(day).getAnEntry(index) + "\n");
            }
        }
        return allDataCsv;
    }

    public HashMap<LocalDate, DataSingleDay> getAllData() {
        return allData;
    }

    public boolean importDayEntry(Integer index, LocalDate date, String entry){
        //returns true if date already contains this entry with any key index value in hashmap
        if(index == null){
            System.out.println("Index null");
        }
        if(allData.containsKey(date)){
            if(allData.get(date).containsEntry(entry)){
                return true;
            } else {
                allData.get(date).addText(entry);
            }
        } else {
            addNewDayData(date,entry);
        }
        return false;
    }
    public Integer duplicateNewIndexFinder(LocalDate date, ArrayList<Integer> indexList){
        return allData.get(date).duplicateNewIndexFinder(indexList);
    }

    public void setdBPath(String dBPath) {
        this.dBPath = dBPath;
    }

    public String getdBPath() {
        return dBPath;
    }

    public void setUsingDB(boolean usingDB) {
        this.usingDB = usingDB;
    }

    public boolean isUsingDB() {
        return usingDB;
    }
}