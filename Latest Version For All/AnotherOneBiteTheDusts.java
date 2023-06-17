package JOJOLands.JOJO;

import java.util.*;

public class AnotherOneBiteTheDusts {

    public void checkBiteTheClass() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Yoshikage Kira's path: ");
        String path = scanner.nextLine();

        String[] pathArray = path.split(">");

        // Identify the longest path with a duplicate
        String longestPathWithDuplicate = "";
        int maxLength = 0;
        Set<String> visitedPaths = new HashSet<>();
        boolean hasDuplicate = false;

        for (int i = 0; i < pathArray.length - 1; i++) {
            StringBuilder currentPath = new StringBuilder(pathArray[i]);
            for (int j = i + 1; j < pathArray.length; j++) {
                currentPath.append(">").append(pathArray[j]);
                if (!visitedPaths.contains(currentPath.toString())) {
                    visitedPaths.add(currentPath.toString());
                } else {
                    hasDuplicate = true;
                    if (currentPath.length() > maxLength) {
                        longestPathWithDuplicate = currentPath.toString();
                        maxLength = currentPath.length();
                    }
                }
            }
        }
        scanner.close();
        // Print the result or "try again" message
        if (hasDuplicate) {
            System.out.println("Bites the Dust is most likely to be activated when Kira passed through "
                    + longestPathWithDuplicate);
        } else {
            System.out.println("Bites the Dust is not activated.");
        }
        System.exit(0);
    }
}