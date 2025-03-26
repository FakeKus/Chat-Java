package com.alexandre;

import com.alexandre.client.ManagerClient;
import com.alexandre.client.SocketClient;
import com.alexandre.server.SocketServer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class Main {

    public static SocketServer socketServer;
    public static ObservableMap<String, ManagerClient> clients = FXCollections.observableHashMap();
    public static ObservableList<String> messages = FXCollections.observableArrayList();

    public static void main(String[] args) {
        socketServer = new SocketServer();
        new SocketClient().start();
        App.main(args);
    }
}