package javafx_chat_application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Logout {

    public Scene createLogoutScene(Stage primaryStage, Scene previousScene) {
        Label confirmationLabel = new Label("Are you sure you want to logout?");
        
        Button yesButton = new Button("Yes");
        Button cancelButton = new Button("Cancel");
        
        HBox buttonBox = new HBox(10, yesButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, confirmationLabel, buttonBox);
        layout.setAlignment(Pos.CENTER);

        Scene logoutScene = new Scene(layout, 400, 200);
        logoutScene.getStylesheets().add(getClass().getResource("styleLogout.css").toExternalForm());

        // Handle button actions
        yesButton.setOnAction(e -> {
            // Clear the session
            UserSession.getInstance().clearSession();

            // Go back to welcome screen
            Index index = new Index();
            Scene indexScene = index.createWelcomeScene(primaryStage);
            primaryStage.setScene(indexScene);
        });


        cancelButton.setOnAction(e -> {
            primaryStage.setScene(previousScene); // return to previous scene (Main chat)
        });

        return logoutScene;
    }
}


