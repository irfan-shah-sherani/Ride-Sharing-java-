// package src;
import java.util.*;

class Graph {
    int V;
    int[][] graph;
    String[] cities;

    Graph(int v) {
        V = v;
        graph = new int[v][v];
        cities = new String[v];

        // initialize matrix with infinity
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                graph[i][j] = (i == j) ? 0 : 999999;
            }
        }
    }
    public List<Integer> getPath(int src, int dest) {
        int[] parent = shortestPathReturnParent(src); // reuse your existing method
        List<Integer> path = new ArrayList<>();

        int current = dest;
        while (current != -1) {
            path.add(current);
            current = parent[current];
        }

        Collections.reverse(path); // because we built it from dest to src
        return path;
    }

    void addCity(int index, String name) {
        cities[index] = name;
    }

    void addEdge(int a, int b, int w) {
        graph[a][b] = w;
        graph[b][a] = w;
    }

    // ---- Dijkstra with PATH ----
    void shortestPath(int src, int dest) {
        int[] dist = new int[V];
        boolean[] visited = new boolean[V];
        int[] parent = new int[V];

        Arrays.fill(dist, 999999);
        Arrays.fill(parent, -1);

        dist[src] = 0;

        for (int count = 0; count < V - 1; count++) {
            int u = minDistance(dist, visited);
            visited[u] = true;

            for (int v = 0; v < V; v++) {
                if (!visited[v] &&
                        graph[u][v] != 999999 &&
                        dist[u] + graph[u][v] < dist[v]) {

                    dist[v] = dist[u] + graph[u][v];
                    parent[v] = u;
                }
            }
        }

        printResult(src, dest, dist, parent);
    }

    int minDistance(int[] dist, boolean[] visited) {
        int min = 999999, index = -1;
        for (int i = 0; i < V; i++) {
            if (!visited[i] && dist[i] < min) {
                min = dist[i];
                index = i;
            }
        }
        return index;
    }

    void printResult(int src, int dest, int[] dist, int[] parent) {
        System.out.println("\nShortest distance:");
        System.out.println(cities[src] + " → " + cities[dest] + " = " + dist[dest]);

        System.out.println("\nShortest Path:");
        printPath(dest, parent);
        System.out.println();
    }

    void printPath(int v, int[] parent) {
        if (v == -1)
            return;
        printPath(parent[v], parent);
        System.out.print(cities[v] + "  ");
    }

    int[] shortestPathReturnParent(int src) {
        int[] dist = new int[V];
        boolean[] visited = new boolean[V];
        int[] parent = new int[V];

        Arrays.fill(dist, 999999);
        Arrays.fill(parent, -1);

        dist[src] = 0;

        for (int count = 0; count < V - 1; count++) {
            int u = minDistance(dist, visited);
            visited[u] = true;

            for (int v = 0; v < V; v++) {
                if (!visited[v] && graph[u][v] != 999999 &&
                        dist[u] + graph[u][v] < dist[v]) {

                    dist[v] = dist[u] + graph[u][v];
                    parent[v] = u;
                }
            }
        }

        return parent;
    }

    int getCityIndex(String name) {
        for (int i = 0; i < cities.length; i++)
            if (cities[i].equals(name))
                return i;
        return -1;
    }
}