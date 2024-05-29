package com.example.projekt_01;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.layout.BorderPane;
public class DarkMode extends StartDarkMode
{

    public DarkMode(BorderPane scene, CheckMenuItem darkModeButton)
    {
        super(scene,darkModeButton);
    }
    @Override
    protected void checkAndSetInitialDarkModeState()
    {
        if(DarkModeState.getInstance().isDarkMode)
        {
            scene.getStylesheets().add("style.css");
        }
        System.out.println("Is dark mode: " + DarkModeState.getInstance().isDarkMode);
        darkModeButton.setSelected(DarkModeState.getInstance().isDarkMode);
    }
}
