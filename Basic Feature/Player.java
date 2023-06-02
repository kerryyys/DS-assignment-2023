package JOJOLands;

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
        int input = sc.nextInt();

        switch (input) {
            case 1:
                System.out.println("[1] Default Map");
                System.out.println("[2] Parallel Map");
                System.out.println("[3] Alternate Map");
                System.out.print("\nSelect: ");
                int mapchoice = sc.nextInt();

                //constructor of Hermit may be change to get better code structure
                switch (mapchoice) {
                    case 1:
                        HermitPurple hermitPurple1 = new HermitPurple();
                        hermitPurple1.getMapType(jojo.getDefaultMap());
                        break;

                    case 2:
                        HermitPurple hermitPurple2 = new HermitPurple();
                        hermitPurple2.getMapType(jojo.getParallelMap());
                        break;

                    case 3:
                        HermitPurple hermitPurple3 = new HermitPurple();
                        hermitPurple3.getMapType(jojo.getAlternateMap());
                        break;
                }

                break;

            case 2:
                // load game

                break;

            case 3:
                System.exit(0);
                break;
        }

        // run the world to choose map, then
        // if option = 1, getDefaultMap
        // then hermitPurple.getMapType(); in each case
        HermitPurple hermitPurple = new HermitPurple();
        hermitPurple.getMapName(null); // insert into each option case
    }
}
