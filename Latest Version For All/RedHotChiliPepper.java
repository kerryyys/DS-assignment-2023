package JOJOLands.JOJO;

import java.util.*;

public class RedHotChiliPepper extends Player {

    public static List<GraphEdge> Kruskal(List<GraphEdge> edges) { // implement kruskal's logic

        // Sort edges by weight
        Collections.sort(edges);

        List<String> vertices = new ArrayList<>(); // to store vertices
        List<Integer> parent = new ArrayList<>();// to store parent of vertex

        // to store the edges of the graph
        List<GraphEdge> kruskal = new ArrayList<>();

        for (GraphEdge edge : edges) {
            String vertex1 = edge.vertex1; // source
            String vertex2 = edge.vertex2; // destination

            int index1 = getIndex(vertices, vertex1);
            int index2 = getIndex(vertices, vertex2);

            // if vertex not found in the list
            if (index1 == -1) {
                vertices.add(vertex1);
                parent.add(vertices.size() - 1);// adds to parent list as well
                index1 = vertices.size() - 1;
            }

            if (index2 == -1) {
                vertices.add(vertex2);
                parent.add(vertices.size() - 1);
                index2 = vertices.size() - 1;
            }

            // check if vertices belong to other vertices
            int parent1 = find(parent, index1);
            int parent2 = find(parent, index2);
            if (parent1 != parent2) {
                kruskal.add(edge);

                // merge components
                union(parent, parent1, parent2);
            }
        }

        return kruskal;
    }

