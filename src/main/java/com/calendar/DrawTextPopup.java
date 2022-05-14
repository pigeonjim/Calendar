package com.calendar;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.StageStyle;

public class DrawTextPopup{

    private DataAllDays dataAllDays;
    private VBox layout;
    private Button addButton, deleteButton,close;
    private Stage popUp;
    private TextField inputBox;
    private DrawDay drawDay;
    private Label outputBox;

    public DrawTextPopup(DataAllDays dataAllDays, DrawDay drawDay){
        this.dataAllDays = dataAllDays;
        this.layout = new VBox();
        this.inputBox = new TextField();
        this.drawDay = drawDay;
        this.outputBox = new Label();
    }

    public void showPopup(){
        close = new Button("Close");
        close.setOnAction((event) -> {
            allButtonEvents();
            popUp.close();
        });
        popUp = new Stage();
        popUp.setTitle(drawDay.getDate().toString());
        popUp.initModality(Modality.APPLICATION_MODAL);
        choice();
        Scene popScene = new Scene(layout);
        popScene.setFill(Color.TRANSPARENT);
        popUp.setScene(popScene);
        popUp.initStyle(StageStyle.TRANSPARENT);
        popUp.show();
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
        outputBox.setText(dataAllDays.getDayText(drawDay.getDate()));

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
            outputBox.setText(dataAllDays.getDayText(drawDay.getDate()));
            inputBox.setDisable(false);
            inputBox.setText("");
            addButton.setText("Save and add another new entry");
            close.setText("Close and save");
        }

        private void allButtonEvents(){
                if(layout.getChildren().contains(inputBox) && !inputBox.getText().isEmpty()){
                    dataAllDays.addNewDayData(drawDay.getDate(),inputBox.getText().toString());
                    drawDay.setDayText();
                }
        }
        private void deleteButtonEvent(){
            allButtonEvents();
            DrawDeletePopup drawDeletePopup = new DrawDeletePopup(this.dataAllDays,this.drawDay, this);
            drawDeletePopup.showPopup();
            if(layout.getChildren().contains(inputBox)){
                inputBox.setText("");
                addButton.setText("Click to add new entry");
            }
        }

    public void setOutputBox() {
        outputBox.setText(dataAllDays.getDayText(drawDay.getDate()));
    }
}