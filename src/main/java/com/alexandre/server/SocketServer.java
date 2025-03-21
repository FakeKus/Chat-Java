package com.alexandre.server;

import java.net.ServerSocket;
import java.net.Socket;

import com.alexandre.client.ManagerClient;

public class SocketServer extends Thread {

    public static final int PORT = 8282;

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server iniciado na porta: " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                new ManagerClient(socket);
            }
        } catch (Exception e) {
            System.out.println("Erro ao iniciar o server 001: " + e.getMessage());
            try {
                if (serverSocket != null){serverSocket.close();}
            } catch (Exception e2) {
                System.out.println("Erro ao fechar o server 002: " + e2.getMessage());
            }
        }
    }
}
