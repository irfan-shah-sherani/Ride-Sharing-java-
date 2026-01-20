// package com.example.daaprojectdemo;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {

    private Graph graph;

    // Make sure these fx:id match the FXML exactly
    @FXML
    private Line lahore_islamabad;
    @FXML
    private Line islamabad_DIkhan;
    @FXML
    private Line DIkhan_faislabad;
    @FXML
    private Line faislabad_iyari;
    @FXML
    private Line quetta_DIkhan;
    @FXML
    private Line muzafarabad_faislabad;
    @FXML
    private Line islamabad_iyari;
    @FXML
    private Line peshawar_pindi;
    @FXML
    private Line pindi_DIkhan;

    private Map<String, Line> edgeMap = new HashMap<>();

    // track paths so we can clear or avoid overwriting
    private List<Integer> currentUserPath = null;

    @FXML
    public void initialize() {
        graph = new Graph(9);

        // Add cities
        graph.addCity(0, "Lahore");
        graph.addCity(1, "Islamabad");
        graph.addCity(2, "Peshawar");
        graph.addCity(3, "Quetta");
        graph.addCity(4, "DIKhan");
        graph.addCity(5, "Rawalpindi"); 
        graph.addCity(6, "Muzaffarabad");
        graph.addCity(7, "Faisalabad");
        graph.addCity(8, "Lyari");

        // Add edges
        graph.addEdge(0, 1, 10);
        graph.addEdge(1, 4, 2);  // Islamabad - DIKhan (was 1,5)
        graph.addEdge(4, 7, 5);  // DIKhan - Faisalabad (was 5,7)
        graph.addEdge(7, 8, 10);
        graph.addEdge(3, 4, 10); // Quetta - DIKhan (was 3,5)
        graph.addEdge(6, 7, 10);
        graph.addEdge(1, 8, 20);
        graph.addEdge(2, 5, 5);  // Peshawar - Pindi (was 2,4)
        graph.addEdge(4, 5, 10); // DIKhan - Pindi

        // Map lines
        edgeMap.put("0-1", lahore_islamabad);
        edgeMap.put("1-4", islamabad_DIkhan);
        edgeMap.put("4-7", DIkhan_faislabad);
        edgeMap.put("7-8", faislabad_iyari);
        edgeMap.put("3-4", quetta_DIkhan);
        edgeMap.put("6-7", muzafarabad_faislabad);
        edgeMap.put("1-8", islamabad_iyari);
        edgeMap.put("2-5", peshawar_pindi);
        edgeMap.put("4-5", pindi_DIkhan);
    }
    private RideClient rideClient;

public void setRideClient(RideClient client) {
    this.rideClient = client;
    client.listen(this); // start listening server messages
}
    // clear previously drawn user path (only reset edges that were painted GREEN)
    private void clearUserPath() {
        if (currentUserPath == null) return;
        for (int i = 0; i < currentUserPath.size() - 1; i++) {
            int a = currentUserPath.get(i);
            int b = currentUserPath.get(i + 1);

            String key1 = a + "-" + b;
            String key2 = b + "-" + a;

            Line line = edgeMap.getOrDefault(key1, edgeMap.get(key2));
            if (line != null && Color.GREEN.equals(line.getStroke())) {
                line.setStroke(Color.BLACK);
                line.setStrokeWidth(2);
            }
        }
        currentUserPath = null;
    }

    public void findPath(int src, int dest) {

        System.out.println("Controller findPath() function");

        System.out.println("findPath called with src=" + src + ", dest=" + dest);

        List<Integer> path = graph.getPath(src, dest);
        System.out.println("Computed path: " + path);
        if (path == null || path.isEmpty()) {
            System.out.println("No path found (path is null or empty)");
            return;
        }

        // Clear previous user path so it doesn't mix with new selection
        clearUserPath();

        // Color the new user path and remember it
        for (int i = 0; i < path.size() - 1; i++) {
            int a = path.get(i);
            int b = path.get(i + 1);

            String key1 = a + "-" + b;
            String key2 = b + "-" + a;

            Line line = edgeMap.getOrDefault(key1, edgeMap.get(key2));
            System.out.println("Checking edge " + a + "-" + b + " (keys: " + key1 + "," + key2 + ") -> line=" + (line != null));
            if (line != null) {
                line.setStroke(Color.GREEN);
                line.setStrokeWidth(4);
            } else {
                System.out.println("WARNING: No Line fx:id mapped for edge " + a + "-" + b);
            }
        }
        currentUserPath = path;

        // send this path to server for matching (if connected)
        if (rideClient != null) {
            rideClient.sendPath(path);
        }
    }
    public void showOtherPath(List<Integer> path, String matchType) {

        // Other users' paths should never overwrite the current user's green path.
        // Always color other users' path RED; use matchType only for logging/extra behavior.
        Color color = Color.RED;
        System.out.println("showOtherPath called, type=" + matchType + ", path=" + path);

        for (int i = 0; i < path.size() - 1; i++) {
            int a = path.get(i);
            int b = path.get(i + 1);

            String key1 = a + "-" + b;
            String key2 = b + "-" + a;

            Line line = edgeMap.getOrDefault(key1, edgeMap.get(key2));
            if (line != null) {
                // If this edge is already marked as the user's path (GREEN), don't overwrite it.
                if (Color.GREEN.equals(line.getStroke())) {
                    System.out.println("Skipping overwrite of user's edge " + a + "-" + b);
                    continue;
                }
                line.setStroke(color);
                line.setStrokeWidth(4);
            } else {
                System.out.println("WARNING: No Line fx:id mapped for other user's edge " + a + "-" + b);
            }
        }
    }
}
