package com.alexandre.controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class LoginController {

    private static final Map<String, String> users = Map.of(
        "admin", "root",
        "user", "65477"
    );
    
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
            System.out.println("Login efetuado com sucesso!");
        } else {
            System.out.println("Login ou senha incorretos!");
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

        imgPassword.setImage(new javafx.scene.image.Image(getClass()
            .getResourceAsStream("/com/alexandre/icons/EyeHide.png")));
    }

}