package com.example.projekt_01;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.json.*;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class OffersController implements Initializable
{

    @FXML
    private BorderPane scene;
    @FXML
    private CheckMenuItem darkModeButton;
    DarkMode darkMode;
    @FXML
    private TableView<Offers> offersTable;
    @FXML
    private TableColumn<Offers, String> image;
    @FXML
    private TableColumn<Offers, String> title;
    @FXML
    private TableColumn<Offers, String> address;
    @FXML
    private TableColumn<Offers, Integer> cost;
    @FXML
    private TableColumn<Offers, Integer> area;
    @FXML
    private TableColumn<Offers, Integer> rooms;
    @FXML
    private TableColumn<Offers, Hyperlink> url;
    @FXML
    private Button mainMenuButton;
    @FXML
    private TextField costFrom;
    @FXML
    private TextField costTo;
    @FXML
    private TextField areaFrom;
    @FXML
    private TextField areaTo;
    @FXML
    private TextField roomsFrom;
    @FXML
    private TextField roomsTo;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        darkMode = new DarkMode(scene, darkModeButton);
        Platform.runLater(() -> darkMode.checkAndSetInitialDarkModeState());
        setTables();
    }
    private void setTables()
    {

        image.setCellFactory(tc -> new TableCell<>()
        {
            final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String item, boolean empty)
            {
                super.updateItem(item, empty);
                if (item == null || empty)
                {
                    setGraphic(null);
                } else
                {
                    imageView.setImage(new Image(item));
                    imageView.setFitWidth(100);  // You can set fitWidth and fitHeight as per your need
                    imageView.setFitHeight(100);
                    setGraphic(imageView);
                }
            }
        });
        url.setCellFactory(tc -> new TableCell<Offers, Hyperlink>() {
            private final Hyperlink link = new Hyperlink();

            @Override
            protected void updateItem(Hyperlink item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    link.setText("View offer");
                    link.setUserData(item.getUserData());
                    link.setOnAction(evt -> {
                        try {
                            Desktop.getDesktop().browse(new URI((String) link.getUserData()));
                        } catch (IOException | URISyntaxException e) {
                            e.printStackTrace();
                        }
                    });
                    setGraphic(link);
                }
            }
        });

        image.setCellValueFactory(new PropertyValueFactory<>("image"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        area.setCellValueFactory(new PropertyValueFactory<>("area"));
        rooms.setCellValueFactory(new PropertyValueFactory<>("rooms"));
        url.setCellValueFactory(new PropertyValueFactory<>("url"));

        ObservableList<Offers> offersList = FXCollections.observableArrayList();

        try
        {
            offersList = getJson("http://localhost:8081/");
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        offersTable.setItems(offersList);
    }
    private void refreshTable(String parsedURL)
    {
        ObservableList<Offers> offersList = FXCollections.observableArrayList();
        offersTable.getItems().clear();
        try
        {
            offersList = getJson(parsedURL);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        offersTable.setItems(offersList);
    }
    @FXML
    private void filter(ActionEvent actionEvent)
    {
        String base_url = "http://localhost:8081/";
        String minCost = costFrom.getText();
        String maxCost = costTo.getText();
        String minRooms = roomsFrom.getText();
        String maxRooms = roomsTo.getText();
        String minArea = areaFrom.getText();
        String maxArea = areaTo.getText();

        String urlWithParams = String.format("%s?minCost=%s&maxCost=%s&minRooms=%s&maxRooms=%s&minArea=%s&maxArea=%s", base_url, minCost, maxCost, minRooms, maxRooms, minArea, maxArea);
        refreshTable(urlWithParams);

    }
    private ObservableList<Offers> getJson(String parsedURL) throws Exception
    {
        ObservableList<Offers> offersList = FXCollections.observableArrayList();
        URL url = new URL(parsedURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream inputConn = connection.getInputStream();
        Scanner sc = new Scanner(inputConn);
        sc.useDelimiter("\\Z");
        String text = "";
        if (sc.hasNext()) {
            text = sc.next();
        }
        System.out.println(text);
        JSONArray jsonArr = new JSONArray(text);
        for(int i=0; i < jsonArr.length(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            String imageUrl = jsonObj.getString("image_url");
            String title = jsonObj.getString("title");
            String address = jsonObj.getString("address");
            Integer cost = jsonObj.getInt("cost");
            Integer area = jsonObj.getInt("area");
            Integer rooms = jsonObj.getInt("rooms");
            Hyperlink urlOffer = new Hyperlink();
            urlOffer.setUserData(jsonObj.getString("offer_url"));
            Offers offers = new Offers(imageUrl, title, address, cost, area, rooms, urlOffer);
            offersList.add(offers);
        }
        return offersList;
    }
    @FXML
    private void enableDarkMode(ActionEvent actionEvent)
    {
        darkMode.enableDarkMode();
    }
    @FXML
    private void openMainMenu(ActionEvent actionEvent)
    {
        WindowManager.openWindow("MainMenu.fxml", mainMenuButton);
    }
    @FXML
    private void aboutMenuItemOnAction(ActionEvent actionEvent)
    {
        WindowManager.showAboutPage();
    }

}
