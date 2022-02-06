package com.twitter.client;

import com.implementation.TweetingServiceImpl;
import com.twitter.server.LoadingFiles;
import com.twitter.server.Tweet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

import static com.twitter.server.LoadingFiles.*;
import static com.twitter.server.LoadingFiles.loadingFollowingList;
import static com.twitter.server.Server.loginState;

public class Tweet_Controller {
    public Button sendButton;
    public TextArea tweetTextArea;
    public MenuBar menuBar;

    public Tweet_Controller() {
        new LoadingFiles();
        loadingAccounts();
        loadingTweets();
        loadingFollowingList();
    }

    @FXML
    public void initialize() {

    }
    /**
     * handle the profile button when clicked
     *
     * @param actionEvent event
     * @throws IOException
     */
    public void handleProfileButtonOnClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * handle the timeline button when clicked
     *
     * @param actionEvent event
     * @throws IOException
     */
    public void handleTimelineButtonOnClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * handle the tweet button when clicked
     *
     * @param actionEvent event
     * @throws IOException
     */
    public void handleTweetButtonOnClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Tweet.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * handle the refresh button when clicked
     *
     * @param actionEvent event
     * @throws IOException
     */
    public void handleRefreshButtonOnClicked(ActionEvent actionEvent) throws IOException {
        new TweetController();
    }
    /**
     * handle the send button when clicked
     *
     * @param actionEvent event
     * @throws IOException
     */
    public void handleSendButtonOnClicked(ActionEvent actionEvent) {
        String tweet = tweetTextArea.getText();
        TweetingServiceImpl tweetingService = new TweetingServiceImpl();
        tweetingService.tweeting(new Tweet(findAccount(Client.username), tweet));
        storingAccounts();
        storingFollowingList();
        storingTweets();
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
