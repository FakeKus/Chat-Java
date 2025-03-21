package com.alexandre;

import java.net.Socket;

import com.alexandre.server.ChatServer;

public class Main {
    public static void main(String[] args) {
        

        App.main(args);
    }

    public static void acceptClients() {
        while(true) {
            try {
                Socket socket = ChatServer.serverSocket.accept();
            } catch (Exception e) {
                System.out.println("Erro ao aceitar clientes 001: " + e.getMessage());
            }
        }
    }
}