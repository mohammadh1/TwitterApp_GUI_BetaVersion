module TwitterFinal {
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.web;
    requires com.google.gson;
    requires json.simple;
    requires java.logging;

    opens com.twitter.client;
}