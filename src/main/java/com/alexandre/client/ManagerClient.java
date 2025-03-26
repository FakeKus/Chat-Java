package com.alexandre.client;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.alexandre.Main;
import com.alexandre.controller.ChatController;

public class ManagerClient extends Thread {

    private ChatController chatController;

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private static Map<String, PrintWriter> clientWriters = new HashMap<>();

    private String name;
    private String areaMessage;

    public ManagerClient(Socket socket) {
        this.socket = socket;
        start();
    }
    public ManagerClient() {
        start();
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(socket.getInputStream()));) {
            writer = new PrintWriter(socket.getOutputStream(), false);

            // Registra o cliente
            name = reader.readLine();
            synchronized (clientWriters) {
                clientWriters.put(name, writer);
            }

            String message;
            while ((message = reader.readLine()) != null) {
                if (message.startsWith("PRIVADO")) {
                    // Mensagem privada no formato: "PRIVADO <destinatário> <mensagem>"
                    String[] parts = message.split(" ", 3);
                    if (parts.length == 3) {
                        String recipient = parts[1];
                        String privateMessage = parts[2];

                        // Envia a mensagem apenas para o destinatário
                        synchronized (clientWriters) {
                            PrintWriter recipientWriter = clientWriters.get(recipient);
                            if (recipientWriter != null) {
                                recipientWriter.println("(Privado) " + name + ": " + privateMessage);
                            }
                        }
                    }
                } else {
                    // Mensagem pública (broadcast)
                    synchronized (clientWriters) {
                        for (PrintWriter clientWriter : clientWriters.values()) {
                            clientWriter.println(name + ": " + message);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Remove o cliente ao desconectar
            synchronized (clientWriters) {
                clientWriters.remove(name);
            }
        }
    }

    /*@Override
    public void run() {
        try {
            reader = new BufferedReader(new java.io.InputStreamReader(client.getInputStream()));
            writer = new PrintWriter(client.getOutputStream(), false);

            //writer.println("Conexão estabelecida com o servidor!");
            //writer.println("Qual seu nome: ");
            //writer.flush();

            //String message = reader.readLine();
            //this.name = message;
            //clients.put(this.name.toLowerCase(), this);
            //writer.println("Bem vindo " + this.name + "!");
            //writer.flush();

            while (true) {
                String message = this.name + ": " + areaMessage;
                //System.out.println(this.name + ": " + message);
                if (message.equalsIgnoreCase("/SAIR")) {
                    writer.println(this.name + " saiu da conversa!");
                    writer.flush();
                    Main.clients.remove(this.name.toLowerCase());
                    this.client.close();
                } else if (message.toUpperCase().startsWith("/PM ")) {
                    String[] split = message.split(" ");
                    String pmName = split[1].toLowerCase();
                    System.out.println(pmName);
                    String pmMessage = message.substring(pmName.length() + 4);
                    ManagerClient client = Main.clients.get(pmName);
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

                    chatController.addMessage(message);
                    this.areaMessage = "";
                    this.chatController = null;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro com cliente 001: " + e.getMessage());
        }
    }*/

    public void addClient(String name) {
        this.name = name.toLowerCase();
        Main.clients.put(this.name, this);
    }

    public void setAreaMessage(String message, ChatController controller) {
        // Formata a mensagem
        String formattedMessage = this.name + ": " + message;

        // Envia a mensagem ao servidor
        sendMessageToServer(formattedMessage);
    }

    private void sendMessageToServer(String message) {
        if (writer != null) {
            writer.println(message);
            writer.flush();
        }
    }

    public void sendPrivateMessage(String client, String message) {
        if (writer != null) {
            // Formata a mensagem para indicar que é privada e inclui o destinatário
            writer.println("PRIVADO " + client + " " + message);
        }
    }

    public String getClientName() {
        return name;
    }
}
