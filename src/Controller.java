import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {

    private Graph graph;

    // Make sure these fx:id match the FXML exactly
    @FXML private Line lahore_islamabad;
    @FXML private Line islamabad_DIkhan;
    @FXML private Line DIkhan_faislabad;
    @FXML private Line faislabad_iyari;
    @FXML private Line quetta_DIkhan;
    @FXML private Line muzafarabad_faislabad;
    @FXML private Line islamabad_iyari;
    @FXML private Line peshawar_pindi;
    @FXML private Line pindi_DIkhan;

    private Map<String, Line> edgeMap = new HashMap<>();

    @FXML
    public void initialize() {
        graph = new Graph(9);

        // Add cities
        graph.addCity(0, "Lahore");
        graph.addCity(1, "Islamabad");
        graph.addCity(2, "Peshawar");
        graph.addCity(3, "Quetta");
        graph.addCity(4, "Pindi");
        graph.addCity(5, "DIKhan");
        graph.addCity(6, "Muzafarabad");
        graph.addCity(7, "Faislabad");
        graph.addCity(8, "Iyari");

        // Add edges
        graph.addEdge(0, 1, 10);
        graph.addEdge(1, 5, 2);
        graph.addEdge(5, 7, 5);
        graph.addEdge(7, 8, 10);
        graph.addEdge(3, 5, 10);
        graph.addEdge(6, 7,10);
        graph.addEdge(1, 8, 20);
        graph.addEdge(2, 4, 5);
        graph.addEdge(4, 5, 10);

        // Map lines
        edgeMap.put("0-1", lahore_islamabad);
        edgeMap.put("1-5", islamabad_DIkhan);
        edgeMap.put("5-7", DIkhan_faislabad);
        edgeMap.put("7-8", faislabad_iyari);
        edgeMap.put("3-5", quetta_DIkhan);
        edgeMap.put("6-7", muzafarabad_faislabad);
        edgeMap.put("1-8", islamabad_iyari);
        edgeMap.put("2-4", peshawar_pindi);
        edgeMap.put("4-5", pindi_DIkhan);
    }

    public void findPath(int src, int dest) {
        // Reset lines
        // for (Line l : edgeMap.values()) {
        //     if (l != null) {
        //         l.setStroke(Color.BLACK);
        //         l.setStrokeWidth(2);
        //     }
        // }

        // Get path from Graph
        List<Integer> path = graph.getPath(src, dest);
        if (path == null || path.isEmpty()) return;

        // Color the path
        for (int i = 0; i < path.size() - 1; i++) {
            int a = path.get(i);
            int b = path.get(i + 1);

            String key1 = a + "-" + b;
            String key2 = b + "-" + a;

            Line line = edgeMap.getOrDefault(key1, edgeMap.get(key2));
            if (line != null) {
                line.setStroke(Color.GREEN);
                line.setStrokeWidth(4);
            }
        }
    }
}
