package com.alexandre.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ChatController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea areaClientMessage;

    @FXML
    private TextArea areaLog;

    @FXML
    private Button buttonPrivate;

    @FXML
    private ImageView imgAreaClientMessage;

    @FXML
    private Label labelMessage;

    @FXML
    private ListView<?> listViewClients;

    @FXML
    void buttonPrivateEvent(ActionEvent event) {

    }

    @FXML
    void imgAreaClientMessageEvent(MouseEvent event) {

    }

    @FXML
    void initialize() {
        assert areaClientMessage != null : "fx:id=\"areaClientMessage\" was not injected: check your FXML file 'MainChat.fxml'.";
        assert areaLog != null : "fx:id=\"areaLog\" was not injected: check your FXML file 'MainChat.fxml'.";
        assert buttonPrivate != null : "fx:id=\"buttonPrivate\" was not injected: check your FXML file 'MainChat.fxml'.";
        assert imgAreaClientMessage != null : "fx:id=\"imgAreaClientMessage\" was not injected: check your FXML file 'MainChat.fxml'.";
        assert labelMessage != null : "fx:id=\"labelMessage\" was not injected: check your FXML file 'MainChat.fxml'.";
        assert listViewClients != null : "fx:id=\"listViewClients\" was not injected: check your FXML file 'MainChat.fxml'.";

        imgAreaClientMessage.setImage(new javafx.scene.image.Image(getClass()
            .getResourceAsStream("/com/alexandre/icons/Send.png")));
    }

}