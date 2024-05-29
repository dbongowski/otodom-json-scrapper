module com.example.projekt_01 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires org.json;
    requires java.desktop;


    opens com.example.projekt_01 to javafx.fxml;
    exports com.example.projekt_01;
}