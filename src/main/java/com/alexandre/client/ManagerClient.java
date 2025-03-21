package com.alexandre.client;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ManagerClient extends Thread {

    private Socket client;
    private BufferedReader reader;
    private PrintWriter writer;

    private String name;
    private static final Map<String, ManagerClient> clients = new HashMap<>();

    public ManagerClient(Socket socket) {
        this.client = socket;
        start();
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new java.io.InputStreamReader(client.getInputStream()));
            writer = new PrintWriter(client.getOutputStream(), false);

            writer.println("Conexão estabelecida com o servidor!");
            writer.println("Qual seu nome: ");
            writer.flush();

            String message = reader.readLine();
            this.name = message;
            clients.put(this.name.toLowerCase(), this);
            writer.println("Bem vindo " + this.name + "!");
            writer.flush();

            while (true) {
                message = reader.readLine();
                //System.out.println(this.name + ": " + message);
                if (message.equalsIgnoreCase("/SAIR")) {
                    writer.println(this.name + " saiu da conversa!");
                    writer.flush();
                    clients.remove(this.name.toLowerCase());
                    this.client.close();
                } else if (message.toUpperCase().startsWith("/PM ")) {
                    String[] split = message.split(" ");
                    String pmName = split[1].toLowerCase();
                    System.out.println(pmName);
                    String pmMessage = message.substring(pmName.length() + 4);
                    ManagerClient client = clients.get(pmName);
                    if (client == null) {
                        writer.println("Usuario não encontrado!");
                        writer.flush();
                    } else {
                        client.writer.println(this.name + " (privado): " + pmMessage);
                        client.writer.flush();
                    }
                } else {
                    writer.println(this.name + ": " + message);
                    writer.flush();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro com cliente 001: " + e.getMessage());
        }
    }
}
