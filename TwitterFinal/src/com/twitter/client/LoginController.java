package com.twitter.client;


import com.implementation.CommandParserServiceImpl;
import com.implementation.ConnectionServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static com.twitter.client.Client.ip;
import static com.twitter.client.Client.port;
import static com.twitter.server.Server.loginState;


/** Controls the login screen */
public class LoginController {
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    public MenuBar menuBar;

    /**
     * handle the signup button when clicked
     *
     * @param actionEvent action event
     * @throws IOException
     */
    public void handleSignupButtonOnClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Signup.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * handle the login button when clicked
     *
     * @param actionEvent action event
     * @throws IOException
     */
    public void handleLoginButtonOnClicked(ActionEvent actionEvent) throws IOException {
        if (this.usernameTextField != null && this.passwordTextField != null) {
            String username = this.usernameTextField.getText();
            String password = this.passwordTextField.getText();
            CommandParserServiceImpl commandParserService = new CommandParserServiceImpl();
            String nameFile = commandParserService.loginLines(username, password);
            File file = new File(nameFile);
            new Client();
            ConnectionServiceImpl connectionService = new ConnectionServiceImpl(ip,port,file);
            Object object = connectionService.ConnectionServ();
            if (object instanceof Boolean) {
                if (object.equals(true)) {
                    Client.setUsername(username);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                else {
                    this.usernameTextField.setText("wrong , try again");
                    this.passwordTextField.setText("wrong , try again");
                }
            }
        }
    }

    /**
     * handle the about in tab bar button when clicked
     *
     * @param actionEvent action event
     * @throws IOException
     */
    public void handleAboutButtonOnClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("about.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * handle the help in tab bar button when clicked
     *
     * @param actionEvent action event
     * @throws IOException
     */
    public void handleHelpButtonOnClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("help.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * handle the light in tab bar button when clicked
     *
     * @param actionEvent action event
     * @throws IOException
     */
    public void handleLightButtonOnClicked(ActionEvent actionEvent) {
        Scene scene = menuBar.getScene();
        scene.getStylesheets().remove("dark.css");
    }
    /**
     * handle the dark in tab bar button when clicked
     *
     * @param actionEvent action event
     * @throws IOException
     */
    public void handleDarkButtonOnClicked(ActionEvent actionEvent) {
        Scene scene = menuBar.getScene();
        scene.getStylesheets().add("dark.css");
    }
    /**
     * handle the maximize in tab bar button when clicked
     *
     * @param actionEvent action event
     * @throws IOException
     */
    public void handleMaximizeButtonOnClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        if (stage.isMaximized())
            stage.setMaximized(false);
        else
            stage.setMaximized(true);
    }
    /**
     * handle the exit in tab bar button when clicked
     *
     * @param actionEvent action event
     * @throws IOException
     */
    public void handleExitButtonOnClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.close();
    }
    /**
     * handle the logout in tab bar button when clicked
     *
     * @param actionEvent action event
     * @throws IOException
     */
    public void handleLogoutButtonOnClicked(ActionEvent actionEvent) throws IOException {
        loginState.put(Client.username,false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) menuBar.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
