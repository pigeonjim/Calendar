package com.calendar;

import javafx.scene.Scene;
import javafx.scene.control.Label;
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

    private AllData allData;
    private VBox layout;
    private Button addButton, seeButton,close;
    private Stage popUp;
    private TextField inputBox;
    private DrawDay drawDay;
    private Label output;

    public DrawTextPopup(AllData allData, DrawDay drawDay){
        this.allData = allData;
        this.layout = new VBox();
        this.inputBox = new TextField();
        this.drawDay = drawDay;
        this.output = new Label();
    }

    public void showPopup(){
        close = new Button("Close");
        close.setOnAction((event) -> {
            closeEvent();
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
    }

    public void choice(){

        addButton = new Button("Click to add new entry");
        seeButton = new Button("Click to see all entries for this day");
        layout.getChildren().addAll(addButton,seeButton,close,inputBox,output);
        inputBox.setDisable(true);
        inputBox.setStyle("-fx-background-radius: 25;"+
                "-fx-border-style: solid inside;" +
                "-fx-border-radius: 15px;" +
                "-fx-border-width: 4;" +
                "-fx-border-color: #ECE146;" +
                "-fx-background-color: #FFFFFF");
        output.setMinSize(300,300);
        output.setAlignment(Pos.TOP_CENTER);
        output.setStyle("-fx-background-radius: 25;"+
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
                "-fx-background-color: #3B74B4");

        addButton.setOnAction((event) -> {
            addButtonEvent();
        });

        seeButton.setOnAction((event) -> {
            seeButtonEvent();
        });

    }
        private void addButtonEvent(){
            inputBox.setDisable(false);
            addButton.setDisable(true);
            seeButton.setDisable(false);
            output.setDisable(true);
            close.setText("Close and save");
        }

        private void seeButtonEvent(){
            closeEvent();
            if(layout.getChildren().contains(inputBox)){
                inputBox.setText("");
                inputBox.setDisable(true);
            }
            output.setDisable(false);
            addButton.setDisable(false);
            seeButton.setDisable(true);
            close.setText("Close");
            output.setText(allData.getDayText(drawDay.getDate()));

        }
        private void closeEvent(){
                if(layout.getChildren().contains(inputBox)){
                    allData.addNewDayData(drawDay.getDate(),inputBox.getText().toString());
                    drawDay.setDayText();
                }
        }

}