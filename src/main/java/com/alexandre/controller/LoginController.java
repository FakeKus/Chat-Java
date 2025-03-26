package com.alexandre.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.alexandre.Main;
import com.alexandre.client.ManagerClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController {

    private static final Map<String, String> users = new HashMap<>();
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonLogin;

    @FXML
    private Button buttonRegister;

    @FXML
    private ImageView imgPassword;

    @FXML
    private TextField textLogin;

    @FXML
    private PasswordField textPasswordHide;

    @FXML
    private TextField textPasswordShow;

    @FXML
    void buttonLoginEvent(ActionEvent event) {
        if (users.containsKey(textLogin.getText()) && users.get(textLogin.getText()).equals(textPasswordHide.getText())) {
            Stage chatStage = new Stage();
            Parent root;
            try {
                ManagerClient managerClient = new ManagerClient(Main.socketServer.getSocketClient());
                managerClient.addClient(textLogin.getText());

                //Carregando o arquivo FXML do Login
                FXMLLoader loaderChat = new FXMLLoader(getClass().getResource("/com/alexandre/view/Chat.fxml"));
                //Instanciando e setando o Controller do FXML
                ChatController chatController = new ChatController(managerClient);
                loaderChat.setController(chatController);
                //Criando a cena
                root = loaderChat.load();
                Scene chatScene = new Scene(root);

                //Carregando e definindo algumas configs da cena
                chatStage.setScene(chatScene);                                           //Definindo a cena
                chatStage.setResizable(false);                                     //Definindo a janela como não redimensionável
                chatStage.getIcons().add(new Image(getClass()
                    .getResourceAsStream("/com/alexandre/icons/MainLogo.png")));    //Definindo o Icone da janela
                chatStage.setTitle("Chat User: " + textLogin.getText());                 //Definindo o titulo da janela
                chatStage.show();                                                        //Mostrando a janela

                textLogin.setText("");
                textPasswordHide.setText("");
                textPasswordShow.setText("");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
                dialogoInfo.setTitle("ATENÇÃO");
                dialogoInfo.setHeaderText("Nome ou Senha inválidos!");
                dialogoInfo.showAndWait();
        }
    }

    @FXML
    void buttonRegisterEvent(ActionEvent event) {
        if (users.containsKey(textLogin.getText())) {
            //System.out.println("Usuário já cadastrado!");
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
                dialogoInfo.setTitle("ATENÇÃO");
                dialogoInfo.setHeaderText("Usuário já cadastrado!");
                dialogoInfo.setContentText("O usuário informado já está cadastrado no sistema!");
                dialogoInfo.showAndWait();
        } else {
            users.put(textLogin.getText(), textPasswordHide.getText());
            //System.out.println("Usuário cadastrado com sucesso!");
            Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                dialogoInfo.setTitle("");
                dialogoInfo.setHeaderText("Cadastro efetuado com sucesso!");
                dialogoInfo.showAndWait();
        }
    }

    @FXML
    void imgPasswordEvent(MouseEvent event) {
        if (textPasswordHide.isVisible()) {
            textPasswordHide.setVisible(false);
            textPasswordShow.setVisible(true);
            imgPassword.setImage(new javafx.scene.image.Image(getClass()
                .getResourceAsStream("/com/alexandre/icons/EyeShow.png")));
        } else {
            textPasswordHide.setVisible(true);
            textPasswordShow.setVisible(false);
            imgPassword.setImage(new javafx.scene.image.Image(getClass()
                .getResourceAsStream("/com/alexandre/icons/EyeHide.png")));
        }
    }

    @FXML
    void textPasswordHideEvent(KeyEvent event) {
        textPasswordShow.setText(textPasswordHide.getText());
    }

    @FXML
    void textPasswordShowEvent(KeyEvent event) {
        textPasswordHide.setText(textPasswordShow.getText());
    }

    @FXML
    void initialize() {
        assert buttonLogin != null : "fx:id=\"buttonLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert buttonRegister != null : "fx:id=\"buttonRegister\" was not injected: check your FXML file 'Login.fxml'.";
        assert imgPassword != null : "fx:id=\"imgPassword\" was not injected: check your FXML file 'Login.fxml'.";
        assert textLogin != null : "fx:id=\"textLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert textPasswordHide != null : "fx:id=\"textPasswordHide\" was not injected: check your FXML file 'Login.fxml'.";
        assert textPasswordShow != null : "fx:id=\"textPasswordShow\" was not injected: check your FXML file 'Login.fxml'.";
        users.put("admin", "root");
        users.put("user", "65477");

        imgPassword.setImage(new javafx.scene.image.Image(getClass()
            .getResourceAsStream("/com/alexandre/icons/EyeHide.png")));
    }

}