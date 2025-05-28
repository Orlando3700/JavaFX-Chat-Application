package javafx_chat_application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Index extends Application {

    @Override
    public void start(Stage primaryStage) {
        Index index = new Index();
        Scene welcomeScene = index.createWelcomeScene(primaryStage);
        primaryStage.setTitle("Chat Application");
        primaryStage.setScene(welcomeScene);
        primaryStage.show();
    }

    public Scene createWelcomeScene(Stage primaryStage) {
        Label welcomeLabel = new Label("Welcome to Chat Application");
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        loginButton.setOnAction(e -> {
            Login login = new Login();
            Scene loginScene = login.createLoginScene(primaryStage);
            primaryStage.setScene(loginScene);
        });

        registerButton.setOnAction(e -> {
            Register register = new Register();
            Scene registerScene = register.createRegisterScene(primaryStage);
            primaryStage.setScene(registerScene);
        });

        VBox layout = new VBox(20, welcomeLabel, loginButton, registerButton);

        Scene scene = new Scene(layout, 650, 500);
        scene.getStylesheets().add(getClass().getResource("styleIndex.css").toExternalForm()); // CSS linked here
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}


