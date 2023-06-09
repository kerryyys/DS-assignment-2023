package JOJOLands.JOJO;

import java.util.*;

public class Chase {
    private static final int INITIAL_SPEED = 1;
    private static final int TIME_STOP_COOLDOWN = 2;
    private static final String THE_WORLD_STAND = "The World";
    private static Graph<String, Integer> map = new Graph<>();

    public static void playChase(String initialLocation) {
        List<String> locations = new ArrayList<>();
        List<Integer> distances = new ArrayList<>();

        // Add the initial location
        locations.add(initialLocation);
        distances.add(0);

        int turn = 0;
        boolean gameOver = false;

        while (!gameOver) {
            System.out.println("======================================================================");
            System.out.println("Turn " + turn);
            System.out.println("DIO and Pucci are at " + locations.get(turn));

            // Check if Pucci reaches the same location as DIO
            if (distances.get(turn) == 0) {
                System.out.println("Pucci reaches the same location as DIO!");
                gameOver = true;
                break;
            }

            // Calculate DIO's next move
            String dioNextLocation = getNextLocation(locations.get(turn), locations.get(turn + 1), turn);
            int dioNextDistance = distances.get(turn)
                    + calculateDistanceToPucci(dioNextLocation, locations.get(turn + 1), turn);

            // Calculate Pucci's next move
            String pucciNextLocation = getNextLocation(locations.get(turn + 1), dioNextLocation, turn + 1);
            int pucciNextDistance = distances.get(turn)
                    + calculateDistanceToPucci(pucciNextLocation, dioNextLocation, turn + 1);

            // Add DIO's and Pucci's positions after their movements
            locations.add(dioNextLocation);
            distances.add(dioNextDistance);
            locations.add(pucciNextLocation);
            distances.add(pucciNextDistance);

            // Print DIO's move and distance
            System.out.println("DIO moves to " + dioNextLocation + " (" + dioNextDistance + " km from Pucci)");

            // Print Pucci's move and distance
            String pucciPath = locations.get(turn + 1) + " -> " + pucciNextLocation;
            double pucciProgress = (double) pucciNextDistance / distances.get(turn) * 100;
            System.out.println("Pucci moves to " + pucciPath + " (" + pucciNextDistance + "/" + distances.get(turn)
                    + ") (" + pucciProgress + "%) (km from DIO)\n");

            // Increment the turn
            turn++;
        }
        System.out.println("======================================================================");
    }

    private static String getNextLocation(String currentLocation, String pucciLocation, int currentTurn) {
        // Get the list of adjacent locations for the current location
        List<String> adjacentLocations = map.getNeighbours(currentLocation);

        // Calculate the distances to Pucci for each adjacent location
        Map<String, Integer> distancesToPucci = new HashMap<>();
        for (String location : adjacentLocations) {
            int distance = calculateDistanceToPucci(location, pucciLocation, currentTurn);
            distancesToPucci.put(location, distance);
        }

        // Sort the adjacent locations based on the distances to Pucci
        List<String> sortedLocations = new ArrayList<>(adjacentLocations);
        sortedLocations.sort(Comparator.comparingInt(distancesToPucci::get));

        // Choose the location with the highest distance (longest distance) to Pucci
        String nextLocation = sortedLocations.get(sortedLocations.size() - 1);

        // Check if the cooldown for using the Stand has expired
        boolean isCooldownExpired = (currentTurn % (TIME_STOP_COOLDOWN + 1) == TIME_STOP_COOLDOWN);

        if (isCooldownExpired) {
            // Calculate the distance for the current turn and the next turn without using
            // the Stand
            int currentTurnDistance = distancesToPucci.get(nextLocation);
            int nextTurnDistance = distancesToPucci.get(sortedLocations.get(sortedLocations.size() - 2));

            // Calculate the distance for the next turn using the Stand
            int nextTurnWithStandDistance = currentTurnDistance + nextTurnDistance;

            // Compare the distances to determine if using the Stand is beneficial
            if (nextTurnWithStandDistance > currentTurnDistance && nextTurnWithStandDistance > nextTurnDistance) {
                nextLocation = sortedLocations.get(sortedLocations.size() - 2); // Choose the location for using the
                                                                                // Stand
            }
        }

        return nextLocation;
    }

    private static int calculateDistanceToPucci(String location, String pucciLocation, int turn) {
        int distance = Math.abs(location.length() - pucciLocation.length());

        // Calculate the additional distance for Pucci's increased speed
        int pucciSpeed = getPucciSpeed(turn);
        int additionalDistance = (pucciSpeed - 1) * (location.length() - 1);
        distance += additionalDistance;

        return distance;
    }

    private static int getPucciSpeed(int turn) {
        // Pucci's speed increases by 1 km per turn starting from the second turn
        return Math.max(1, INITIAL_SPEED + (turn - 1));
    }
}