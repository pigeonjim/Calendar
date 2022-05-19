package com.calendar;

import javafx.scene.control.CheckBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class DuplicateExport extends DuplicateAbstract{
    ArrayList<Integer> indexList;
    Integer oldIndex;
    String dBPath;
    public DuplicateExport(HashMap<LocalDate,String> thelist, ArrayList<Integer> indexList){
        super(thelist);
        this.indexList = indexList;
    }

    public DuplicateExport(LocalDate entryDate, String entry,
                           Integer oldIndex, ArrayList<Integer> indexList,String dbPath){
        super(entryDate,entry);
        this.indexList = indexList;
        this.oldIndex = oldIndex;
        this.dBPath = dbPath;
    }
    public void showPopup(DataAllDays data){
        super.showPopup(data);
        setLabelString("The following entries already exist in the database");
        setyesButtonString("Click to add selected entries to the database again");
    }
    public void yesButtonEvent(DataAllDays data) {
        for (CheckBox cb : cbAry) {
            if (cb.isSelected()) {
                Integer newIndex = data.duplicateNewIndexFinder(entryDate, indexList);
                indexList.add(newIndex);
                data.deleteDayData(entryDate, oldIndex);
                data.addNewDayData(entryDate, entry, newIndex);

                String accessURL = "jdbc:ucanaccess://" + dBPath;
                try (Connection connection = DriverManager.getConnection(accessURL)) {

                    String SQLQuery = "INSERT INTO Cal_Entries(Entry_ID, Entry_Date, Entry) VALUES(" + newIndex +
                            ",'" + entryDate + "',\"" + entry + "\")";
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(SQLQuery);

                } catch (Exception e) {
                    System.out.println("DupExport button: " + e.toString());
                }
            }
        }
    }


}
