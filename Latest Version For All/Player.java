package JOJOLands.JOJO;

import java.util.*;

public class Player extends JOJOMaps {
    
    public static Graph<String,Integer> mainMap1 = new Graph<>();
    
    public static Graph<String,Integer> mainMap2 = new Graph<>();
    
    public static Graph<String,Integer> mainMap3 = new Graph<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        JOJOMaps jojo = new JOJOMaps();
        System.out.println("Welcome to JOJOLands!");

        System.out.println("[1] Start Game");
        System.out.println("[2] Load Game");
        System.out.println("[3] Exit");
        System.out.print("\nSelect: ");
        String input = sc.nextLine();
        System.out.println("================================================================================");
        if(!input.equals("1") && !input.equals("2") && !input.equals("3") || input.isEmpty()){
            System.out.println("Please choose valid input.");
                return; 
            }

        switch (input) {
            case "1":
                System.out.println("[1] Default Map");
                System.out.println("[2] Parallel Map");
                System.out.println("[3] Alternate Map");
                System.out.print("\nSelect: ");
                String mapchoice = sc.nextLine();
                System.out.println("================================================================================");
                if(!mapchoice.equals("1") && !mapchoice.equals("2") && !mapchoice.equals("3") ||mapchoice.isEmpty()){
                    System.out.println("Please choose valid input.");
                return; 
            }

                //constructor of Hermit may be change to get better code structure
                switch (mapchoice) {
                    case "1":
                        HermitPurple hermitPurple1 = new HermitPurple();
                        hermitPurple1.getMapType(jojo.getDefaultMap());
                        mainMap1 = hermitPurple1.getMaps();
                        hermitPurple1.setMapName("Default Map");
                        hermitPurple1.startGame();
                        break;

                    case "2":
                        HermitPurple hermitPurple2 = new HermitPurple();
                        hermitPurple2.getMapType(jojo.getParallelMap());
                        mainMap2 = hermitPurple2.getMaps();
                        hermitPurple2.setMapName("Parallel Map");
                        hermitPurple2.startGame();
                        break;

                    case "3":
                        HermitPurple hermitPurple3 = new HermitPurple();
                        hermitPurple3.getMapType(jojo.getAlternateMap());
                        mainMap3 = hermitPurple3.getMaps();
                        hermitPurple3.setMapName("Alternate Map");
                        hermitPurple3.startGame();
                        break;
                }
                break;

            case "2":
                // Load game
                System.out.print("Enter the path of your save file: ");
                String filepath = sc.nextLine();
                System.out.println("================================================================================");
                String mapIdentifier = null;

                System.out.println("[1] Default Map");
                System.out.println("[2] Parallel Map");
                System.out.println("[3] Alternate Map");
                System.out.print("\nSelect the map for the saved game(Must be same as the directory map): ");
                String savedMapChoice = sc.nextLine();
                System.out.println("================================================================================");

                switch (savedMapChoice) {
                        
                    case "1":
                        mapIdentifier = "Default Map";
                        HermitPurple savedHermitPurple1 = new HermitPurple();
                        savedHermitPurple1.getMapType(jojo.getDefaultMap());
                        savedHermitPurple1.setMapName(mapIdentifier);
                        savedHermitPurple1.LoadGame(filepath);
                        savedHermitPurple1.startGame();
                        break;

                    case "2":
                        mapIdentifier = "Parallel Map";
                        HermitPurple savedHermitPurple2 = new HermitPurple();
                        savedHermitPurple2.getMapType(jojo.getParallelMap());
                        savedHermitPurple2.setMapName(mapIdentifier);
                        savedHermitPurple2.LoadGame(filepath);
                        savedHermitPurple2.startGame();
                        break;

                    case "3":
                        mapIdentifier = "Alternate Map";
                        HermitPurple savedHermitPurple3 = new HermitPurple();
                        savedHermitPurple3.getMapType(jojo.getAlternateMap());
                        savedHermitPurple3.setMapName(mapIdentifier);
                        savedHermitPurple3.LoadGame(filepath);
                        savedHermitPurple3.startGame();
                        break;

                    default:
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
