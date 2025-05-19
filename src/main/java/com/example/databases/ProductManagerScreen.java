package com.example.databases;

/*
AUTHOR       : Sa'Cairo Bonner
CREATE DATE  : 4/08/2025
Updated      : 4/27/2025
Class        : ITN261
Class Section: 201
Assignment   : Final Project
PURPOSE      : take the inventory program we have been developing and turn it into a fully functional, database driven program.
Notes        :
 */


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProductManagerScreen {

    private Stage primaryStage;
    private Button logoutButton;
    private Button newProductButton;
    private ObservableList<Product> allProductsList;
    private ListView<Product> listView;
    private TextField productIdField, brandField, priceField, descriptionField, weightField, ratingField;
    private Button saveButton;

    public ProductManagerScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;
        allProductsList = FXCollections.observableArrayList(DatabaseHelper.getProducts());
    }

    public Scene createInventoryScene() {
        logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> handleLogout());

        listView = new ListView<>(allProductsList);
        listView.setPrefWidth(250);

        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> loadProductDetails(newVal));

        // === Create the editable fields ===
        productIdField = new TextField();
        productIdField.setPromptText("ID");
        productIdField.setEditable(false); // ID should not be editable

        brandField = new TextField();
        brandField.setPromptText("Brand");

        priceField = new TextField();
        priceField.setPromptText("Price");

        descriptionField = new TextField();
        descriptionField.setPromptText("Description");

        weightField = new TextField();
        weightField.setPromptText("Weight");

        ratingField = new TextField();
        ratingField.setPromptText("Rating");

        saveButton = new Button("Save Changes");
        saveButton.setOnAction(e -> handleSaveChanges());

        newProductButton = new Button("New Product");
        newProductButton.setOnAction(e -> resetInputFields());

        // Detailed values
        VBox detailsBox = new VBox(10,
                new Label("ID:"), productIdField,
                new Label("Brand:"), brandField,
                new Label("Price:"), priceField,
                new Label("Description:"), descriptionField,
                new Label("Weight:"), weightField,
                new Label("Rating:"), ratingField,
                saveButton,
                newProductButton
        );

        // === Filter Buttons ===
        Button allButton = new Button("All Products");
        Button rating2Button = new Button("Rating 2.00 - 2.99");
        Button rating3Button = new Button("Rating 3.00 - 3.99");
        Button rating4Button = new Button("Rating 4.00 - 5.00");

        allButton.setOnAction(e -> listView.setItems(allProductsList));
        rating2Button.setOnAction(e -> filterByRating(2.0, 2.99));
        rating3Button.setOnAction(e -> filterByRating(3.0, 3.99));
        rating4Button.setOnAction(e -> filterByRating(4.0, 5.0));

        HBox filterBox = new HBox(10, allButton, rating2Button, rating3Button, rating4Button, logoutButton);

        BorderPane root = new BorderPane();
        root.setLeft(listView);
        root.setCenter(detailsBox);
        root.setBottom(filterBox);

        return new Scene(root, 800, 500);
    }

    private void loadProductDetails(Product product) {
        if (product != null) {
            productIdField.setText(String.valueOf(product.getId()));
            brandField.setText(product.getBrand());
            priceField.setText(String.valueOf(product.getPrice()));
            descriptionField.setText(product.getDescription());
            weightField.setText(String.valueOf(product.getWeight()));
            ratingField.setText(String.valueOf(product.getRating()));
        }
    }

    /* Determines whether to insert a new product or update an existing one,
     * based on whether the ID field is empty.
     * Refreshes the product list view after a successful operation
     */
    private void handleSaveChanges() {
        try {
            String idText = productIdField.getText();
            Product product = buildProductFromFields();

            if (idText == null || idText.isEmpty()) {
                addProductToDatabase(product);
            } else {
                updateProductInDatabase(product);
            }

            allProductsList.setAll(DatabaseHelper.getProducts()); // reload fresh data
            listView.setItems(allProductsList);

        } catch (Exception e) {
            showError("Error saving changes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /* Collects input data from all TextFields and constructs a Product object
     * If ID is empty, assigns 0 as a placeholder for new product creation
     */
    private Product buildProductFromFields() {
        String brand = brandField.getText();
        double price = Double.parseDouble(priceField.getText());
        String description = descriptionField.getText();
        double weight = Double.parseDouble(weightField.getText());
        double rating = Double.parseDouble(ratingField.getText());

        String idText = productIdField.getText();
        int id = (idText == null || idText.isEmpty()) ? 0 : Integer.parseInt(idText);

        return new Product(id, brand, price, description, weight, rating);
    }

    /* Attempts to insert a new product into the database
     * Displays a success or error message based on the operation outcome
     */
    private void addProductToDatabase(Product product) {
        boolean success = DatabaseHelper.insertProduct(product);

        if (success) {
            showSuccess("New product added successfully!");
        } else {
            showError("Failed to add new product.");
        }
    }

    /* Attempts to update an existing product in the database
     * Displays a success or error message based on the operation outcome
     */
    private void updateProductInDatabase(Product product) {
        boolean success = DatabaseHelper.updateProduct(product);

        if (success) {
            showSuccess("Product updated successfully!");
        } else {
            showError("Failed to update product.");
        }
    }

    // Success message
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Error message
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void filterByRating(double minRating, double maxRating) {
        ObservableList<Product> filtered = allProductsList.filtered(p -> p.getRating() >= minRating && p.getRating() <= maxRating);
        listView.setItems(filtered);
    }



    // Clears out all the fields when clicking 'New product' button
    private void resetInputFields() {
        productIdField.clear();
        brandField.clear();
        priceField.clear();
        descriptionField.clear();
        weightField.clear();
        ratingField.clear();
    }

    // === Handles Logout and returns to LoginScreen ===
    private void handleLogout() {
        LoginScreen loginController = new LoginScreen(primaryStage);
        Scene loginScene = loginController.createLoginScene();
        primaryStage.setScene(loginScene);
    }
}
