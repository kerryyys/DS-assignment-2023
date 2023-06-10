package JOJOLands.JOJO;

import java.util.*;

public class Player extends JOJOMaps {

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
                        hermitPurple1.getMapName("Default Map");
                        hermitPurple1.startGame();
                        break;

                    case "2":
                        HermitPurple hermitPurple2 = new HermitPurple();
                        hermitPurple2.getMapType(jojo.getParallelMap());
                        hermitPurple2.getMapName("Parallel Map");
                        hermitPurple2.startGame();
                        break;

                    case "3":
                        HermitPurple hermitPurple3 = new HermitPurple();
                        hermitPurple3.getMapType(jojo.getAlternateMap());
                        hermitPurple3.getMapName("Alternate Map");
                        hermitPurple3.startGame();
                        break;
                }
                break;

            case "2":
                // load game
                System.out.print("Enter the path of your save file: ");
                String filepath =sc.nextLine();
                System.out.println("======================================================================");
                HermitPurple hermitPurple = new HermitPurple();
                hermitPurple.LoadGame(filepath);
                break;

            case "3":
                System.exit(0);
                break;
        }
    }
}