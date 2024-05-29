package com.example.projekt_01;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.application.Platform;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class StartController implements Initializable
{

    @FXML
    private BorderPane scene;
    @FXML
    private CheckMenuItem darkModeButton;
    StartDarkMode startDarkMode;
    @FXML
    private TextField loginTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Label loginNotificationLabel;
    @FXML
    private Button loginButton;
    private static String username;

    @FXML
    protected void loginButtonOnAction(ActionEvent actionEvent)
    {
        validateLogin();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Platform.runLater(() -> {
            startDarkMode = new StartDarkMode(scene, darkModeButton);
            startDarkMode.checkAndSetInitialDarkModeState();
        });

        // Add event listener for ENTER key press on loginTextField
        loginTextField.setOnKeyPressed(this::loginOnKeyPressed);
        passwordTextField.setOnKeyPressed(this::loginOnKeyPressed);
    }

    @FXML
    protected void enableDarkMode(ActionEvent actionEvent)
    {
        startDarkMode.enableDarkMode();
    }

    protected void validateLogin()
    {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM useraccounts WHERE username = '" + loginTextField.getText() + "' AND password = '" + passwordTextField.getText() + "'";

        try
        {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next())
            {
                if (queryResult.getInt(1) == 1)
                {
                    username = loginTextField.getText();
                    WindowManager.openWindow("mainMenu.fxml", loginButton);
                }
                else
                {
                    loginNotificationLabel.setText("Login failed! Please try again!");
                }
                break;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }

    // Event listener for ENTER key press on loginTextField
    @FXML
    protected void loginOnKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            validateLogin();
        }
    }

    public static String getUsername()
    {
        return username;
    }

    @FXML
    private void aboutMenuItemOnAction(ActionEvent actionEvent)
    {
        WindowManager.showAboutPage();
    }
}
