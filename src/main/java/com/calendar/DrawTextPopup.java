package com.calendar;

import java.time.LocalDate;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

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
        popUp.initModality(Modality.APPLICATION_MODAL);
        choice();
        Scene popScene = new Scene(layout);
        popScene.setFill(Color.TRANSPARENT);
        popUp.setScene(popScene);
        popUp.initStyle(StageStyle.TRANSPARENT);
        popUp.show();

    }

    public void choice(){

        addButton = new Button("Click to add new entry");
        seeButton = new Button("Click to see all entries for this day");
        layout.getChildren().addAll(addButton,seeButton,close);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));
        layout.setSpacing(15);
        layout.setStyle("-fx-background-radius: 25;"+
                "-fx-border-style: solid inside;" +
                "-fx-border-radius: 25px;" +
                "-fx-border-width: 4;" +
                "-fx-border-color: black;" +
                "-fx-background-color: #9BADDC");
        layout.setMinSize(150,200);

        addButton.setOnAction((event) -> {
            addButtonEvent();
        });

        seeButton.setOnAction((event) -> {
            layout.getChildren().clear();

           try{
               Label show = new Label(allData.getDayText(date));
               layout.getChildren().addAll(show,close);
               close.setText("Close");
           }catch(Exception e){
               System.out.println("No data for this day");
           }
        });

    }

        private void addButtonEvent(){
            layout.getChildren().remove(addButton);
            layout.getChildren().add(inputBox);
            close.setText("Close and save");
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
