package com.calendar;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.stage.StageStyle;
import javafx.scene.control.CheckBox;

public class DrawDeletePopup {
    private String[] todaysData;
    private ArrayList<CheckBox> cbAry;
    private DataAllDays dataAllDays;
    private DrawDay drawDay;
    private VBox cbLayout;
    private BorderPane mainLayout;
    private Button allButton, selectedButton, closeButton;
    private HBox buttonLayout;
    private DrawTextPopup textPopup;

    public DrawDeletePopup(DataAllDays dataAllDays, DrawDay drawDay, DrawTextPopup textPopup){
        this.dataAllDays = dataAllDays;
        this.drawDay = drawDay;
        cbAry = new ArrayList<>();
        cbLayout = new VBox();
        buttonLayout = new HBox();
        this.textPopup  = textPopup;
    }

    public void showPopup(){
        if(dataAllDays.getNoDayEntries(drawDay.getDate()) == 0){
            return;
        }
        setUpCheckBoxes();

        mainLayout = new BorderPane();
        allButton = new Button("Click to remove all entries from this date");
        selectedButton = new Button("Click to remove selected entries from this date");
        closeButton = new Button("Exit");
        buttonLayout.getChildren().addAll(allButton,selectedButton,closeButton);
        buttonLayout.setPrefWidth(750);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.setStyle("-fx-background-color: transparent");
        cbLayout.setPrefSize(750,400);
        mainLayout.setTop(buttonLayout);
        mainLayout.setCenter(cbLayout);
        mainLayout.setPrefSize(750,500);

        cbLayout.setStyle("-fx-background-radius: 25;"+
                "-fx-border-style: solid inside;" +
                "-fx-border-radius: 25px;" +
                "-fx-border-width: 4;" +
                "-fx-border-color: black;" +
                "-fx-background-color: #FFFF;" +
                "-fx-padding: 15px;");
        mainLayout.setStyle("-fx-background-color: transparent");

        formatButton(allButton);
        formatButton(selectedButton);
        closeButton.setPrefSize(100,40);
        closeButton.setStyle("-fx-background-color: #D6DBDF;" +
                "-fx-background-radius: 25;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-radius: 25px;" +
                "-fx-border-width: 4;" +
                "-fx-border-color: black;");

        Scene popScene = new Scene(mainLayout);
        Stage popUp = new Stage();

        popScene.setFill(Color.TRANSPARENT);
        popUp.initStyle(StageStyle.TRANSPARENT);

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
        todaysData = new String[dataAllDays.getNoDayEntries(drawDay.getDate())];
        todaysData = dataAllDays.getDayText(drawDay.getDate()).split("\n");
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
            }
        }
        drawDay.setDayText();
        setUpCheckBoxes();
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
