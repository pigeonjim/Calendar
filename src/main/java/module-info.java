module com.calendar.calendar {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.calendar to javafx.fxml;
    exports com.calendar;
}