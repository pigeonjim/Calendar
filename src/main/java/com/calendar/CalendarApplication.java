package com.calendar;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

public class CalendarApplication extends Application {
    @Override
    public void start(Stage stage) {
        DateLogic dateLogic =new DateLogic();
        DataAllDays dataAllDays = new DataAllDays();
        DataIO dataIO = new DataIO(dataAllDays);
        DrawCalendar drawCalendar = new DrawCalendar(dataAllDays,dateLogic);
        DrawUI ui = new DrawUI(dateLogic, drawCalendar, dataAllDays,dataIO);

        BorderPane layout = new BorderPane();
        layout.setRight(drawCalendar.getView());
        layout.setTop(ui.getView());
        Scene mainScene = new Scene(layout);
        stage.setScene(mainScene);
        stage.setTitle(dataAllDays.getWorkingDate().toString() + " " + dataAllDays.getWorkingDate().getYear());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaxHeight(dataAllDays.getScreenHeight() * 0.8);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}