package javafx_chat_application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Main.java

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	// messages: a dynamic list that holds chat messages and automatically updates the UI.
	// messageListView: displays the list of chat messages.
	// messageTextArea: where the user types their message.
    private ObservableList<String> messages;
    private ListView<String> messageListView;
    private TextArea messageTextArea;
    
    // This is the main method that launches the JavaFX application.
    public static void main(String[] args) {
        launch(args);
    }

    // The start() method is called when the JavaFX app starts.
    // It sets up the main window (Stage) and scene.
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chat Application");

        // Initializes the message list, list view, and text area.
        // The list view shows messages from messages.
        messages = FXCollections.observableArrayList();
        messageListView = new ListView<>(messages);
        messageTextArea = new TextArea();
        messageTextArea.setPrefRowCount(2);
        Button sendButton = new Button("Send");
        
        // Create logout button
        Button logoutButton = new Button("Logout");
        logoutButton.setMinWidth(80);

        // This block runs when you click the "Send" button:
        // Gets the message and current user from UserSession.
        // Adds the message to the list (UI).
        // Clears the text box.
        // Saves the message to the database using saveMessageToDB().
        sendButton.setOnAction(event -> {
            String message = messageTextArea.getText().trim();
            if (!message.isEmpty()) {
                String username = UserSession.getInstance().getUsername();
                int userId = UserSession.getInstance().getUserId();

                messages.add(username + ": " + message);
                messageListView.scrollTo(messages.size() - 1);
                messageTextArea.clear();

                saveMessageToDB(userId, message); // Save to database
            }
        });
        
        // Handles pressing Enter in the text area:
        // If Shift is not held, it sends the message.
        // Prevents newline from being added with event.consume().
        messageTextArea.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    if (!event.isShiftDown()) {
                        event.consume(); // Prevents adding a new line
                        String message = messageTextArea.getText().trim();
                        if (!message.isEmpty()) {
                            messages.add("You: " + message);
                            messageTextArea.clear();
                            messageListView.scrollTo(messages.size() - 1);
                        }
                    }
                    break;
                default:
                    break;
            }
        });

        HBox inputBox = new HBox(10);
        HBox.setHgrow(messageTextArea, Priority.ALWAYS);
        messageTextArea.setPrefWidth(300);
        sendButton.setMinWidth(80);
        inputBox.getChildren().addAll(messageTextArea, sendButton);
        VBox root = new VBox(10, messageListView, inputBox, logoutButton);
        
        logoutButton.setOnAction(e -> {
            Logout logout = new Logout();
            Scene logoutScene = logout.createLogoutScene(primaryStage, primaryStage.getScene());
            primaryStage.setScene(logoutScene);
        });
        
        loadMessagesFromDB();

        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Saves each message sent into the messages table with the user ID.
    private void saveMessageToDB(int userId, String message) {
        String sql = "INSERT INTO messages (user_id, message) VALUES (?, ?)";
        try (Connection conn = DatabaseConnector.getConnection(); // Your Database connection method
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, message);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
       

    	// Loads messages from the database when the app starts.
    	// Uses SQL JOIN to get both message text and username.
        private void loadMessagesFromDB() {
            String sql = "SELECT u.username, m.message, m.timestamp FROM messages m JOIN users u ON m.user_id = u.id ORDER BY m.timestamp ASC";
            try (Connection conn = DatabaseConnector.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("username");
                    String message = rs.getString("message");
                    messages.add(username + ": " + message);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}



