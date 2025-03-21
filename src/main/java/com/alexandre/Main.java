package com.alexandre;

import com.alexandre.client.SocketClient;
import com.alexandre.server.SocketServer;

public class Main {
    public static void main(String[] args) {
        new SocketServer().start();
        new SocketClient().start();
        //App.main(args);
    }
}