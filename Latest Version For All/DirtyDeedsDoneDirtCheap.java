package JOJOLands.JOJO;

import java.util.*;

public class DirtyDeedsDoneDirtCheap {

    private Scanner sc = new Scanner(System.in);
    private static Graph<String, Integer> map = new Graph<>();
    List<List<String>> paths = new ArrayList<>();

    public DirtyDeedsDoneDirtCheap() {
    }

    public void RunDDDDC(){
        List<List<String>> paths = new ArrayList<>();
        List<String> currentPath = new ArrayList<>();

        System.out.print("Source:");
        String source = sc.next();
        System.out.print("\nDestination: ");
        String destination = sc.next();
        System.out.println("\n======================================================================");

        currentPath.add(source);
        findPossiblePaths(source, destination, currentPath, paths);

        List<List<String>> possiblePaths = new ArrayList<>();
        System.out.println("Top Three Shortest Paths:");
        for (int i = 0; i < Math.min(3, paths.size()); i++) {
            possiblePaths.add(paths.get(i));
        }

        System.out.println();

        int n = paths.size();
        List<Integer> indices1 = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            indices1.add(i);
        }

        sortDistance(possiblePaths, indices1);
        System.out.println("Top Three Shortest Paths:");
        for (int i = 0; i < Math.min(3, paths.size()); i++) {
            double distance = calculateDistance(paths.get(i));
            System.out.printf("%d. ", i + 1);
            for (int j = 0; j < paths.get(i).size(); j++) {
                System.out.print(paths.get(i).get(j));
                if (j < paths.get(i).size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.printf(" (%.2f km)\n", distance);
        }
        System.out.println("======================================================================");
        System.out.println();
    }

    // find possible paths using depth first search
    public static void findPossiblePaths(String currentVertex, String targetVertex,
            List<String> currentPath, List<List<String>> paths) {
        if (currentVertex.equals(targetVertex)) {
            // Found a path from A to G
            paths.add(new ArrayList<>(currentPath));
            return;
        }

        List<String> neighbors = map.getNeighbours(currentVertex);
        for (String neighbor : neighbors) {
            if (!currentPath.contains(neighbor)) {
                currentPath.add(neighbor);
                findPossiblePaths(neighbor, targetVertex, currentPath, paths);
                currentPath.remove(currentPath.size() - 1);
            }
        }
    }

    public static int calculateDistance(List<String> path) {
        int distance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String currentVertex = path.get(i);
            String nextVertex = path.get(i + 1);
            int edgeWeight = map.getEdgeWeight(currentVertex, nextVertex);

            distance += edgeWeight;
        }
        return distance;
    }

    private static void sortDistance(List<List<String>> paths, List<Integer> indices) {
        int n = paths.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                int distance1 = calculateDistance(paths.get(j));
                int distance2 = calculateDistance(paths.get(j + 1));
                if (distance1 > distance2) {
                    List<String> temp = paths.get(j);
                    paths.set(j, paths.get(j + 1));
                    paths.set(j + 1, temp);

                    Integer temp1 = indices.get(i);
                    indices.set(j, indices.get(j + 1));
                    indices.set(j + 1, temp1);
                }
            }
        }
    }
}
