package com.calendar;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.LocalDate;

public class DuplicateEntryPopup {
    private Button yesButton, noButton;
    private BorderPane pane;
    private Label output;
    BlankStage blankStage;
    public DuplicateEntryPopup(){
        blankStage = new BlankStage();
    }
    public void showPopup(DataAllDays data,LocalDate date, String entry){
        yesButton = new Button("Click to add this entry again");
        yesButton.setStyle("-fx-border-style: solid inside;" +
                        "-fx-border-width: 3;" +
                        "-fx-border-color: green;");
        noButton = new Button("Click to discard this entry");
        noButton.setStyle("-fx-border-style: solid inside;" +
                        "-fx-border-width: 3;" +
                        "-fx-border-color: red;");
        output = new Label(date.toString() + " Already contains entry - " + entry);
        output.setMinSize(250,75);
        output.setAlignment(Pos.CENTER);

        blankStage.getPane().setTop(output);
        blankStage.getPane().setLeft(yesButton);
        blankStage.getPane().setRight(noButton);
        blankStage.getPane().setStyle("-fx-border-style: solid inside;" +
                "-fx-border-width: 4;" +
                "-fx-border-color: black;");
        blankStage.setTitle("Duplicate entry found");
        blankStage.showStage();

        noButton.setOnAction((event) -> {
            blankStage.closeStage();
        });
        yesButton.setOnAction((event) -> {
            data.addNewDayData(date,entry);
            blankStage.closeStage();
        });

    }
}
