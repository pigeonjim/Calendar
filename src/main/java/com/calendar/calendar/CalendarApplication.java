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
        DrawDay testDay = new DrawDay();
        DrawDay testDay2 = new DrawDay();
        DrawDay testDay3 = new DrawDay();
        DrawDay testDay4 = new DrawDay();
        DrawDay testDay5 = new DrawDay();
        DrawDay testDay6 = new DrawDay();
        DrawDay testDay7 = new DrawDay();

        DateLogic dl =new DateLogic();

        GridPane gp = new GridPane();
        gp.add(testDay.getView(),0,0);
        gp.add(testDay2.getView(),1,0);
        gp.add(testDay3.getView(),2,0);
        gp.add(testDay4.getView(),3,0);
        gp.add(testDay5.getView(),4,0);
        gp.add(testDay6.getView(),5,0);
        gp.add(testDay7.getView(),6,0);
        gp.setHgap(15);

        BorderPane layout = new BorderPane();
        layout.setPrefSize(400,400);
        layout.setCenter(gp);
        Scene testScene = new Scene(layout);

        stage.setScene(testScene);
        stage.show();

        testDay7.setWeekendBackgroundColour();
        LocalDate date = LocalDate.now();

        testDay7.dayLabel.setText(dl.getFormattedDate(date));

    }

    public static void main(String[] args) {

        launch();

    }
}