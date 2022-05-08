package com.calendar.calendar;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.text.Font;
import javafx.geometry.Insets;


public class DrawDay {

    public DrawDay(){
    }

    public Parent getView(){
        //declare and format controls
        Label dayLabel = new Label("testing");
        dayLabel.setFont(Font.font("SansSerif", 15));
        dayLabel.setAlignment(Pos.CENTER);
        dayLabel.setMinHeight(50);
        dayLabel.setMinWidth(100);
        dayLabel.setStyle("fx-padding: 5,5,5,5;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: black;");
        Label dayText = new Label("Tester");
        dayText.setMinHeight(150);
        dayText.setMinWidth(100);
        dayText.setPadding(new Insets(5));
        dayText.setStyle("fx-padding: 5,10,5,5;" +
                "-fx-border-style: solid;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: black;");

        //declare main layout for the day view
        VBox dayLayout = new VBox();
        //add controls to layout and format
        dayLayout.getChildren().addAll(dayLabel,dayText);
        //dayLayout.setPadding(new Insets(5));
        dayLayout.setAlignment(Pos.TOP_LEFT);
        dayLayout.setPrefSize(350,100);


        return dayLayout;
    }


}
