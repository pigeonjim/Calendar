package com.calendar;

import javafx.scene.control.CheckBox;

import java.time.LocalDate;
import java.util.HashMap;

public class DuplicateExport extends DuplicateAbstract{

    public DuplicateExport(HashMap<LocalDate,String> thelist, DrawCalendar drawCalendar){
        super(thelist,drawCalendar);
    }
    public void showPopup(DataAllDays data){
        super.showPopup(data);
        setLabelString("The following entries already exist in the database");
        setyesButtonString("Click to add selected entries to the database again");
    }
    public void yesButtonEvent(DataAllDays data) {

        for (CheckBox cb : cbAry) {
            if (cb.isSelected()) {

            }
        }

    }
}
