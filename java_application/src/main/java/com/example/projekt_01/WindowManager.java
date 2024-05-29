package com.example.projekt_01;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class WindowManager
{
    public static void openWindow(String windowName, Button button)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(WindowManager.class.getResource(windowName));
            Parent windowRoot = fxmlLoader.load();
            Scene windowScene = new Scene(windowRoot);

            // Get the current Stage and set the new Scene
            Stage currentStage = (Stage) button.getScene().getWindow();
            currentStage.setScene(windowScene);
            currentStage.show();

        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public static void addWindow(String windowName, Object controller)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(WindowManager.class.getResource(windowName));
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void showAboutPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(WindowManager.class.getResource("About.fxml"));
            Parent aboutRoot = fxmlLoader.load();
            Stage aboutStage = new Stage();

            // Set the modality to block user interaction with other windows while the About page is open
            aboutStage.initModality(Modality.APPLICATION_MODAL);
            aboutStage.initStyle(StageStyle.UTILITY);
            aboutStage.setTitle("About");

            Scene aboutScene = new Scene(aboutRoot);
            aboutStage.setScene(aboutScene);
            aboutStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
