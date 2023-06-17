package JOJOLands.JOJO;

import java.io.File;
import java.util.*;

public class Player extends JOJOMaps {

    public static Graph<String, Integer> DefaultMap = new Graph<>();

    public static Graph<String, Integer> ParallelMap = new Graph<>();

    public static Graph<String, Integer> AlternateMap = new Graph<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = "";
        JOJOMaps jojo = new JOJOMaps();
        System.out.println("Welcome to JOJOLands!");

        System.out.println("[1] Start Game");
        System.out.println("[2] Load Game");
        System.out.println("[3] Exit");
        while (true) {
            System.out.print("\nSelect: ");
            input = sc.nextLine();
            System.out.println("================================================================================");

            if (input.equals("1") || input.equals("2") || input.equals("3")) {
                break; // Valid input, exit the loop
            } else {
                System.out.println("Please choose a valid input.");
            }
        }

        switch (input) {
            case "1":
                String mapchoice;
                String MapName = "";
                while (true) {
                    System.out.println("[1] Default Map");
                    System.out.println("[2] Parallel Map");
                    System.out.println("[3] Alternate Map");
                    System.out.print("\nSelect: ");
                    mapchoice = sc.nextLine();
                    System.out.println(
                            "================================================================================");

                    if (mapchoice.equals("1") || mapchoice.equals("2") || mapchoice.equals("3")) {
                        if (mapchoice.equals("1")) {
                            MapName = "Default Map";
                        } else if (mapchoice.equals("2")) {
                            MapName = "Parallel Map";
                        } else {
                            MapName = "Alternate Map";
                        }
                        break; // Valid input, exit the loop
                    } else {
                        System.out.println("Please choose a valid input.");
                    }
                }

                // clean file in exist directory before start a new game
                String directoryPath = "D:/JOJOLands/" + MapName;
                File existDirectory = new File(directoryPath);
                File[] files = existDirectory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            file.delete();
                        }
                    }
                }
                switch (mapchoice) {
                    case "1":
                        HermitPurple hermitPurple1 = new HermitPurple();
                        hermitPurple1.getMapType(jojo.getMapByName(MapName));
                        DefaultMap = hermitPurple1.getMaps();
                        hermitPurple1.setMapName("Default Map");
                        hermitPurple1.startGame();
                        break;

                    case "2":
                        HermitPurple hermitPurple2 = new HermitPurple();
                        hermitPurple2.getMapType(jojo.getMapByName(MapName));
                        ParallelMap = hermitPurple2.getMaps();
                        hermitPurple2.setMapName("Parallel Map");
                        hermitPurple2.startGame();
                        break;

                    case "3":
                        HermitPurple hermitPurple3 = new HermitPurple();
                        hermitPurple3.getMapType(jojo.getMapByName(MapName));
                        AlternateMap = hermitPurple3.getMaps();
                        hermitPurple3.setMapName("Alternate Map");
                        hermitPurple3.startGame();
                        break;
                }
                break;

            case "2":
                String directory = null;
                String mapIdentifier = null;

                boolean validDirectory = false;
                while (!validDirectory) {
                    System.out.print("Enter the path of your save file: ");
                    directory = sc.nextLine();
                    System.out.println(
                            "================================================================================");

                    // Check if the file exists
                    File file = new File(directory);
                    if (!file.exists()) {
                        System.out.println("The specified path does not exist. Please enter a valid path.");
                    } else {
                        validDirectory = true;
                    }
                }

                // Extract the map name from the directory path
                int lastSlashIndex = directory.lastIndexOf("\\");
                if (lastSlashIndex != -1) {
                    mapIdentifier = directory.substring(lastSlashIndex + 1);
                } else {
                    System.out.println("Invalid directory path.");
                    return;
                }

                // Check if the extracted map name matches the available map choices
                if (mapIdentifier.equals("Default Map") || mapIdentifier.equals("Parallel Map")
                        || mapIdentifier.equals("Alternate Map")) {
                    HermitPurple savedHermitPurple = new HermitPurple();
                    savedHermitPurple.getMapType(jojo.getMapByName(mapIdentifier));
                    savedHermitPurple.setMapName(mapIdentifier);
                    savedHermitPurple.LoadGame(directory);
                    savedHermitPurple.startGame();
                } else {
                    System.out.println("Invalid map choice.");
                    return;
                }

                break;
            case "3":
                System.exit(0);
                break;
        }
    }
}
