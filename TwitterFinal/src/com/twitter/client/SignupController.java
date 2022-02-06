package com.twitter.client;

import com.implementation.CommandParserServiceImpl;
import com.implementation.ConnectionServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static com.twitter.client.Client.ip;
import static com.twitter.client.Client.port;

public class SignupController {
    @FXML public TextField passwordTextField;
    @FXML public TextField usernameTextField;
    @FXML public TextField lastnameTextField;
    @FXML public TextField birthTextField;
    @FXML public TextArea bioTextArea;
    @FXML public TextField firstnameTextField;
    @FXML public Text textBoxForMessage;

    /**
     * handle the back button when clicked
     *
     * @param actionEvent event
     * @throws IOException
     */
    public void handleBackButtonOnClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * handle the signup button when clicked
     *
     * @param actionEvent event
     * @throws IOException
     */
    public void handleSignupButtonOnClicked(ActionEvent actionEvent) throws IOException {
        if (this.usernameTextField != null && this.passwordTextField != null &&
                this.birthTextField != null && this.lastnameTextField != null &&
                this.firstnameTextField != null && this.bioTextArea != null) {
            String username = this.usernameTextField.getText();
            String password = this.passwordTextField.getText();
            String firstname = this.firstnameTextField.getText();
            String lastname = this.lastnameTextField.getText();
            String bio = this.bioTextArea.getText();
            String birth = this.birthTextField.getText();
            CommandParserServiceImpl commandParserService = new CommandParserServiceImpl();
            String nameFile = commandParserService.signupLines(username, password, firstname, lastname, birth, bio);
            File file = new File(nameFile);
            new Client();
            ConnectionServiceImpl connectionService = new ConnectionServiceImpl(ip,port,file);
            Object object = connectionService.ConnectionServ();
            if (object.equals(true)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else {
                this.textBoxForMessage.setText("something is wrong, try again");
            }
        }
    }
}
