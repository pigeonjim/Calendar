package com.calendar;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class AllData {
    private HashMap<LocalDate, DayData> allData;
    private LocalDate workingDate = LocalDate.now();
    private GraphicsDevice gfxDevice;
    private int screenHeight, screenWidth;

    public AllData() {
        allData = new HashMap<>();
        gfxDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        screenHeight = gfxDevice.getDisplayMode().getHeight();
        screenWidth = gfxDevice.getDisplayMode().getWidth();
    }

    public void addNewDayData(LocalDate date, String text) {
        if (!allData.containsKey(date)) {
            allData.put(date, new DayData());
        }
        allData.get(date).addText(text);
    }

    public void deleteDayData(LocalDate date, String text) {
        if (allData.containsKey(date)) {
            allData.get(date).removeText(text);
        }
    }

    public String getDayText(LocalDate date) {
        if (allData.containsKey(date)) {
            return this.allData.get(date).toString();
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
            for (String entry : allData.get(day).getTodaysData()) {
                allDataCsv.add(day.toString() + "," + entry + "\n");
            }
        }
        return allDataCsv;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public HashMap<LocalDate, DayData> getAllData() {
        return allData;
    }
}

