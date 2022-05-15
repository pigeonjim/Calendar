package com.calendar;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BlankStage {
    private Stage window;
    private Scene scene;
    private BorderPane pane;

    public BlankStage(){
        window = new Stage();
        pane = new BorderPane();
    }
    public void showStage(){
        scene = new Scene(pane);
        scene.setFill(Color.TRANSPARENT);
        window.setTitle("Duplicate entry found");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.initStyle(StageStyle.TRANSPARENT);
        window.show();
    }
    public void closeStage(){
        window.close();
    }

    public BorderPane getPane() {
        return pane;
    }
    public void setTitle(String title){
        window.setTitle(title);
    }
}
