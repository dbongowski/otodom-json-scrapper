package com.example.projekt_01;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable
{
    @FXML
    private BorderPane scene;
    ModifyWindowDarkMode darkMode;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        darkMode = new ModifyWindowDarkMode(scene);
        Platform.runLater(() -> darkMode.checkAndSetInitialDarkModeState());
    }

}
