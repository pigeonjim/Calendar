package com.calendar;

import java.time.LocalDate;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Modality;

public class DrawTextPopup{

    private AllData allData;
    private LocalDate date;
    private VBox layout;
    private Label header;
    private Button addButton, seeButton,close;
    private Stage popUp;
    private TextField inputBox;

    public DrawTextPopup(AllData allData, LocalDate date){
        this.allData = allData;
        this.date = date;
        this.layout = new VBox();
        this.header = new Label();
        this.inputBox = new TextField();
    }

    public void showPopup(){
        close = new Button("Close");
        close.setOnAction((event) -> {
            closeEvent();
        });

        popUp = new Stage();
        popUp.setTitle(date.toString());
        popUp.setMinHeight(250);
        popUp.setMinWidth(250);
        popUp.initModality(Modality.APPLICATION_MODAL);
        choice();
        Scene popScene = new Scene(layout);
        popUp.setScene(popScene);
        popUp.show();

    }

    public void choice(){

        addButton = new Button("Click to add new entry");
        seeButton = new Button("Click to see all entries for this day");
        layout.getChildren().addAll(addButton,seeButton,close);

        addButton.setOnAction((event) -> {
            addButtonEvent();
        });

        seeButton.setOnAction((event) -> {
            layout.getChildren().clear();

           try{
               Label show = new Label(allData.getDayText(date));
               layout.getChildren().addAll(show,close);
           }catch(Exception e){
               System.out.println("No data for this day");
           }
        });

    }

        private void addButtonEvent(){
            layout.getChildren().remove(addButton);
            layout.getChildren().add(inputBox);
        }

        private void closeEvent(){

                if(layout.getChildren().contains(inputBox)){
                    allData.addNewDayData(this.date,inputBox.getText());
                    inputBox.setText(" ");
                }
                layout.getChildren().clear();
                popUp.close();
        }

}
