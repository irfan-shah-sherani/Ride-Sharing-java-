// package src;
import java.util.Arrays;

public class Dijkstra {

    public static void shortestPath(int[][] graph, int src) {
        int V = graph.length;
        boolean[] visited = new boolean[V];
        int[] dist = new int[V];

        Arrays.fill(dist, 999999);
        dist[src] = 0;

        for (int i = 0; i < V - 1; i++) {

            // pick the minimum distance unvisited node
            int u = minDistance(dist, visited);
            visited[u] = true;

            for (int v = 0; v < V; v++) {
                if (!visited[v] && graph[u][v] != 999999 &&
                    dist[u] + graph[u][v] < dist[v]) {

                    dist[v] = dist[u] + graph[u][v];
                }
            }
        }

        printResult(dist);
    }

    private static int minDistance(int[] dist, boolean[] visited) {
        int min = 999999, index = -1;

        for (int i = 0; i < dist.length; i++) {
            if (!visited[i] && dist[i] < min) {
                min = dist[i];
                index = i;
            }
        }
        return index;
    }

    private static void printResult(int[] dist) {
        System.out.println("City\tDistance from Source");
        for (int i = 0; i < dist.length; i++) {
            System.out.println(i + "\t\t" + dist[i]);
        }
    }
}
