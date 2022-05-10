package com.calendar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDate;


public class CalendarApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
         DateLogic dl =new DateLogic();
        LocalDate now = LocalDate.of(2022,05,10);
        DrawCalendar cal = new DrawCalendar(now,dl);

        BorderPane layout = new BorderPane();
        layout.setPrefSize(800,800);

        layout.setRight(cal.getView());
        //layout.setCenter();
        Scene testScene = new Scene(layout);

        stage.setScene(testScene);
        stage.setTitle(now.getMonth().toString() + " " + now.getYear());
        stage.show();
    }

    public static void main(String[] args) {

        launch();

    }
}