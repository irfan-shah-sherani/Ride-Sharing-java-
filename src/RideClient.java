// package com.example.daaprojectdemo;

import java.io.*;
import java.net.*;
import java.util.*;

public class RideClient {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public RideClient() throws IOException {
        socket = new Socket("localhost", 5983); // CONNECT TO SERVER
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Connected to Ride Server");
    }

   public void listen(Controller controller) {
    new Thread(() -> {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                if (line.startsWith("MATCH:")) {
                    // New format: MATCH:<TYPE>:<path> e.g. MATCH:FULL:0,1,5
                    // Fallback: MATCH:<path>
                    String payload = line.substring(6);
                    String type;
                    String pathStr;
                    if (payload.contains(":")) {
                        String[] parts = payload.split(":", 2);
                        type = parts[0];
                        pathStr = parts[1];
                    } else {
                        // older server format (no type) -> treat as PARTIAL
                        type = "PARTIAL";
                        pathStr = payload;
                    }
                    List<Integer> otherPath = parsePath(pathStr);
                    controller.showOtherPath(otherPath, type);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }).start();
}

    // send user's computed path to server
    public void sendPath(List<Integer> path) {
        if (out != null) {
            out.println("PATH:" + listToString(path));
        }
    }

    private String listToString(List<Integer> list) {
        return String.join(",", list.stream().map(String::valueOf).toList());
    }

    private List<Integer> parsePath(String data) {
        List<Integer> list = new ArrayList<>();
        for (String s : data.split(",")) list.add(Integer.parseInt(s));
        return list;
    }
}
