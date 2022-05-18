package com.calendar;

import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class PopupDayInfo {

    private DataAllDays dataAllDays;
    private VBox layout;
    private Button addButton, deleteButton,close;
    private TextField inputBox;
    private DrawDay drawDay;
    private Label outputBox;
    private BlankStage blankStage;

    public PopupDayInfo(DataAllDays dataAllDays, DrawDay drawDay){
        this.dataAllDays = dataAllDays;
        this.layout = new VBox();
        this.inputBox = new TextField();
        this.drawDay = drawDay;
        this.outputBox = new Label();
        blankStage = new BlankStage();
    }

    public void showPopup(){
        close = new Button("Close");
        close.setOnAction((event) -> {
            allButtonEvents();
            blankStage.closeStage();
        });
        blankStage.setTitle(drawDay.getDate().toString());
        choice();
        blankStage.getPane().setCenter(layout);
        blankStage.getPane().setStyle("-fx-background-color: transparent;");
        blankStage.buildStage();
        inputBox.requestFocus();
    }

    public void choice(){

        addButton = new Button("Click to save and add new entry");
        deleteButton  =new Button("Click to remove entries from this date");
        layout.getChildren().addAll(addButton,deleteButton,close,inputBox, outputBox);
        inputBox.setStyle("-fx-background-radius: 25;"+
                "-fx-border-style: solid inside;" +
                "-fx-border-radius: 15px;" +
                "-fx-border-width: 4;" +
                "-fx-border-color: #ECE146;" +
                "-fx-background-color: #FFFFFF");
        outputBox.setMinSize(300,300);
        outputBox.setAlignment(Pos.TOP_CENTER);
        outputBox.setStyle("-fx-background-radius: 25;"+
                "-fx-border-style: solid inside;" +
                "-fx-border-radius: 15px;" +
                "-fx-border-width: 4;" +
                "-fx-border-color: #ECE146;" +
                "-fx-background-color: #FFFFFF");
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));
        layout.setSpacing(15);
        layout.setStyle("-fx-background-radius: 25;"+
                "-fx-border-style: solid inside;" +
                "-fx-border-radius: 25px;" +
                "-fx-border-width: 4;" +
                "-fx-border-color: black;" +
                "-fx-background-color: #778899");
        outputBox.setText(dataAllDays.getDayEntriesToString(drawDay.getDate()));

        addButton.setOnAction((event) -> {
            addButtonEvent();
        });
        inputBox.setOnKeyReleased((event) -> {
            if(event.getCode() == KeyCode.ENTER){
                addButtonEvent();
            }
        });
        deleteButton.setOnAction((event) -> {
            deleteButtonEvent();
        });
    }
        private void addButtonEvent(){
            allButtonEvents();
            outputBox.setText(dataAllDays.getDayEntriesToString(drawDay.getDate()));
            inputBox.setDisable(false);
            inputBox.setText("");
            addButton.setText("Save and add another new entry");
            close.setText("Close and save");
        }

        private void allButtonEvents(){
                if(layout.getChildren().contains(inputBox) && !inputBox.getText().isEmpty()){
                    dataAllDays.addNewDayData(drawDay.getDate(),inputBox.getText());
                    drawDay.setDayText();
                }
        }
        private void deleteButtonEvent(){
            allButtonEvents();
            PopupDeleteEntries popupDeleteEntries = new PopupDeleteEntries(this.dataAllDays,this.drawDay, this);
            popupDeleteEntries.showPopup();
            if(layout.getChildren().contains(inputBox)){
                inputBox.setText("");
                addButton.setText("Click to add new entry");
            }
        }

    public void setOutputBox() {
        outputBox.setText(dataAllDays.getDayEntriesToString(drawDay.getDate()));
    }
}