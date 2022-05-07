module com.calendar.calendar {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.calendar.calendar to javafx.fxml;
    exports com.calendar.calendar;
}