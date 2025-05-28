package javafx_chat_application;

import java.sql.Connection;
import java.sql.SQLException;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login {

	public Scene createLoginScene(Stage primaryStage) {
		
    	// Create label for username
        Label userLabel = new Label("Username:");
        // Create text input field for username
        TextField userField = new TextField();
        // Create label for password
        Label passLabel = new Label("Password:");
        // Create text input field for password
        PasswordField passField = new PasswordField();
        // Create Login button
        Button loginButton = new Button("Login");
        // Create Back button
        Button backButton = new Button("Back");
        // create label to display login result
        Label loginStatus = new Label();
        
        loginStatus.setId("loginStatus");

        // Create a layout (VBox) to arrange the elements.
        VBox loginLayout = new VBox(10, userLabel, userField, passLabel, passField, loginButton, backButton, loginStatus);
        loginLayout.setStyle("-fx-padding: 20;");

        // Set an action for the "Login" button to validate the credentials.
        loginButton.setOnAction(event -> {
            String email = userField.getText().trim();
            String password = passField.getText().trim();

            try (Connection conn = DatabaseConnector.getConnection()) {
                String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
                var statement = conn.prepareStatement(sql);
                statement.setString(1, email);
                statement.setString(2, password);
                var rs = statement.executeQuery();

                if (rs.next()) {
                    // Get user info from the database
                    int userId = rs.getInt("id");
                    String username = rs.getString("username"); // or whatever field holds the name

                    // Save session
                    UserSession.getInstance().setUser(username, userId);

                    // Go to main chat screen
                    Main main = new Main();
                    main.start(primaryStage);
                } else {
                    loginStatus.setText("Invalid username or password.");
                }

            } catch (SQLException ex) {
                loginStatus.setText("Database error: " + ex.getMessage());
            }
        });
        
        // Back button logic
        backButton.setOnAction(e -> {
        	Index index = new Index();
        	Scene welcomeScene = index.createWelcomeScene(primaryStage);
        	primaryStage.setScene(welcomeScene);
        });

        Scene scene = new Scene(loginLayout, 500, 450);
        // Link to CSS file
        scene.getStylesheets().add(getClass().getResource("styleLogin.css").toExternalForm());
        return scene;
    }
}


