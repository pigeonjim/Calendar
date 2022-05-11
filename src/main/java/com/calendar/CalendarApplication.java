package com.calendar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

public class CalendarApplication extends Application {
    @Override
    public void start(Stage stage) {

        DateLogic dateLogic =new DateLogic();
        AllData allData = new AllData();
        DrawCalendar drawCalendar = new DrawCalendar(allData,dateLogic);
        DrawUI ui = new DrawUI(dateLogic, drawCalendar, allData);

        BorderPane layout = new BorderPane();

        layout.setRight(drawCalendar.getView());
        layout.setTop(ui.getView());
        Scene mainScene = new Scene(layout);

        stage.setScene(mainScene);
        stage.setTitle(allData.getWorkingDate().toString() + " " + allData.getWorkingDate().getYear());
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }

}