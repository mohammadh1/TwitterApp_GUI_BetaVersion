package com.twitter.client;


import java.io.IOException;
import java.util.logging.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;

/** Manages control flow for logins */
public class LoginManager {
    private Scene scene;

    public LoginManager(Scene scene) {
        this.scene = scene;
    }

    /**
     * Callback method invoked to notify that a user has been authenticated.
     * Will show the main application screen.
     */
    public void authenticated(String sessionID) {
        showMainView(sessionID);
    }

    /**
     * Callback method invoked to notify that a user has logged out of the main application.
     * Will show the login application screen.
     */
    public void logout() {
        showLoginScreen();
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            scene.setRoot(loader.load());
            LoginController loginController =
                    loader.getController();
            //loginController.initManagerLogin(this);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showMainView(String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
            scene.setRoot((Parent) loader.load());
            MainViewController controller = loader.getController();
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void showSignupScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Signup.fxml"));
            scene.setRoot(loader.load());
            SignupController signupController =
                    loader.getController();
            //signupController.initManager(this);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}