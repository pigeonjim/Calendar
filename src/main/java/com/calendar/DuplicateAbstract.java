package com.calendar;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;

public abstract class DuplicateAbstract {
    protected Button yesButton, noButton;
    protected ArrayList<CheckBox> cbAry;
    protected Label output;
    protected BlankStage blankStage;
    protected VBox cbLayout;
    protected HashMap<LocalDate,String> duplicatesHM;
    protected ScrollPane scrolling;
    protected String yesButtonString, labelString, entry;
    protected LocalDate entryDate;

    public DuplicateAbstract(HashMap<LocalDate,String> duplicatesHM){
        this.allConstructors();
        this.duplicatesHM = duplicatesHM;
        this.entry = "";
    }
    public DuplicateAbstract(LocalDate entryDate, String entry){
        this.allConstructors();
        this.entry = entry;
        this.entryDate = entryDate;
    }
    protected void allConstructors(){
        blankStage = new BlankStage();
        cbLayout = new VBox();
        cbAry = new ArrayList<>();
        yesButtonString = "";
        labelString = "";
    }

    public void showPopup(DataAllDays data){
        if(entry.isEmpty()){
            setUpCheckBoxesHM();
        } else{
            setUpCheckBoxesSingle();
        }
        yesButton = new Button(yesButtonString);
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
        output = new Label(labelString);
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
            yesButtonEvent(data);
            blankStage.closeStage();
        });

        noButton.setOnAction((event) -> {
            blankStage.closeStage();
        });
    }

    private void setUpCheckBoxesHM(){
        cbLayout.getChildren().clear();
        cbAry.clear();
        for(LocalDate date: duplicatesHM.keySet()){
            createCB(date,duplicatesHM.get(date));
        }
        for(CheckBox cb:cbAry){
            cbLayout.getChildren().add(cb);
        }
    }

    private void setUpCheckBoxesSingle(){
        cbLayout.getChildren().clear();
        cbAry.clear();
        createCB(entryDate,entry);
        for(CheckBox cbox:cbAry){
            cbLayout.getChildren().add(cbox);
        }
    }

    private void createCB(LocalDate date, String entry){
        CheckBox cb = new CheckBox(date + " : " + entry);
        cb.setPrefHeight(40);
        cb.setStyle("-fx-font-weight: bold;" +
                "-fx-font-family: cursive;" +
                "fx-font-size: 30;");
        cbAry.add(cb);
    }

    protected void yesButtonEvent(DataAllDays yesdata){

    }

    public void setyesButtonString(String butttonstring){
        this.yesButtonString = butttonstring;
    }

    public void setLabelString(String labString){
        this.labelString = labString;
    }
}
