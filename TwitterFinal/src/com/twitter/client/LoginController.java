package com.twitter.client;


import com.implementation.CommandParserServiceImpl;
import com.implementation.ConnectionServiceImpl;
import com.implementation.ConsoleViewServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static com.twitter.client.Client.ip;
import static com.twitter.client.Client.port;


/** Controls the login screen */
public class LoginController {
    @FXML private TextField user;
    @FXML private TextField password;
    @FXML private Button loginButton;

    public void handleSignupButtonOnClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signup.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleLoginButtonOnClicked(ActionEvent actionEvent) throws IOException {
        if (this.user != null && this.password != null) {
            String username = this.user.getText();
            String password = this.password.getText();
            CommandParserServiceImpl commandParserService = new CommandParserServiceImpl();
            String nameFile = commandParserService.loginLines(username, password);
            File file = new File(nameFile);
            new Client();
            ConnectionServiceImpl connectionService = new ConnectionServiceImpl(ip,port,file);
            Object object = connectionService.ConnectionServ();
            if (object instanceof Boolean) {
                if (object.equals(true)) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }
    }
}
