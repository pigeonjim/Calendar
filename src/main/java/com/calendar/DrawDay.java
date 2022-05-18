package com.calendar;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.geometry.Insets;
import java.time.LocalDate;

public class DrawDay {
    private Label dayLabel, dayText;
    private DataAllDays dataAllDays;
    private DateLogic dateLogic;
    private LocalDate date;

    public DrawDay(LocalDate date, DataAllDays dataAllDays, DateLogic dateLogic){
        dayText = new Label("");
        this.dataAllDays = dataAllDays;
        this.dateLogic = dateLogic;
        this.date = date;
        dayLabel = new Label();
    }

    public Parent getView(){
        dayLabel.setAlignment(Pos.CENTER);
        dayLabel.setMinHeight(50);
        dayLabel.setMinWidth(200);
        dayLabel.setStyle("-fx-border-style: solid inside;" +
                "-fx-border-width: 3;" +
                "-fx-border-insets: 2;" +
                "-fx-border-color: black;" +
                "-fx-background-color: #fafad290;" +
                "-fx-background-radius: 20px 20px 5px 5px; " +
               "-fx-border-radius: 20px 20px 5px 5px;" +
                "-fx-background-insets: 5px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: sans-serif;" +
                "fx-font-size: 15;");
        dayLabel.setText((dateLogic.getWeekDayName(this.date)  + " " + dateLogic.getFormattedDate(this.date)));

        dayText.setMinHeight(150);
        dayText.setMinWidth(200);
        dayText.setAlignment(Pos.TOP_CENTER);
        dayText.setPadding(new Insets(5));
        dayText.setMaxSize(200,150);
        dayText.setStyle("fx-padding: 5,10,5,5;" +
                "-fx-border-style: solid;" +
                "-fx-border-radius: 25px 25px 0px 0px;" +
                "-fx-border-width: 4;" +
                "-fx-border-color: black;" +
                "-fx-background-color: #F3F3F5;" +
                "-fx-background-insets: 5px;" +
                "-fx-background-radius: 15px 15px 0px 0px;");
        dayText.setText(dataAllDays.getDayEntriesToString(date));

        VBox dayLayout = new VBox();
        dayLayout.getChildren().addAll(dayLabel,dayText);
        dayLayout.setAlignment(Pos.CENTER);
        dayLayout.setSpacing(-5);
        dayLayout.setMinSize(200,200);

        dayText.setOnMouseClicked((event) -> {
            clickTextLabel();
        });

        return dayLayout;
    }

    public void setWeekendBackgroundColour(){
        dayLabel.setStyle( "fx-padding: 5;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-radius: 25px;" +
                "-fx-border-width: 3;" +
                "-fx-border-insets: 2;" +
                "-fx-border-color: black;" +
                "-fx-background-color: #AEB6BF;" +
                "-fx-background-insets: 5px;" +
                "-fx-background-radius: 25;");
    }

    private void clickTextLabel(){
        PopupDayInfo popup = new PopupDayInfo(dataAllDays, this);
        popup.showPopup();
    }

    public void setDayText() {
        dayText.setText(dataAllDays.getDayEntriesToString(date));
    }

    public LocalDate getDate(){
        return date;
    }
}