package JOJOLands.JOJO;

import java.util.*;

    public class ThusSpokeRohanKishibe {
        private Graph<String, Integer> map;
    
        public ThusSpokeRohanKishibe(Graph<String, Integer> mapsGraph) {
            this.map = mapsGraph;
            Scanner sc = new Scanner(System.in);
    
            while (true) {
                System.out.println("First alphabet of location must in upper case (ex: Jade Garden)");
                System.out.print("Enter a location (or 'done' to finish): ");
                String locationInput = sc.nextLine();
    
                if (locationInput.equalsIgnoreCase("done")) {
                    return; //need return to hermit
                }
    
                String[] locations = locationInput.split(", ");
                List<String> uniqueLocations = new ArrayList<>(new HashSet<>(Arrays.asList(locations)));
                List<String> shortestPath = findShortestPath(uniqueLocations);
    
                System.out.println("======================================================================");
                System.out.println("Shortest Path:");
                printPath(shortestPath);
                int totalDistance = calculateDistance(shortestPath);
                System.out.printf(" (%d km)\n", totalDistance);
                System.out.println("======================================================================");
            }
        }

    private List<String> findShortestPath(List<String> locations) {
        List<String> shortestPath = new ArrayList<>();
        String startVertex = "Morioh Grand Hotel";

        for (int i = 0; i < locations.size(); i++) {
            String endVertex = locations.get(i);
            List<String> path = findPath(startVertex, endVertex);
            if (i > 0) {
                shortestPath.addAll(path.subList(1, path.size()));
            } else {
                shortestPath.addAll(path);
            }
            startVertex = endVertex;
        }

        return shortestPath;
    }

    private List<String> findPath(String startVertex, String endVertex) {
        PriorityQueue<VertexEntry> priorityQueue = new PriorityQueue<>();
        Map<String, Integer> distance = new HashMap<>();
        Map<String, String> previousVertex = new HashMap<>();

        for (Vertex<String, Integer> vertex : map.getAllVertices()) {
            String vertexName = vertex.getVertexInfo();
            distance.put(vertexName, Integer.MAX_VALUE);
            previousVertex.put(vertexName, null);
        }

        distance.put(startVertex, 0);
        priorityQueue.add(new VertexEntry(startVertex, 0));

        while (!priorityQueue.isEmpty()) {
            VertexEntry currentEntry = priorityQueue.poll();
            String currentVertex = currentEntry.getVertex();

            if (currentVertex.equals(endVertex)) {
                break;
            }

            int currentDistance = distance.get(currentVertex);

            List<String> neighbors = map.getNeighbours(currentVertex);
            for (String neighbor : neighbors) {
                int edgeWeight = map.getEdgeWeight(currentVertex, neighbor);
                int totalDistance = currentDistance + edgeWeight;

                if (totalDistance < distance.get(neighbor)) {
                    distance.put(neighbor, totalDistance);
                    previousVertex.put(neighbor, currentVertex);
                    priorityQueue.add(new VertexEntry(neighbor, totalDistance));
                }
            }
        }

        List<String> path = new ArrayList<>();
        String currentVertex = endVertex;

        while (currentVertex != null && !currentVertex.equals(startVertex)) {
            path.add(0, currentVertex);
            currentVertex = previousVertex.get(currentVertex);
        }

        path.add(0, startVertex);
        return path;
    }

    private int calculateDistance(List<String> path) {
        int distance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String currentVertex = path.get(i);
            String nextVertex = path.get(i + 1);
            Integer edgeWeight = map.getEdgeWeight(currentVertex, nextVertex);
            if (edgeWeight != null) {
                distance += edgeWeight;
            }
        }
        return distance;
    }

    private void printPath(List<String> path) {
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    private static class VertexEntry implements Comparable<VertexEntry> {
        private String vertex;
        private int distance;

        public VertexEntry(String vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        public String getVertex() {
            return vertex;
        }

        @Override
        public int compareTo(VertexEntry other) {
            return Integer.compare(distance, other.distance);
        }
    }
}