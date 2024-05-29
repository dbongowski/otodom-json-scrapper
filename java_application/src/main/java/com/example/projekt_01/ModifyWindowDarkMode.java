package com.example.projekt_01;

import javafx.scene.layout.BorderPane;

public class ModifyWindowDarkMode extends StartDarkMode
{
    public ModifyWindowDarkMode(BorderPane scene)
    {
        super(scene);
    }
    @Override
    protected void checkAndSetInitialDarkModeState()
    {
        if(DarkModeState.getInstance().isDarkMode)
        {
            scene.getStylesheets().add("style.css");
        }
        System.out.println("Is dark mode: " + DarkModeState.getInstance().isDarkMode);
    }
}
