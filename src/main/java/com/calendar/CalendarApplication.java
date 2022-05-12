package com.calendar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.stage.StageStyle;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
public class CalendarApplication extends Application {
    @Override
    public void start(Stage stage) {

        DateLogic dateLogic =new DateLogic();
        AllData allData = new AllData();
        DataIO dataIO = new DataIO(allData);
        DrawCalendar drawCalendar = new DrawCalendar(allData,dateLogic);
        DrawUI ui = new DrawUI(dateLogic, drawCalendar, allData,dataIO);

        BorderPane layout = new BorderPane();

        layout.setRight(drawCalendar.getView());
        layout.setTop(ui.getView());
        Scene mainScene = new Scene(layout);

        stage.setScene(mainScene);
        stage.setTitle(allData.getWorkingDate().toString() + " " + allData.getWorkingDate().getYear());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}