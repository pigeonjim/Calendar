package com.calendar;

import javafx.scene.control.CheckBox;
import java.sql.Connection;
import java.sql.*;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class DuplicateExport extends DuplicateAbstract{
    private ArrayList<Integer> indexList;
    private Integer oldIndex;
    private String dBPath;
    private DataAllDays dataAllDays;
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
    public void showPopup(DataAllDays data ){
        this.dataAllDays = data;
        setLabelString("The following entries already exist in the database");
        setyesButtonString("Click to add selected entries to the database again");
        super.showPopup(data);
        yesButton.setOnAction((event) -> {
            yesButtonEvent();
            blankStage.closeStage();
        });
    }
    public void yesButtonEvent() {
        for (CheckBox cb : cbAry) {
            if (cb.isSelected()) {
                Integer newIndex = newIndex();
                System.out.println("cb works");
                String accessURL = "jdbc:ucanaccess://" + dBPath;
                try (Connection connection = DriverManager.getConnection(accessURL)) {

                    String SQLQuery = "INSERT INTO Cal_Entries(Entry_ID, Entry_Date, Entry) VALUES(" + newIndex +
                            ", '" + entryDate + "', '" + entry + "')";
                    Statement statement = connection.createStatement();
                        statement.executeUpdate(SQLQuery);

                } catch (Exception e) {
                    System.out.println("DupExport button: " + e.toString());
                }
            }
        }
    }

    public Integer newIndex(){
        Integer newIndex = dataAllDays.duplicateNewIndexFinder(entryDate, indexList);
        indexList.add(newIndex);
        dataAllDays.deleteDayData(entryDate, oldIndex);
        dataAllDays.addNewDayData(entryDate, entry, newIndex);
        return newIndex;
    }

}
