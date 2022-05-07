package com.calendar.calendar;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.text.Font;

public class DrawDay {
    private BorderPane dayLayout;
    private Label dateLabel, dayInfo;
    private TextField dayText;

    public DrawDay(){
        dayLayout = new BorderPane();
        dateLabel = new Label();
        dateLabel.setFont(Font.font("SansSerif", 30));
        dayInfo = new Label();
        dayInfo.setFont(Font.font("SansSerif", 30));
        dayText = new TextField();
    }

    public void setDateLabelText(String dateLabel) {
        this.dateLabel.setText(dateLabel);
    }

    public void setDayInfoText(String dayInfo) {
        this.dayInfo.setText(dayInfo);
    }

    public String getDayTextInput(){
        return dayText.getText();
    }
}
