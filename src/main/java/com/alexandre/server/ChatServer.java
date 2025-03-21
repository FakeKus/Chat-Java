package com.alexandre.server;

import java.net.ServerSocket;

public class ChatServer {

    private final static int port = 8818;
    public static ServerSocket serverSocket;
    
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor iniciado na porta: " + port);
            
        } catch (Exception e) {
            System.out.println("Erro ao iniciar o servidor 001: " + e.getMessage());
            System.exit(1);
            
        }
    }
}
