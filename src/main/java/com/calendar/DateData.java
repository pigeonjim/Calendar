package com.calendar;

import java.time.LocalDate;
import java.util.ArrayList;

public class DateData {
    private LocalDate today;
    private ArrayList<String> notes;
    private String dayName;
    private DateLogic dayLogic;

    public DateData(LocalDate today, DateLogic dayLogic){
        this.notes = new ArrayList<>();
        this.dayLogic = dayLogic;
        this.dayName = dayLogic.getWeekDayName(today);
    }

    public String getDayName() {
        return dayName;
    }

    public ArrayList<String> getNotes() {
        return notes;
    }

    public LocalDate getToday() {
        return today;
    }

    public void setNotes(ArrayList<String> notes) {
        this.notes = notes;
    }
    public void addANote(String note){
        notes.add(note);
    }
    public void removeANote(String note){
        notes.remove(note);
    }

}
