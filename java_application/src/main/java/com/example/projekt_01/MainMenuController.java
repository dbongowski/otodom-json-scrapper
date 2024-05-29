package com.example.projekt_01;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.fxml.Initializable;
import javafx.application.Platform;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable
{

    @FXML
    private BorderPane scene;
    @FXML
    private CheckMenuItem darkModeButton;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Button ordersButton;
    @FXML
    private Button inventoryButton;
    DarkMode darkMode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        Platform.runLater(() -> {
            darkMode = new DarkMode(scene, darkModeButton);
            darkMode.checkAndSetInitialDarkModeState();
        });
    }
    @FXML
    private void enableDarkMode(ActionEvent actionEvent)
    {
        darkMode.enableDarkMode();
    }
    @FXML
    private void openOrders(ActionEvent actionEvent)
    {
        WindowManager.openWindow("Orders.fxml", ordersButton);
    }
    @FXML
    private void openInventory(ActionEvent actionEvent)
    {
        WindowManager.openWindow("Offers.fxml", inventoryButton);
    }
    @FXML
    private void aboutMenuItemOnAction(ActionEvent actionEvent)
    {
        WindowManager.showAboutPage();
    }
}