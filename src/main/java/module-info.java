module com.example.databases {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.databases to javafx.fxml;
    exports com.example.databases;
}