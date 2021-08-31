package project.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import javafx.scene.image.Image;

/**
 * Application methods to run the GUI
 */
public class GUIApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/project/GUI_Screens.fxml"));
        primaryStage.setScene(new Scene(root, 750, 530));
        primaryStage.setResizable(false);
        primaryStage.setTitle("PlaneSonar25");
        primaryStage.show();
        primaryStage.getIcons().add(new Image("primaryStageIcon.png"));
    }

    public static void main(String[] args) {
        launch(args);
    }

}
