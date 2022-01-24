package com.twitter.client;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.twitter.client.LoginManager;

/** Main application class for the login demo application */
public class AppUI extends Application {
    public Stage stage;
    public Stage getStage() {
        return stage;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public static void main(String[] args) { launch(args); }
    @Override public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new StackPane());
        LoginManager loginManager = new LoginManager(scene);
        loginManager.showLoginScreen();
        stage.setScene(scene);
        stage.show();
    }
}
