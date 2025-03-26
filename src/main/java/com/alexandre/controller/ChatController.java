package com.alexandre.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.alexandre.Main;
import com.alexandre.client.ManagerClient;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ChatController {

    private ManagerClient managerClient;

    private static ObservableList<String> names = FXCollections.observableArrayList();

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
    private ListView<String> listViewClients;

    public ChatController(ManagerClient managerClient) {
        this.managerClient = managerClient;
    }

    @FXML
    void buttonPrivateEvent(ActionEvent event) {
        // Obtém o cliente selecionado na ListView
        String selectedClient = listViewClients.getSelectionModel().getSelectedItem();

        if (selectedClient.isEmpty() || selectedClient.isBlank()) {
            // Caso nenhum cliente esteja selecionado, exibe uma mensagem de erro
            areaLog.appendText("Servidor: Selecione um cliente para enviar uma mensagem privada.\n");
            return;
        }

        // Obtém a mensagem do campo de texto
        String message = areaClientMessage.getText();

        if (message.isEmpty() || message.isBlank()) {
            // Caso a mensagem esteja vazia, exibe uma mensagem de erro
            areaLog.appendText("Servidor: Digite uma mensagem para enviar.\n");
            return;
        }

        // Formata a mensagem como privada
        String formattedMessage = "(Privado) " + managerClient.getClientName() + " para " + selectedClient + ": " + message;

        // Envia a mensagem privada para o servidor via ManagerClient
        this.managerClient.sendPrivateMessage(selectedClient, message);

        // Exibe a mensagem na área de log da instância atual
        areaLog.appendText(formattedMessage + "\n");

        // Limpa o campo de entrada
        areaClientMessage.clear();
    }

    @FXML
    void imgAreaClientMessageEvent(MouseEvent event) {
        // Obtém a mensagem do campo de texto
        String message = areaClientMessage.getText();

        if (!(message.isEmpty() || message.isBlank())){
            // Formata a mensagem
            String formattedMessage = managerClient.getClientName() + ": " + message;

            // Adiciona a mensagem à lista global
            Main.messages.add(formattedMessage);

            // Envia a mensagem para o servidor via ManagerClient
            this.managerClient.setAreaMessage(message, this);

            // Limpa o campo de entrada
            areaClientMessage.clear();
        }
    }

    @FXML
    void initialize() {
        assert areaClientMessage != null : "fx:id=\"areaClientMessage\" was not injected: check your FXML file 'MainChat.fxml'.";
        assert areaLog != null : "fx:id=\"areaLog\" was not injected: check your FXML file 'MainChat.fxml'.";
        assert buttonPrivate != null : "fx:id=\"buttonPrivate\" was not injected: check your FXML file 'MainChat.fxml'.";
        assert imgAreaClientMessage != null : "fx:id=\"imgAreaClientMessage\" was not injected: check your FXML file 'MainChat.fxml'.";
        assert labelMessage != null : "fx:id=\"labelMessage\" was not injected: check your FXML file 'MainChat.fxml'.";
        assert listViewClients != null : "fx:id=\"listViewClients\" was not injected: check your FXML file 'MainChat.fxml'.";

        new Thread() {
            @Override
            public void run() {
                Main.clients.addListener((MapChangeListener<String, ManagerClient>) change -> {
                    names.clear();
                    Main.clients.forEach((k, v) -> {
                        names.add(k);
                    });
                    updateListView();
                });
                Main.messages.addListener((javafx.collections.ListChangeListener<String>) change -> {
                    while (change.next()) {
                        if (change.wasAdded()) {
                            for (String message : change.getAddedSubList()) {
                                areaLog.appendText(message + "\n");
                            }
                        }
                    }
                });
            }
        }.start();

        updateListView();
        imgAreaClientMessage.setImage(new javafx.scene.image.Image(getClass()
            .getResourceAsStream("/com/alexandre/icons/Send.png")));
    }

    public void addMessage(String message) {
        areaLog.appendText(message + "\n");
    }

    private void updateListView() {
        listViewClients.getItems().clear();
        listViewClients.getItems().setAll(names);
    }
}