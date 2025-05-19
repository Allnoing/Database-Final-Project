package com.example.databases;

/*
AUTHOR       : Sa'Cairo Bonner
CREATE DATE  : 4/08/2025
Updated      : 4/27/2025
Class        : ITN261
Class Section: 201
Assignment   : Final Project
PURPOSE      : Handles user authentication for the inventory management system.
               Displays a login screen, verifies credentials, and transitions to the inventory screen upon successful login.
NOTES        :
 */


import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/*
 * LoginScreen class responsible for managing the login screen and login process
 * Provides functionality to validate user credentials and handle login attempts
 */
public class LoginScreen {

    private int attemptCount = 0; // Counter to track failed login attempts
    private TextField usernameField;
    private PasswordField passwordField;
    private Label messageLabel;
    private Button loginButton;

    private Stage primaryStage; // Main application window passed from Main.java

    // Constructor to receive and store the primary stage
    public LoginScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // === Build the Login Screen ===
    // Creates and returns the login screen scene
    // Sets up username and password fields, login button, and a message label for feedback
    public Scene createLoginScene() {
        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        messageLabel = new Label();

        loginButton = new Button("Login");
        loginButton.setOnAction(e -> handleLogin());

        VBox vbox = new VBox(10, usernameField, passwordField, loginButton, messageLabel);
        vbox.setPadding(new Insets(20));

        return new Scene(vbox, 300, 200);
    }

    // === Handle Login Button Click ===
    /* Handles the logic when the Login button is clicked
     * Checks the entered username and password against the database
     * On success, transitions to the Inventory screen
     * On failure, increments attempt counter and handles lockout after 3 failed attempts
    */
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean success = DatabaseHelper.checkLogin(username, password);

        // Small pause to show success message before transitioning from login screen
        if (success) {
            messageLabel.setText("Login successful!");

            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> openInventoryScreen());
            pause.play();
        } else {
            attemptCount++;
            handleLoginFailure();
        }
    }

    // === Handle Failed Login Attempts ===
    /* After 3 unsuccessful attempts, exits the application
    * Displays a failure message after each failed attempt
    */
    private void handleLoginFailure() {
        if (attemptCount >= 3) {
            messageLabel.setText("Too many failed attempts. Exiting...");
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> System.exit(0));
            pause.play();
        } else {
            messageLabel.setText("Login failed. Attempt " + attemptCount + " of 3.");
        }
    }

    // === Transitions the user to the Inventory Management screen after successful login ===
    private void openInventoryScreen() {
        try {
            ProductManagerScreen inventoryController = new ProductManagerScreen(primaryStage);
            Scene inventoryScene = inventoryController.createInventoryScene();
            primaryStage.setScene(inventoryScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
