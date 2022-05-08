package com.calendar.calendar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.time.LocalDate;

import javafx.scene.layout.GridPane;


public class CalendarApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
         DateLogic dl =new DateLogic();


        BorderPane layout = new BorderPane();
        layout.setPrefSize(800,800);
        //layout.setCenter();
        Scene testScene = new Scene(layout);

        stage.setScene(testScene);
        stage.show();



    }

    public static void main(String[] args) {

        //launch();
        DateLogic dl = new DateLogic();
        LocalDate now = LocalDate.of(2022,02,01);
        System.out.println(dl.getNoDaysInMonth(now));



        //DrawCalendar cal = new DrawCalendar(now,dl);
        //cal.createDrawDays();

    }
}