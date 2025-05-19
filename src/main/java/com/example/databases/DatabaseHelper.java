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


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    // === Database connection info ===
    // These are the details required to connect to the MySQL database
    private static final String DB_URL = "jdbc:mysql://localhost:3306/electronicproducts?useSSL=false";
    private static final String DBUSER = "root";
    private static final String DBPASS = "mysqlrootpass";


    /*
    * Connects to the database and retrieves all product records
    * Each record is converted into a Product object and added to a list
    */
    public static List<Product> getProducts() {
        List<Product> products = new ArrayList<>();

        // Try-with-resources ensures the connection is closed automatically
        try (Connection conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS)) {
            String query = "SELECT * FROM electronicproducts";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Loops through each row in the result set and converts it to a Product object
            while (rs.next()) {
                Product product = extractProductFromResultSet(rs);
                products.add(product);
            }

        } catch (SQLException e) {
            // Log any SQL-related errors (like bad credentials, connection issues, etc.)
            e.printStackTrace();
        }

        return products;
    }

    /* Converts a single row from the ResultSet into a Product object
     * Extracts and cleans weight safely to avoid any errors, as it's stored as a string like "15.6 pounds"
     */
    private static Product extractProductFromResultSet(ResultSet rs) throws SQLException {
        // Extracts weight as a string first since it's not stored as a clean number
        String weightStr = rs.getString("weight");
        double weight = 0.0;

        try {
            // Regex to find the first decimal number (such as "15.6" from "15.6 pounds")
            java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("\\d+(\\.\\d+)?").matcher(weightStr);
            if (matcher.find()) {
                weight = Double.parseDouble(matcher.group());
            }
        } catch (Exception ex) {
            // If parsing fails, logs it. better to have a product with weight 0.0 than to crash the app!!!
            ex.printStackTrace();
        }

        // Create and returns a Product using the parsed data
        return new Product(
                rs.getInt("product_id"),
                rs.getString("brand"),
                rs.getDouble("price"),
                rs.getString("description"),
                weight,
                rs.getDouble("rating")
        );
    }

    // === Hash the entered password with SHA-256 ===
    private static String hashPassword(String password) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // === Check Login Credentials ===
    public static boolean checkLogin(String username, String password) {
        String query = "SELECT password_hash FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                String enteredHash = hashPassword(password);

                return storedHash.equals(enteredHash);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // login failed if user not found or passwords don't match
    }

    // === Update an existing product ===
    public static boolean updateProduct(Product product) {
        String query = "UPDATE electronicproducts SET brand = ?, price = ?, description = ?, weight = ?, rating = ? WHERE product_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, product.getBrand());
            stmt.setDouble(2, product.getPrice());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getWeight());
            stmt.setDouble(5, product.getRating());
            stmt.setInt(6, product.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // === Insert a new product ===
    public static boolean insertProduct(Product product) {
        String query = "INSERT INTO electronicproducts (brand, price, description, weight, rating) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, product.getBrand());
            stmt.setDouble(2, product.getPrice());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getWeight());
            stmt.setDouble(5, product.getRating());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
