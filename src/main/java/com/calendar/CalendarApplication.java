package com.calendar;

import javafx.application.Application;
import javafx.stage.Stage;

public class CalendarApplication extends Application {
    @Override
    public void start(Stage stage) {

        DateLogic dateLogic =new DateLogic();
        AllData allData = new AllData();
        DataIO dataIO = new DataIO(allData);
        DrawCalendar drawCalendar = new DrawCalendar(allData,dateLogic);
        DrawUI ui = new DrawUI(dateLogic, drawCalendar, allData,dataIO);

        dataIO.getDataFromAccess();

        /*BorderPane layout = new BorderPane();

        layout.setRight(drawCalendar.getView());
        layout.setTop(ui.getView());
        Scene mainScene = new Scene(layout);

        stage.setScene(mainScene);
        stage.setTitle(allData.getWorkingDate().toString() + " " + allData.getWorkingDate().getYear());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();*/
    }

    public static void main(String[] args) {
        launch();
    }

}