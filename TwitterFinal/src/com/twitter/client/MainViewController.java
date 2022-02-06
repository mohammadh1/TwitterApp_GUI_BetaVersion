package com.twitter.client;


import com.implementation.CommandParserServiceImpl;
import com.implementation.ConnectionServiceImpl;
import com.implementation.TimelineServiceImpl;
import com.twitter.server.LoadingFiles;
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

import static com.twitter.client.Client.*;
import static com.twitter.server.LoadingFiles.*;
import static com.twitter.server.Server.loginState;

/** Controls the main application screen */
public class MainViewController {
    public TextArea timelineTextArea;
    public MenuBar menuBar;

    public MainViewController() {
        new LoadingFiles();
        loadingTweets();
        loadingAccounts();
        loadingFollowingList();
    }

    @FXML
    public void initialize() {
        timelineTextArea.setWrapText(true);
        timelineTextArea.setEditable(false);
        /*// send request to server and get response to client
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
        }*/
        TimelineServiceImpl timelineService = new TimelineServiceImpl();
        if (timelineService.showMyTimeLine(findAccount(username)) != null) {
            ArrayList<Tweet> tweets = timelineService.showMyTimeLine(findAccount(username));
            StringBuilder stringBuilder = new StringBuilder();
            for (Tweet tweet : tweets) {
                System.out.println(tweet.getText());
                stringBuilder.append(tweet.getSender().getUsername());
                stringBuilder.append(" : ");
                stringBuilder.append('\n');
                stringBuilder.append(tweet.getText());
                stringBuilder.append('\n');
                stringBuilder.append('\n');
            }
            timelineTextArea.setText(String.valueOf(stringBuilder));
        }
        else
            timelineTextArea.setText("empty");
    }
    /**
     * handle the profile button when clicked
     *
     * @param actionEvent action event
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
     * @param actionEvent action event
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
     * @param actionEvent action event
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
     * @param actionEvent action event
     * @throws IOException
     */
    public void handleRefreshButtonOnClicked(ActionEvent actionEvent) throws IOException {
        this.initialize();
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
        Client.username = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) menuBar.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}