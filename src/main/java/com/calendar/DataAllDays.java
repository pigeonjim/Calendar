package com.calendar;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class DataAllDays {
    private HashMap<LocalDate, DataSingleDay> allData;
    private LocalDate workingDate = LocalDate.now();
    private GraphicsDevice gfxDevice;
    private int screenHeight, screenWidth;

    public DataAllDays() {
        allData = new HashMap<>();
        gfxDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        screenHeight = gfxDevice.getDisplayMode().getHeight();
        screenWidth = gfxDevice.getDisplayMode().getWidth();
    }

    public void addNewDayData(LocalDate date, String text) {
        if (!allData.containsKey(date)) {
            allData.put(date, new DataSingleDay());
        }
        allData.get(date).addText(text);
    }

    public void updateDayEntry(Integer index, String text, LocalDate date){
        allData.get(date).getTodaysData().put(index,text);
    }

    public void deleteDayData(LocalDate date, String text) {
        if (allData.containsKey(date)) {
            allData.get(date).removeText(text);
        }
    }

    public String getDayText(LocalDate date) {
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
            for (Integer index : allData.get(day).getTodaysData().keySet()) {
                allDataCsv.add(index + "," + day.toString() + "," + allData.get(day).getTodaysData().get(index) + "\n");
            }
        }
        return allDataCsv;
    }

    public int getScreenHeight() {
        return screenHeight;
    }//THIS DOESNT BELONG HERE
    public HashMap<LocalDate, DataSingleDay> getAllData() {
        return allData;
    }

    public HashMap<Integer,String> getDayData(LocalDate date){
        return allData.get(date).getTodaysData();
    }


}

