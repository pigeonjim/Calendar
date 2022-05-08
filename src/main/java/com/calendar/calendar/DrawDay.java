package com.calendar.calendar;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.text.Font;
import javafx.geometry.Insets;


public class DrawDay {
    Label dayLabel;

    public DrawDay(){
        dayLabel = new Label("testing");
    }

    public Parent getView(){
        //declare and format controls

        dayLabel.setFont(Font.font("SansSerif", 15));
        dayLabel.setAlignment(Pos.CENTER);
        dayLabel.setMinHeight(50);
        dayLabel.setMinWidth(200);
        dayLabel.setStyle( "fx-padding: 5;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-radius: 25px;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 2;" +
                "-fx-border-color: black;" +
                "-fx-background-color: #C3C37080;" +
                "-fx-background-insets: 5px;" +
                "-fx-background-radius: 25;");
        Label dayText = new Label("Tester");
        dayText.setMinHeight(150);
        dayText.setMinWidth(200);
        dayText.setPadding(new Insets(5));
        dayText.setStyle("fx-padding: 5,10,5,5;" +
                "-fx-border-style: solid;" +
                "-fx-border-radius: 10px;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-color: black;");

        //declare main layout for the day view
        VBox dayLayout = new VBox();
        //add controls to layout and format
        dayLayout.getChildren().addAll(dayLabel,dayText);
        dayLayout.setAlignment(Pos.CENTER);
        dayLayout.setSpacing(-8);
        dayLayout.setPrefSize(200,200);

        return dayLayout;
    }

    public void setWeekendBackgroundColour(){
        dayLabel.setStyle( "fx-padding: 5;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-radius: 25px;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 2;" +
                "-fx-border-color: black;" +
                "-fx-background-color: #A7999B80;" +
                "-fx-background-insets: 5px;" +
                "-fx-background-radius: 25;");
    }
}
