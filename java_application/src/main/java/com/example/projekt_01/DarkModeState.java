package com.example.projekt_01;

public class DarkModeState
{
    private static DarkModeState instance;
    public boolean isDarkMode;

    private DarkModeState() {
        isDarkMode = false;
    }

    public static DarkModeState getInstance() {
        if (instance == null) {
            instance = new DarkModeState();
        }
        return instance;
    }
}