    public static int getIndex(List<String> vertices, String vertex) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).equals(vertex)) {
                return i;
            }
        }
        return -1;
    }

    public static int find(List<Integer> parent, int vertex) {
        // find the parent
        if (parent.get(vertex) != vertex) { // check if have parent different from itself
            parent.set(vertex, find(parent, parent.get(vertex))); // find all parents recursively
        }
        return parent.get(vertex);
    }

    public static void union(List<Integer> parent, int x, int y) {
        parent.set(x, y); // set parent of vertex x to be vertex y
    }

    public void display(Graph<String, Integer> map) {

        Graph<String, Integer> a = Player.mainMap1;
        Graph<String, Integer> b = Player.mainMap2;
        Graph<String, Integer> c = Player.mainMap3;

        if (map.equals(Player.mainMap3)) {
            List<GraphEdge> edges = new ArrayList<>();
            edges.add(new GraphEdge("Town Hall", "Morioh Grand Hotel", 2));
            edges.add(new GraphEdge("Town Hall", "Green Dolphin Street Prison", 3));
            edges.add(new GraphEdge("Town Hall", "Libeccio", 7));

            edges.add(new GraphEdge("Morioh Grand Hotel", "Green Dolphin Street Prison", 2));
            edges.add(new GraphEdge("Morioh Grand Hotel", "San Giorgio Maggiore", 3));
            edges.add(new GraphEdge("Morioh Grand Hotel", "Joestar Mansion", 4));

            edges.add(new GraphEdge("Jade Garden", "Angelo Rock", 1));
            edges.add(new GraphEdge("Jade Garden", "Polnareff Land", 2));

            edges.add(new GraphEdge("Cafe Deux Magots", "Libeccio", 4));
            edges.add(new GraphEdge("Cafe Deux Magots", "DIO's Mansion", 1));
            edges.add(new GraphEdge("Cafe Deux Magots", "Vineyard", 4));

            edges.add(new GraphEdge("Trattoria Trussardi", "Joestar Mansion", 5));
            edges.add(new GraphEdge("Trattoria Trussardi", "Green Dolphin Street Prison", 4));
            edges.add(new GraphEdge("Trattoria Trussardi", "Libeccio", 1));

            edges.add(new GraphEdge("Angelo Rock", "Libeccio", 6));
            edges.add(new GraphEdge("Angelo Rock", "Polnareff Land", 2));

            edges.add(new GraphEdge("Savage Garden", "San Giorgio Maggiore", 6));
            edges.add(new GraphEdge("Savage Garden", "Vineyard", 4));

            edges.add(new GraphEdge("DIO's Mansion", "Libeccio", 2));
            edges.add(new GraphEdge("DIO's Mansion", "Polnareff Land", 2));

            System.out.println("Necessary Power Cables to be Upgraded: ");

            System.out.println("Necessary Power Cables to be Upgraded: ");
            List<GraphEdge> kruskal = Kruskal(edges);

            int totalLength = 0;

            for (int i = 0; i < kruskal.size(); i++) {
                GraphEdge edge = kruskal.get(i);
                totalLength += edge.weight;
                System.out.println((i + 1) + ". " + edge.vertex1 + " -- " + edge.vertex2 + "(" + edge.weight + "km)");
            }
            System.out.println();
            System.out.println("Total length: " + totalLength + "km");
        } else if (map.equals(Player.mainMap2)) {
            List<GraphEdge> edges = new ArrayList<>();
            edges.add(new GraphEdge("Town Hall", "Vineyard", 3));
            edges.add(new GraphEdge("Town Hall", "Libeccio", 2));
            edges.add(new GraphEdge("Town Hall", "Cafe Deux Magots", 4));
            edges.add(new GraphEdge("Town Hall", "Trattoria Trussardi", 6));
            edges.add(new GraphEdge("Morioh Grand Hotel", "Cafe Deux Magots", 6));
            edges.add(new GraphEdge("Morioh Grand Hotel", "Joestar Mansion", 4));
            edges.add(new GraphEdge("Jade Garden", "Cafe Deux Magots", 3));
            edges.add(new GraphEdge("Jade Garden", "Savage Garden", 4));
            edges.add(new GraphEdge("Jade Garden", "Joestar Mansion", 3));
            edges.add(new GraphEdge("Cafe Deux Magots", "Polnareff Land", 2));
            edges.add(new GraphEdge("Cafe Deux Magots", "Savage Garden", 5));
            edges.add(new GraphEdge("Trattoria Trussardi", "DIO's Mansion", 4));
            edges.add(new GraphEdge("Trattoria Trussardi", "Angelo Rock", 3));
            edges.add(new GraphEdge("Trattoria Trussardi", "Joestar Mansion", 5));
            edges.add(new GraphEdge("Green Dolphin Street Prison", "DIO's Mansion", 6));
            edges.add(new GraphEdge("Green Dolphin Street Prison", "Angelo Rock", 8));
            edges.add(new GraphEdge("Libeccio", "Vineyard", 3));
            edges.add(new GraphEdge("Angelo Rock", "DIO's Mansion", 1));
            edges.add(new GraphEdge("Savage Garden", "San Giorgio Maggiore", 6));
            edges.add(new GraphEdge("Joestar Mansion", "San Giorgio Maggiore", 5));

            System.out.println("Necessary Power Cables to be Upgraded: ");
            List<GraphEdge> kruskal = Kruskal(edges);

            int totalLength = 0;

            for (int i = 0; i < kruskal.size(); i++) {
                GraphEdge edge = kruskal.get(i);
                totalLength += edge.weight;
                System.out.println((i + 1) + ". " + edge.vertex1 + " -- " + edge.vertex2 + "(" + edge.weight + "km)");
            }
            System.out.println();
            System.out.println("Total length: " + totalLength + "km");
        }

        else {
            List<GraphEdge> edges = new ArrayList<>();
            edges.add(new GraphEdge("Town Hall", "Morioh Grand Hotel", 5));
            edges.add(new GraphEdge("Town Hall", "Jade Garden", 5));
            edges.add(new GraphEdge("Town Hall", "Cafe Deux Magots", 4));
            edges.add(new GraphEdge("Morioh Grand Hotel", "Trattoria Trussardi", 6));
            edges.add(new GraphEdge("Morioh Grand Hotel", "Jade Garden", 3));
            edges.add(new GraphEdge("Jade Garden", "San Giorgio Maggiore", 2));
            edges.add(new GraphEdge("Jade Garden", "Joestar Mansion", 2));
            edges.add(new GraphEdge("Cafe Deux Magots", "Jade Garden", 3));
            edges.add(new GraphEdge("Cafe Deux Magots", "Savage Garden", 4));
            edges.add(new GraphEdge("Cafe Deux Magots", "Polnareff Land", 4));
            edges.add(new GraphEdge("Savage Garden", "Polnareff Land", 6));
            edges.add(new GraphEdge("Savage Garden", "Joestar Mansion", 4));
            edges.add(new GraphEdge("Savage Garden", "Vineyard", 8));
            edges.add(new GraphEdge("Vineyard", "Joestar Mansion", 3));
            edges.add(new GraphEdge("Vineyard", "Libeccio", 6));
            edges.add(new GraphEdge("Vineyard", "DIO's Mansion", 3));
            edges.add(new GraphEdge("Libeccio", "Joestar Mansion", 6));
            edges.add(new GraphEdge("Libeccio", "DIO's Mansion", 2));
            edges.add(new GraphEdge("Libeccio", "Green Dolphin Street Prison", 3));
            edges.add(new GraphEdge("Libeccio", "San Giorgio Maggiore", 4));
            edges.add(new GraphEdge("Trattoria Trussardi", "San Giorgio Maggiore", 3));
            edges.add(new GraphEdge("Trattoria Trussardi", "Green Dolphin Street Prison", 6));
            edges.add(new GraphEdge("Angelo Rock", "DIO's Mansion", 3));
            edges.add(new GraphEdge("Angelo Rock", "Green Dolphin Street Prison", 2));

            System.out.println("Necessary Power Cables to be Upgraded: ");
            List<GraphEdge> kruskal = Kruskal(edges);

            int totalLength = 0;

            for (int i = 0; i < kruskal.size(); i++) {
                GraphEdge edge = kruskal.get(i);
                totalLength += edge.weight;
                System.out.println((i + 1) + ". " + edge.vertex1 + " -- " + edge.vertex2 + "(" + edge.weight + "km)");
            }
            System.out.println();
            System.out.println("Total length: " + totalLength + "km");

        }
    }
}

class GraphEdge implements Comparable<GraphEdge> {
    String vertex1;
    String vertex2;
    int weight;

    public GraphEdge(String vertex1, String vertex2, int weight) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
    }

    @Override
    public int compareTo(GraphEdge other) {
        return this.weight - other.weight;
    }
}