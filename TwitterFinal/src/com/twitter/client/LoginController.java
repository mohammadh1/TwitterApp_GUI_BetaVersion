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


/** Controls the login screen */
public class LoginController {
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;

    public void handleSignupButtonOnClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Signup.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

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
}
