package com.alexandre;

import com.alexandre.controller.LoginController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    public static Scene loginScene, chatScene;
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        App.primaryStage = primaryStage;

        try {

            //Carregando o arquivo FXML do Login
            FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/com/alexandre/view/Login.fxml"));
            //Instanciando e setando o Controller do FXML
            LoginController loginController = new LoginController();
            loaderLogin.setController(loginController);
            //Criando a cena
            Parent loginRoot = loaderLogin.load();
            loginScene = new Scene(loginRoot);
            
            //Carregando e definindo algumas configs da cena
            App.primaryStage.setScene(loginScene);                                   //Definindo a cena
            App.primaryStage.setResizable(false);                              //Definindo a janela como não redimensionável
            App.primaryStage.getIcons().add(new Image(getClass()
                .getResourceAsStream("/com/alexandre/icons/MainLogo.png")));    //Definindo o Icone da janela
            App.primaryStage.setTitle("Chat - Java");                          //Definindo o titulo da janela
            App.primaryStage.show();                                                 //Mostrando a janela
        } catch(Exception e) {

            System.out.println("Erro - 01");  //TO - DO
            e.printStackTrace();
        }
    }

    public static void changeScene(String scene) {
        switch (scene) {
            case "login":
                App.primaryStage.setScene(loginScene);
                break;
            case "chat":
                App.primaryStage.setScene(chatScene);
                break;        
            default:
                break;
        }
    }

    public static void main(String[] args) {launch(args);}
}
