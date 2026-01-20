// package com.example.daaprojectdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    List<Integer> path;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void run() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                if (line.startsWith("PATH:")) {
                    path = parsePath(line.substring(5));
                    RideServer.broadcastMatch(this, path);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Integer> parsePath(String data) {
        List<Integer> list = new ArrayList<>();
        for (String s : data.split(",")) list.add(Integer.parseInt(s));
        return list;
    }

    void sendMatch(String type, List<Integer> otherPath) {
        out.println("MATCH:" + type + ":" + listToString(otherPath));
    }

    private String listToString(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (int i : list) sb.append(i).append(",");
        return sb.substring(0, sb.length() - 1);
    }
}