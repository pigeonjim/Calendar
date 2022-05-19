package com.calendar;


import javafx.scene.control.CheckBox;
import java.time.LocalDate;
import java.util.HashMap;

public class DuplicateImport extends DuplicateAbstract {

    public DuplicateImport(HashMap<LocalDate, String> thelist) {
        super(thelist);
        setLabelString("The following entries already exist on the calendar");
        setyesButtonString("Click to add selected entries again");

    }
    public void showPopup(DataAllDays data){
        super.showPopup(data);
    }
    public void yesButtonEvent(DataAllDays data) {

            for (CheckBox cb : cbAry) {
                if (cb.isSelected()) {
                    String[] bits = cb.getText().split(" : ");
                    data.addNewDayData(LocalDate.parse(bits[0]), bits[1]);
                }
            }

    }
}
