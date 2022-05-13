module com.calendar.calendar {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.calendar to javafx.fxml;
    exports com.calendar;
}