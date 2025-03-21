package com.alexandre.client;

import java.net.Socket;

public class Client {

    private final static int port = 8818;

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", port);) {
            System.out.println("Conectado ao servidor na porta: " + port);

        } catch (Exception e) {
            System.out.println("Erro ao conectar cliente 001: " + e.getMessage());
            System.exit(1);

        }
    }
}
