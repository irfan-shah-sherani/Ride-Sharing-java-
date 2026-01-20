// package com.example.daaprojectdemo;

import java.io.*;
import java.net.*;
import java.util.*;

public class RideServer {

    private static final List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5983);
        System.out.println("Ride Server started listenining");

        while (true) {
            Socket socket = serverSocket.accept();
            ClientHandler handler = new ClientHandler(socket);
            clients.add(handler);
            handler.start();
        }
    }

    static synchronized void broadcastMatch(ClientHandler sender, List<Integer> senderPath) {

        for (ClientHandler other : clients) {

            if (other != sender && other.path != null) {

                boolean overlap = false;

                // simple overlap check (NO HashSet)
                for (int a : senderPath) {
                    for (int b : other.path) {
                        if (a == b) {
                            overlap = true;
                            break;
                        }
                    }
                    if (overlap) break;
                }

                if (overlap) {
                    String type;

                    if (senderPath.equals(other.path)) {
                        type = "FULL";
                    } else {
                        type = "PARTIAL";
                    }

                    other.sendMatch(type, senderPath);
                    sender.sendMatch(type, other.path);
                }
            }
        }
    }
}
