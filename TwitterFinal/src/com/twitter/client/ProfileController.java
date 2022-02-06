package com.twitter.client;

import com.implementation.ObserverServiceImpl;
import com.implementation.TimelineServiceImpl;
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
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import static com.twitter.server.LoadingFiles.*;
import static com.twitter.server.Server.loginState;

public class ProfileController {
    public TextArea profileTextArea;
    public Text nameOfAccount;
    public TextArea bioOfAccount;
    public Text usernameOfAccount;
    public MenuBar menuBar;
    public Button followButton;
    public TextField searchBar;
    public Button unfollowButton;
    private String target;

    public ProfileController() {
        new LoadingFiles();
        loadingAccounts();
        loadingTweets();
        loadingFollowingList();
    }

    @FXML
    public void initialize() {
        unfollowButton.setVisible(false);
        followButton.setVisible(false);
        accountFinder(Client.username);
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

    public void handleRefreshButtonOnClicked(ActionEvent actionEvent) throws IOException {
        initialize();
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
    /**
     * handle the follow button when clicked
     *
     * @param actionEvent action event
     */
    public void handleFollowButtonOnClicked(ActionEvent actionEvent) {
        ObserverServiceImpl observerService = new ObserverServiceImpl();
        observerService.follow(findAccount(Client.username), findAccount(target));
        storingAccounts();
        storingFollowingList();
        storingTweets();
    }
    /**
     * handle the search button when clicked
     *
     * @param actionEvent action event
     */
    public void handleSearchButtonOnClicked(ActionEvent actionEvent) {
        followButton.setVisible(true);
        unfollowButton.setVisible(true);
        target = searchBar.getText();
        accountFinder(target);
    }

    /**
     * private method for setting values of textBoxes and names
     * @param user username
     */
    private void accountFinder(String user) {
        String name = " " + findAccount(user).getFirstName() + " " + findAccount(user).getLastName();
        nameOfAccount.setText(name);
        bioOfAccount.setText(findAccount(user).getBio());
        usernameOfAccount.setText(user);
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<Tweet> tweets = new TimelineServiceImpl().showMyTweets(findAccount(user));
        for (Tweet tweet : tweets) {
            stringBuilder.append(tweet.getSender().getUsername() + " : ");
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(tweet.getText());
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(System.lineSeparator());
        }
        profileTextArea.setText(String.valueOf(stringBuilder));
    }
    /**
     * handle the unfollow button when clicked
     *
     * @param actionEvent action event
     */
    public void handleUnFollowButtonOnClicked(ActionEvent actionEvent) {
        ObserverServiceImpl observerService = new ObserverServiceImpl();
        observerService.unfollow(findAccount(Client.username), findAccount(target));
        storingAccounts();
        storingFollowingList();
        storingTweets();
    }
}
