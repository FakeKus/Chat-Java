package com.alexandre.client;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.alexandre.server.SocketServer;

public class SocketClient extends Thread {

    @Override
    public void run() {
        try {
            Socket socket = new Socket("localhost", SocketServer.PORT);

            new Thread() {
                @Override
                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(socket.getInputStream()));

                        while (true) {
                            String message = reader.readLine();
                            System.out.println("Servidor: " + message);
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao ler mensagem client 002: " + e.getMessage());
                    }
                }
            }.start();

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), false);
            BufferedReader terminalReader = new BufferedReader(new java.io.InputStreamReader(System.in));

            String terminalMessage = "";
            while (true) {
                terminalMessage = terminalReader.readLine();
                if (terminalMessage == null || terminalMessage.length() == 0){continue;}
                writer.println(terminalMessage);
                writer.flush();
                if (terminalMessage.equalsIgnoreCase("/SAIR")) {
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao iniciar client 001: " + e.getMessage());
        }
    }
}
