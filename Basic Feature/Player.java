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

        HermitPurple hermitPurple = new HermitPurple();

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
                        hermitPurple.getMapType(jojo.getDefaultMap());
                        break;

                    case 2:
                        hermitPurple.getMapType(jojo.getParallelMap());
                        break;

                    case 3:
                        hermitPurple.getMapType(jojo.getAlternateMap());
                        break;
                }
                if (hermitPurple != null) {
                    hermitPurple.startGame();
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
    }
}
