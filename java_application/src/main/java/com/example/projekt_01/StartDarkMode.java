package com.example.projekt_01;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.layout.BorderPane;
public class StartDarkMode
{
    @FXML
    protected BorderPane scene;

    protected CheckMenuItem darkModeButton;

    public StartDarkMode(BorderPane scene, CheckMenuItem darkModeButton)
    {
        this.scene = scene;
        this.darkModeButton = darkModeButton;
    }
    public StartDarkMode(BorderPane scene)
    {
        this.scene = scene;
    }
    protected void enableDarkMode() {
        DarkModeState.getInstance().isDarkMode = !DarkModeState.getInstance().isDarkMode;
        if (DarkModeState.getInstance().isDarkMode) {
            setDarkMode();
        } else {
            setLightMode();
        }
    }

    protected void setDarkMode() {
        scene.getStylesheets().add("style.css");
    }

    protected void setLightMode() {
        scene.getStylesheets().remove("style.css");
    }

    protected void checkAndSetInitialDarkModeState() {
        scene.getStylesheets().add("style.css");
        DarkModeState.getInstance().isDarkMode = scene.getStylesheets().contains("style.css");
        System.out.println("Is dark mode: " + DarkModeState.getInstance().isDarkMode);
        darkModeButton.setSelected(DarkModeState.getInstance().isDarkMode);
    }
}
