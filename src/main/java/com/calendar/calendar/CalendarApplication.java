package com.calendar.calendar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CalendarApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

    }

    public static void main(String[] args) {

        //launch();

        DateLogic dateL = new DateLogic();
        System.out.println(dateL.extraLeapYearDays(2024,2));
    }
}