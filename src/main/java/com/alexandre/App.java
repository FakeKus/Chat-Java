package com.alexandre;

import com.alexandre.controller.LoginController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {

        try {

            //Carregando o arquivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/alexandre/view/Login.fxml"));
            //Instanciando e setando o Controller do FXML
            LoginController loginController = new LoginController();
            loader.setController(loginController);
            //Criando a cena
            Parent root = loader.load();
            Scene scene = new Scene(root);
            
            //Carregando e definindo algumas configs da cena
            primaryStage.setScene(scene);                                            //Definindo a cena
            primaryStage.setResizable(false);                                  //Definindo a janela como não redimensionável
            primaryStage.getIcons().add(new Image(getClass()
                .getResourceAsStream("/com/alexandre/icons/MainLogo.png")));    //Definindo o Icone da janela
            primaryStage.setTitle("Chat - Java");                              //Definindo o titulo da janela
            primaryStage.show();                                                     //Mostrando a janela
        } catch(Exception e) {

            System.out.println("Erro - 01");  //TO - DO
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {launch(args);}
}
