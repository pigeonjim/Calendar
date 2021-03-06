package com.calendar;

import java.util.ArrayList;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;

public class PopupDeleteEntries {
    private String[] todaysData;
    private ArrayList<CheckBox> cbAry;
    private DataAllDays dataAllDays;
    private DrawDay drawDay;
    private VBox cbLayout;
    private Button allButton, selectedButton, closeButton;
    private HBox buttonLayout;
    private PopupDayInfo textPopup;
    private BlankStage blankStage;

    public PopupDeleteEntries(DataAllDays dataAllDays, DrawDay drawDay, PopupDayInfo textPopup){
        this.dataAllDays = dataAllDays;
        this.drawDay = drawDay;
        cbAry = new ArrayList<>();
        cbLayout = new VBox();
        buttonLayout = new HBox();
        this.textPopup  = textPopup;
        blankStage = new BlankStage();
    }

    public void showPopup(){
        if(dataAllDays.getNoDayEntries(drawDay.getDate()) == 0){
            return;
        }
        setUpCheckBoxes();
        allButton = new Button("Click to remove all entries from this date");
        selectedButton = new Button("Click to remove selected entries from this date");
        closeButton = new Button("Exit");
        buttonLayout.getChildren().addAll(allButton,selectedButton,closeButton);
        buttonLayout.setPrefWidth(750);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.setStyle("-fx-background-color: transparent");
        cbLayout.setPrefSize(750,400);
        blankStage.getPane().setTop(buttonLayout);
        blankStage.getPane().setCenter(cbLayout);
        blankStage.getPane().setPrefSize(750,500);

        cbLayout.setStyle("-fx-background-radius: 25;"+
                "-fx-border-style: solid inside;" +
                "-fx-border-radius: 25px;" +
                "-fx-border-width: 4;" +
                "-fx-border-color: black;" +
                "-fx-background-color: #FFFF;" +
                "-fx-padding: 15px;");
        blankStage.getPane().setStyle("-fx-background-color: transparent");

        formatButton(allButton);
        formatButton(selectedButton);
        closeButton.setPrefSize(100,40);
        closeButton.setStyle("-fx-background-color: #D6DBDF;" +
                "-fx-background-radius: 25;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-radius: 25px;" +
                "-fx-border-width: 4;" +
                "-fx-border-color: black;");

        blankStage.buildStage();

        closeButton.setOnAction((event) -> {
            blankStage.closeStage();
        });

        selectedButton.setOnAction((event) -> {
            selectedButtonEvent();
        });
        allButton.setOnAction((event) -> {
            allButtonEvent();
        });

    }

    private void populateDataAry(){
        todaysData = new String[dataAllDays.getNoDayEntries(drawDay.getDate())];
        todaysData = dataAllDays.getDayEntriesToString(drawDay.getDate()).split("\n");
    }

    private void setUpCheckBoxes(){
        populateDataAry();
        cbLayout.getChildren().clear();
        cbAry.clear();
        for(int cbIndex = 0; cbIndex < todaysData.length; cbIndex++){
            CheckBox cb = new CheckBox(todaysData[cbIndex]);
            cb.setPrefHeight(40);
            cb.setStyle("-fx-font-weight: bold;" +
                    "-fx-font-family: cursive;" +
                    "fx-font-size: 30;");
            cbAry.add(cb);
        }
        for(CheckBox cb:cbAry){
            cbLayout.getChildren().add(cb);
        }
    }

    private void selectedButtonEvent(){
        for(CheckBox cb:cbAry){
            if(cb.isSelected()){
                dataAllDays.deleteDayData(drawDay.getDate(),cb.getText());
                cb.setDisable(true);
            }
        }
        drawDay.setDayText();
        textPopup.setOutputBox();
    }
    private void allButtonEvent(){
        dataAllDays.deleteAllDataFromDay(drawDay.getDate());
        drawDay.setDayText();
        cbLayout.getChildren().clear();
        textPopup.setOutputBox();
    }

    private void formatButton(Button button){
        button.setPrefSize(350,40);
        button.setStyle("-fx-background-color: #D98880;" +
                "-fx-background-radius: 25;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-radius: 25px;" +
                "-fx-border-width: 4;" +
                "-fx-border-color: black;");
    }
}