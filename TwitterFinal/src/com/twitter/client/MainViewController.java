package com.twitter.client;


import com.implementation.CommandParserServiceImpl;
import com.implementation.ConnectionServiceImpl;
import com.implementation.TimelineServiceImpl;
import com.twitter.server.Tweet;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.twitter.client.Client.ip;
import static com.twitter.client.Client.port;
import static com.twitter.server.LoadingFiles.*;
import static com.twitter.server.Server.loginState;

/** Controls the main application screen */
public class MainViewController {
    public TextArea timelineTextArea;
    public MenuBar menuBar;

    @FXML
    /*public void initialize() {
        timelineTextArea.setWrapText(true);
        timelineTextArea.setEditable(false);
        // send request to server and get response to client
        CommandParserServiceImpl commandParserService = new CommandParserServiceImpl();
        String nameFile = commandParserService.timelineLines(Client.username);
        File file = new File(nameFile);
        new Client();
        System.out.println("salam");
        ConnectionServiceImpl connectionService = new ConnectionServiceImpl(ip,port,file);
        System.out.println("salam");
        Object object = connectionService.ConnectionServ();
        System.out.println("salam");
        if (object instanceof String) {
            // local method
            //ArrayList<Tweet> tweets = new TimelineServiceImpl().showMyTimeLine(findAccount(Client.username));
            String string = (String) object;
            timelineTextArea.setText(string);
        }
        else {
            this.timelineTextArea.setText("wrong , try again");
        }
    }*/

    public void handleProfileButtonOnClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleTimelineButtonOnClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleTweetButtonOnClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Tweet.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleRefreshButtonOnClicked(ActionEvent actionEvent) throws IOException {
        new MainViewController();
    }





    public void handleAboutButtonOnClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("about.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleHelpButtonOnClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("help.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleLightButtonOnClicked(ActionEvent actionEvent) {
        Scene scene = menuBar.getScene();
        scene.getStylesheets().remove("dark.css");
    }

    public void handleDarkButtonOnClicked(ActionEvent actionEvent) {
        Scene scene = menuBar.getScene();
        scene.getStylesheets().add("dark.css");
    }

    public void handleMaximizeButtonOnClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        if (stage.isMaximized())
            stage.setMaximized(false);
        else
            stage.setMaximized(true);
    }

    public void handleExitButtonOnClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.close();
    }

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