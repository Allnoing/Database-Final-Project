package com.example.databases;

/*
AUTHOR       : Sa'Cairo Bonner
CREATE DATE  : 4/08/2025
Updated      : 4/27/2025
Class        : ITN261
Class Section: 201
Assignment   : Final Project
PURPOSE      : Launches the inventory program by displaying the login screen first.
               After a successful login, the user will be transitioned to the inventory management system.
Notes        :
 */

/*
 * Main class responsible for launching the application.
 * It initializes the JavaFX environment and starts by showing the Login screen.
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // === Load the login screen first ===
        // Instantiate the login controller and create its scene
        LoginScreen loginScreen = new LoginScreen(primaryStage);
        Scene loginScene = loginScreen.createLoginScene();

        // Set the scene to the primary stage
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Product Inventory");
        primaryStage.show();
    }

    // Entry point of the program
    public static void main(String[] args) {
        launch(args);
    }
}
