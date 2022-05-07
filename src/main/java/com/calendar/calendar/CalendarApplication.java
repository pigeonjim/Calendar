package com.calendar.calendar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.io.IOException;

public class CalendarApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

    }

    public static void main(String[] args) {

        //launch();
        LocalDate date1 = LocalDate.of(2022,8,2);
        LocalDate date2 = LocalDate.of(2028,03,01);
        DateLogic dateL = new DateLogic();
        System.out.println(dateL.isDateWeekend(date1));
    }
}