package com.calendar;

import javafx.scene.Parent;
import java.time.LocalDate;
import java.util.HashMap;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollBar;
import javafx.geometry.Orientation;
import javafx.scene.layout.BorderPane;

public class DrawCalendar {

    private HashMap<LocalDate, DrawDay> daysOfTheMonth;
    private LocalDate thisMonth;
    private AllData allData;
    private DateLogic dateLogic;
    private LocalDate[] dateAry;
    private GridPane daysLayout;
    private BorderPane calBP;
    private ScrollBar vSB;
    public DrawCalendar(AllData allData,DateLogic dateLogic){
        this.daysOfTheMonth = new HashMap<>();
        this.allData = allData;
        this.dateLogic = dateLogic;
        daysLayout = new GridPane();
    }

    public Parent getView(){
        drawMonth();

        calBP = new BorderPane();
        calBP.setMaxSize(550,550);

        calBP.setCenter(daysLayout);

        vSB = new ScrollBar();
        vSB.setOrientation(Orientation.VERTICAL);
        calBP.setRight(vSB);

        return calBP;
    }

    public void createDrawDays(){
        daysOfTheMonth.clear();
        LocalDate dateToAdd = dateLogic.dateBuilderEndOfMonth(allData.getWorkingDate());

        for(int i = 1; i <= dateLogic.getNoDaysInMonth(allData.getWorkingDate()); i++){
            daysOfTheMonth.put(dateToAdd, new DrawDay(dateToAdd,allData,dateLogic));
            dateToAdd= dateToAdd.minusDays(1);
        }
    }

    private void putKeysIntoAry(){
        int index = 0;
        for(LocalDate date: daysOfTheMonth.keySet()){
            dateAry[index] = date;
            index++;
        }
    }

    public void drawMonth(){
        createDrawDays();
        dateAry = new LocalDate[daysOfTheMonth.size()];

        daysLayout.getChildren().clear();

        int row = 0;
        long column = dateLogic.getDayNoFromDate(dateLogic.dateBuilderFirstOfMonth(allData.getWorkingDate()));
        putKeysIntoAry();
        for(int i = (dateAry.length -1) ; i >= 0 ;i--){
            if(column > 6){
                column = 0;
                row++;
            }
            daysLayout.add(daysOfTheMonth.get(dateAry[i]).getView(), (int) column ,row);
            if(dateLogic.isDateWeekend(dateAry[i])){
                daysOfTheMonth.get(dateAry[i]).setWeekendBackgroundColour();
            }

            column++;
        }
        daysLayout.setStyle("-fx-background-color: linear-gradient(to top left, #26d07c, #A4ADA8);");
        daysLayout.setVgap(5);
        daysLayout.setHgap(5);
    }
}