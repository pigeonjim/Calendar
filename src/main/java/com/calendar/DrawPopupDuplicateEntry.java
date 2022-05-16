package com.calendar;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;

public class DrawPopupDuplicateEntry {
    private Button yesButton, noButton;
    private BorderPane pane;
    private ArrayList<CheckBox> cbAry;
    private Label output;
    private DrawCalendar drawCalendar;
    private BlankStage blankStage;
    private VBox cbLayout;
    private HashMap<LocalDate,String> thelist;
    private ScrollPane scrolling;
    public DrawPopupDuplicateEntry(HashMap<LocalDate,String> thelist, DrawCalendar drawCalendar){
        blankStage = new BlankStage();
        cbLayout = new VBox();
        this.thelist = thelist;
        cbAry = new ArrayList<>();
        this.drawCalendar = drawCalendar;
    }
    public void showPopup(DataAllDays data){
        setUpCheckBoxes();
        yesButton = new Button("Click to add selected entries again");
        yesButton.setStyle("-fx-border-style: solid inside;" +
                        "-fx-border-width: 3;" +
                        "-fx-border-color: green;");
        noButton = new Button("Close window");
        noButton.setStyle("-fx-border-style: solid inside;" +
                        "-fx-border-width: 3;" +
                        "-fx-border-color: red;");
        HBox buttonLayout = new HBox();
        buttonLayout.setStyle("-fx-padding: 15px;" +
                        "-fx-border-radius: 25px;");
        buttonLayout.setSpacing(10);
        buttonLayout.getChildren().addAll(yesButton,noButton);
        output = new Label("The following entries are already on the calendar:");
        output.setStyle("-fx-font-weight: bold;" +
                "-fx-font-family: cursive;" +
                "fx-font-size: 30;");
        output.setMinSize(250,50);
        output.setAlignment(Pos.CENTER);

        cbLayout.setPrefSize(500,500);
        scrolling = new ScrollPane(cbLayout);
        scrolling.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrolling.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        blankStage.getPane().setTop(output);
        blankStage.getPane().setBottom(buttonLayout);
        blankStage.getPane().setCenter(scrolling);
        blankStage.getPane().setStyle("-fx-border-style: solid inside;" +
                "-fx-border-width: 4;" +
                "-fx-border-color: black;" +
                "-fx-border-radius: 25px;" +
                "-fx-padding: 15px;");
        blankStage.setTitle("Duplicate entry found");
        blankStage.buildStage();

        yesButton.setOnAction((event) -> {
            for(CheckBox cb: cbAry){
                if(cb.isSelected()){
                    String[] bits = cb.getText().split(" : ");
                    data.addNewDayData(LocalDate.parse(bits[0]),bits[1]);
                    drawCalendar.refreshDays();
                }
            }
            blankStage.closeStage();
        });
        noButton.setOnAction((event) -> {
            blankStage.closeStage();
        });
    }

    private void setUpCheckBoxes(){
        cbLayout.getChildren().clear();
        cbAry.clear();
        System.out.println(thelist.toString());
        for(LocalDate date: thelist.keySet()){
            CheckBox cb = new CheckBox(date + " : " + thelist.get(date));
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
}