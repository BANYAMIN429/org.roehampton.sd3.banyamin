
package com.sparebnb.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SpareBnBApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // This sets up the layout defined in MainView
        MainView mainView = new MainView();

        // Create the window (Scene) with width 900 and height 600
        Scene scene = new Scene(mainView.getView(), 900, 600);

        primaryStage.setTitle("SpareB&B - Sprint 3 Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}