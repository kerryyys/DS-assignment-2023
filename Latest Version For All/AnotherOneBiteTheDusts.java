package JOJOLands.JOJO;
import java.util.*;
public class AnotherOneBiteTheDusts {
    public void checkBiteTheDust() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Yoshikage Kira's path: ");
        String path = sc.nextLine();
        String[] pathArray = path.split(" > ");
        List<String> longestPath = findLongestNonOverlappingPath(pathArray);

        if (longestPath.size() > 1) {
            System.out.println("================================================================================");
            System.out.print("Bites the Dust is most likely to be activated when Kira passed through ");
            System.out.println(String.join(" > ", longestPath) + ".");
            System.out.println("================================================================================");
        } else {
            System.out.println("================================================================================");
            System.out.println("Bites the Dust is not activated.");
            System.out.println("================================================================================");
        }
    }

    public static List<String> findLongestNonOverlappingPath(String[] locations) {
        Map<String, Integer> pathCounts = new HashMap<>();
        List<String> longestPath = new ArrayList<>();
        List<String> currentPath = new ArrayList<>();

        for (String location : locations) {
            if (currentPath.contains(location)) {
                if (currentPath.size() > longestPath.size()) {
                    longestPath = new ArrayList<>(currentPath);
                }
                currentPath.subList(0, currentPath.indexOf(location) + 1).clear();
            }
            currentPath.add(location);

            String pathString = String.join(" > ", currentPath);

            if (pathCounts.containsKey(pathString)) {
                pathCounts.put(pathString, pathCounts.get(pathString) + 1);
            } else {
                pathCounts.put(pathString, 1);
            }
        }

        if (currentPath.size() > longestPath.size()) {
            longestPath = new ArrayList<>(currentPath);
        }

        List<String> repeatedPaths = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : pathCounts.entrySet()) {
            if (entry.getValue() > 1 && !hasSelfOverlap(entry.getKey())) {
                repeatedPaths.add(entry.getKey());
            }
        }

        if (repeatedPaths.isEmpty()) {
            return Collections.emptyList();
        }

        String longestRepeatedPath = Collections.max(repeatedPaths, Comparator.comparing(String::length));
        return Arrays.asList(longestRepeatedPath.split(" > "));
    }

    public static boolean hasSelfOverlap(String pathString) {
        String[] locations = pathString.split(" > ");
        Set<String> visited = new HashSet<>();

        for (String location : locations) {
            if (!visited.add(location)) {
                return true;
            }
        }

        return false;
    }
}
