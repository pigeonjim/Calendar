package com.calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.StageStyle;
import javafx.scene.control.CheckBox;

public class DrawDeletePopup {
    private String[] todaysData;
    private ArrayList<CheckBox> cbAry;
    private AllData allData;
    private LocalDate date;
    private VBox cbLayout;
    private BorderPane mainLayout;
    private Button allButton, selectedButton, closeButton;
    private HBox buttonLayout;

    public DrawDeletePopup(LocalDate date, AllData allData){
        this.allData = allData;
        this.date = date;
        cbAry = new ArrayList<>();
        cbLayout = new VBox();
        buttonLayout = new HBox();
    }

    public void showPopup(){
        if(allData.getNoDayEntries(date) == 0){
            return;
        }
        populateDataAry();
        setUpCheckBoxes();

        mainLayout = new BorderPane();
        allButton = new Button("Click to remove all entries from this date");
        selectedButton = new Button("Click to remove selected entries from this date");
        closeButton = new Button("Exit");
        buttonLayout.getChildren().addAll(allButton,selectedButton,closeButton);
        cbLayout.setPrefSize(750,400);
        mainLayout.setCenter(cbLayout);
        mainLayout.setTop(buttonLayout);
        mainLayout.setPrefSize(750,500);

        Scene popScene = new Scene(mainLayout);
        Stage popUp = new Stage();
        popUp.setScene(popScene);
        popUp.show();

        closeButton.setOnAction((event) -> {
            popUp.close();
        });

        selectedButton.setOnAction((event) -> {
            selectedButtonEvent();
        });
        allButton.setOnAction((event) -> {
            allButtonEvent();
        });

    }

    private void populateDataAry(){
        todaysData = new String[allData.getNoDayEntries(date)];
        todaysData = allData.getDayText(date).split("\n");
    }

    private void setUpCheckBoxes(){
        for(int cbIndex = 0; cbIndex < todaysData.length; cbIndex++){
            cbAry.add(new CheckBox(todaysData[cbIndex]));
        }
        for(CheckBox cb:cbAry){
            cbLayout.getChildren().add(cb);
        }
    }

    private void selectedButtonEvent(){
        for(CheckBox cb:cbAry){
            if(cb.isSelected()){
                allData.deleteDayData(date,cb.getText());
            }
        }
    }
    private void allButtonEvent(){
        allData.deleteAllDataFromDay(date);
    }
}
