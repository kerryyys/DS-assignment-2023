package JOJOLands.JOJO;

import java.util.*;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HermitPurple { 
    private String currentLocation;
    private int day;
    private int currentDay;
    private Stack<String> visitedLocation = new Stack<>();
    private Stack<String> temp = new Stack<>();
    private String previousLocation;
    private String MapName;
    private HashMap<String, List<String>> missionMap = new HashMap<>();
    private String selection;
    private int lastNumber;
    private String holdTopLocation; // only for storing the location used in Back selection
    private ArrayList<String> adjacentVertices;
    private Scanner sc = new Scanner(System.in);
    private HermitPurple hermitPurple;
    private TheJoestars joestars;
    private Graph<String, Integer> maps;
    public static String directoryPath;

    // add private Menu restaurant;
    /*
     * private TheWorld theworld;
     * private JOJOMaps jojomaps = new JOJOMaps();
     */
    public HermitPurple() {
        this.hermitPurple = this;
        temp = visitedLocation;
        currentLocation = "Town Hall";
        currentDay = 1;
    }

    //used in F7
     public Graph<String,Integer> getMaps(){
        return maps;
    }

    public void getMapType(Graph<String, Integer> mapsGraph) {
        this.maps = mapsGraph;
    }

    public void setMapName(String MapName) {
        // get Map type after the player choose to enter which Map
        this.MapName = MapName;
    }

    public void setFileDirectory(){
        directoryPath = "D:/JOJOLands/" + MapName + " directory";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public void startGame() {
        setFileDirectory();
        storeMission();
        start();
        displayMenu();
    }

    // used to display option
    public void displayMenu() {
        if (!temp.isEmpty()) {
            holdTopLocation = temp.pop();
            if (!temp.isEmpty())
                previousLocation = temp.peek();
            else
                previousLocation = null;
        }
        if (currentLocation.equals("Town Hall")) {
            if (previousLocation != null) {
                moveTo();
                System.out.println("[2] Advance to Next Day");
                System.out.println("[3] Save Game");
                System.out.println("[4] Back" + "(" + previousLocation + ")");
                System.out.println("[5] Exit");
                temp.push(holdTopLocation); // push back to remain temp
            } else if (previousLocation == null) {
                moveTo();
                System.out.println("[2] Advance to Next Day");
                System.out.println("[3] Save Game");
                System.out.println("[4] Exit");
            }
        } else {
            moveTo();
            displayMission();
            System.out.println("[" + lastNumber + "] Back" + "(" + previousLocation + ")");
            System.out.println("[" + (lastNumber + 1) + "] Back To Town Hall");
            temp.push(holdTopLocation);

        }
        Select();
    }

    // used show adjacent location, assume can go once only, if not, need go back
    public void moveTo() {
        adjacentVertices = maps.getNeighbours(currentLocation);
        System.out.println("Current Location: " + currentLocation);
        System.out.print("[1] Move to: \n\t");
        
        for (int i = 0; i < adjacentVertices.size(); i++) {
            char alphabet = (char) (i + 'A');
            System.out.printf("[%c] %-23s", alphabet, adjacentVertices.get(i));
        }
        System.out.println();
    }

    // travel back to the most recent location visited
    public void Back() {
        if (!temp.isEmpty()) {
            visitedLocation.pop();// repeated currentLocation twice since Select() will push in currentLocation again
            holdTopLocation = visitedLocation.pop();
            previousLocation = visitedLocation.peek();
        }
        String forwardLocation = holdTopLocation;
        System.out.println("Back to " + previousLocation);
        System.out.println("[Forward(" + forwardLocation + ")]: Do you want to move forward?: yes / no");
        String input = sc.nextLine();
        System.out.println("================================================================================");
        if (input.equalsIgnoreCase("yes")) { // choose to same location
            visitedLocation.push(holdTopLocation);
            moveForward();
        } else if (input.equalsIgnoreCase("no")) { // go to new location
            currentLocation = visitedLocation.pop(); // clears a player’s forward history when he decides to move to a new location
            displayMenu();
            Select();
        } else {
            System.out.println("Invalid input");
            boolean validInput = false;
            while (!validInput) {
                System.out.println("Do you want to move forward? Enter 'yes' or 'no':");
                input = sc.nextLine();
                if (input.equalsIgnoreCase("yes")) {
                    visitedLocation.push(holdTopLocation);
                    moveForward();
                    validInput = true; // valid input, exit the loop
                } else if (input.equalsIgnoreCase("no")) {
                    currentLocation = visitedLocation.pop();
                    displayMenu();
                    Select();
                    validInput = true; // valid input, exit the loop
                }
                // else, continue the loop to prompt for input again
            }
        }
    }

    // A player can move forward again to last visited location
    public void moveForward() {
        currentLocation = visitedLocation.peek();
        displayMenu();
        Select(); // select to continue
    }

    // travel back to the Town Hall directly
    public void BackTownHall() {
        currentLocation = "Town Hall";
        System.out.println("You back to Town Hall already.");
        visitedLocation.clear();
        temp.clear();
        previousLocation = null;
        displayMenu();
        Select();
    }

    // starts at the Town Hall at the start of each day
    public void start() {
        startNewDay();
        joestars = new TheJoestars(currentLocation, currentDay - 1); // the current day is increase after startNewDay
        joestars.Filter(); // to reset the waiting list
    }

    // recognises the first day as Day 1, which represents Sunday
    public void startNewDay() {
        String dayofWeek = getDay(currentDay);
        System.out.println("It's Day " + currentDay + "(" + dayofWeek + ") of our journey in JOJOLands!");
        this.day = currentDay;
        currentDay++;
    }

    private String getDay(int day) {
        String[] daysofWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
        int index = (day - 1) % 7; // modulo 7 to handle days beyond a week
        return daysofWeek[index];
    }

    // advance to the next day by selecting the corresponding option at the Town Hall
    public void advanceToNextDay() {
        start();
    }

    public void SaveGame(String mapIdentifier) {

        // Save the game progress
        try {
            // Create a GameState object to hold the game state
            GameState gameState = new GameState(visitedLocation, currentLocation, previousLocation, currentDay);

            // Serialize the GameState object to a binary file
            FileOutputStream fileOutputStream = new FileOutputStream(directoryPath + "/game_save.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(gameState);
            objectOutputStream.close();

            System.out.println("Game progress for map " + mapIdentifier + " saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save the game progress: " + e.getMessage());
        }
        System.out.println("Exit the game");
        System.exit(0);
    }

    // Used to terminate the program
    public void Exit() {
        System.out.println("Exiting the game...");
        // Perform any necessary cleanup or saving operations here
        System.exit(0);
    }

     public void LoadGame(String mapIdentifier) {
        // Create a directory path for the game progress
        String directoryPath = mapIdentifier;

        // Load the game progress from the binary file
        try {
            // Read the GameState object from the binary file
            FileInputStream fileInputStream = new FileInputStream(directoryPath + "/game_save.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            GameState gameState = (GameState) objectInputStream.readObject();
            objectInputStream.close();

            // Retrieve the game state data from the GameState object
            visitedLocation = gameState.getVisitedLocation();
            currentLocation = gameState.getCurrentLocation();
            previousLocation = gameState.getPreviousLocation();
            currentDay = gameState.getCurrentDay();

            System.out.println("Game progress for map " + mapIdentifier + " loaded successfully.");
        } catch (IOException e) {
            System.out.println("Failed to load the game progress: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to deserialize the game save file: " + e.getMessage());
        }
    }



    public void Select() {
        while (true) {
            System.out.print("Select: ");
            String input = sc.nextLine().toUpperCase();
            System.out.println("================================================================================");

            adjacentVertices = maps.getNeighbours(currentLocation);
            visitedLocation.push(currentLocation);
            previousLocation = currentLocation;

            if (input.isEmpty() || input.equals("1")) {
                System.out.println("Please enter a valid selection.");
                continue; // Continue to loop back and ask for input again
            }

            // if the player choose moveTo
            // move to adjacent locations connected from current location
            if (input.length() > 1 && input.length() < 3) {
                int optionIndex = input.charAt(1) - 'A';
                if (optionIndex >= 0 && optionIndex < adjacentVertices.size()) {
                    selection = adjacentVertices.get(optionIndex);
                    currentLocation = selection;

                    if (currentLocation.equals("Town Hall")) {
                        if (previousLocation != null) {
                            moveTo();
                            System.out.println("[2] Advance to Next Day");
                            System.out.println("[4] Back" + "(" + previousLocation + ")");
                            System.out.println("[5] Exit");
                        } else {
                            moveTo();
                            System.out.println("[2] Advance to Next Day");
                            System.out.println("[3] Save Game");
                            System.out.println("[4] Exit");
                        }
                    } else {
                            moveTo();
                            displayMission();
                            System.out.println("[" + lastNumber + "] Back" + "(" + previousLocation + ")");
                            System.out.println("[" + (lastNumber + 1) + "] Back To Town Hall");
                        }
                }
                visitedLocation.push(currentLocation); // can only be updated during this time
            }
            // other mission
            else {
                switch (currentLocation) {
                    case "Town Hall":
                        switch (input) {
                            case "2":
                                advanceToNextDay();
                                displayMenu();
                                break;

                            case "3":
                                SaveGame(MapName);
                                displayMenu();
                                break;

                            case "4":
                                if (previousLocation == null) {
                                    Exit();
                                } else {
                                    Back();
                                }
                                break;

                            case "5":
                                Exit();
                                break;
                        }
                        break;

                    case "Morioh Grand Hotel":
                        switch (input) {
                            case "2":
                                HeavensDoor heavensDoor = new HeavensDoor(hermitPurple, currentLocation, currentDay);
                                heavensDoor.printResidents();
                                heavensDoor.select();
                                visitedLocation.pop();
                                previousLocation = visitedLocation.peek();
                                displayMenu();
                                break;

                            case "3":
                                TheHand theHand = new TheHand();
                                theHand.display(maps);
                                visitedLocation.pop();
                                previousLocation = visitedLocation.peek();
                                displayMenu();
                                break;

                            case "4":
                                ThusSpokeRohanKishibe spoke = new ThusSpokeRohanKishibe(maps);
                                visitedLocation.pop();
                                previousLocation = visitedLocation.peek();
                                displayMenu();
                                break;

                            case "5":
                                Back();
                                break;

                            case "6":
                                BackTownHall();
                                break;
                        }
                        break;
                    case "Cafe Deux Magots":
                        switch (input) {

                            case "2":
                                PearlJam pearlJam = new PearlJam(currentLocation, day);
                                pearlJam.displayWaitingList();
                                pearlJam.displayList();
                                visitedLocation.pop(); // to pop the currentLocation that have been added since it will
                                                       // back to here to avoid previous location=currentLocation
                                displayMenu();
                                break;

                            case "3":
                                // Menu_PearlJam menuPearlJam = new Menu_PearlJam();
                                // menuPearlJam.displayMenu(currentLocation);
                                break;

                            case "4":
                                MoodyBlue moodyBlues = new MoodyBlue(hermitPurple, currentLocation, currentDay);
                                moodyBlues.readSalesDataFromFile();
                                moodyBlues.ViewSalesInformation();
                                visitedLocation.pop();
                                displayMenu();
                                break;

                            case "5":
                                // MilagroMan milargoMan = new MilagroMan(currentLocation);
                                // milargoMan.modifyFoodPrices();
                                break;

                            case "6":
                                Back();
                                break;

                            case "7":
                                BackTownHall();
                                break;
                        }
                        break;

                    case "Trattoria Trussardi":
                        switch (input) {
                            case "1":
                                moveTo();
                                break;

                            case "2":
                                PearlJam pearlJam = new PearlJam(currentLocation, day);
                                pearlJam.displayWaitingList();
                                pearlJam.displayList();
                                visitedLocation.pop();
                                displayMenu();
                                break;

                            case "3":
                                // Menu_PearlJam menuPearlJam = new Menu_PearlJam();
                                // menuPearlJam.displayMenu(currentLocation);
                                break;

                            case "4":
                                MoodyBlue moodyBlues = new MoodyBlue(hermitPurple, currentLocation, currentDay);
                                moodyBlues.readSalesDataFromFile();
                                moodyBlues.ViewSalesInformation();
                                visitedLocation.pop();
                                displayMenu();
                                break;

                            case "5":
                                // MilagroMan milargoMan = new MilagroMan(currentLocation);
                                // milargoMan.modifyFoodPrices();
                                break;

                            case "6":
                                Back();
                                break;

                            case "7":
                                BackTownHall();
                                break;
                        }
                        break;

                    case "Jade Garden":
                        switch (input) {

                            case "2":
                                PearlJam pearlJam = new PearlJam(currentLocation, day);
                                pearlJam.displayWaitingList();
                                pearlJam.displayList();
                                visitedLocation.pop();
                                displayMenu();
                                break;

                            case "3":
                                // Menu_PearlJam menuPearlJam = new Menu_PearlJam();
                                // menuPearlJam.displayMenu(currentLocation);
                                break;

                            case "4":
                                MoodyBlue moodyBlues = new MoodyBlue(hermitPurple, currentLocation, currentDay);
                                moodyBlues.readSalesDataFromFile();
                                moodyBlues.ViewSalesInformation();
                                visitedLocation.pop();
                                displayMenu();
                                break;

                            case "5":
                                // MilagroMan milargoMan = new MilagroMan(currentLocation);
                                // milargoMan.modifyFoodPrices();
                                break;

                            case "6":
                                Back();
                                break;

                            case "7":
                                BackTownHall();
                                break;
                        }
                        break;

                    case "Libeccio":
                        switch (input) {
                            case "2":
                                PearlJam pearlJam = new PearlJam(currentLocation, day);
                                pearlJam.displayWaitingList();
                                pearlJam.displayList();
                                visitedLocation.pop();
                                displayMenu();
                                break;

                            case "3":
                                // Menu_PearlJam menuPearlJam = new Menu_PearlJam();
                                // menuPearlJam.displayMenu(currentLocation);
                                break;

                            case "4":
                                MoodyBlue moodyBlues = new MoodyBlue(hermitPurple, currentLocation, currentDay);
                                moodyBlues.readSalesDataFromFile();
                                moodyBlues.ViewSalesInformation();
                                visitedLocation.pop();
                                displayMenu();
                                break;

                            case "5":
                                // MilagroMan milargoMan = new MilagroMan(currentLocation);
                                // milargoMan.modifyFoodPrices();
                                break;

                            case "6":
                                Back();
                                break;

                            case "7":
                                BackTownHall();
                                break;
                        }
                        break;

                    case "Savage Garden":
                        switch (input) {
                            case "2":
                                PearlJam pearlJam = new PearlJam(currentLocation, day);
                                pearlJam.displayWaitingList();
                                pearlJam.displayList();
                                visitedLocation.pop();
                                displayMenu();
                                break;

                            case "3":
                                // Menu_PearlJam menuPearlJam = new Menu_PearlJam();
                                // menuPearlJam.displayMenu(currentLocation);

                            case "4":
                                MoodyBlue moodyBlues = new MoodyBlue(hermitPurple, currentLocation, currentDay);
                                moodyBlues.readSalesDataFromFile();
                                moodyBlues.ViewSalesInformation();
                                visitedLocation.pop();
                                displayMenu();
                                break;

                            case "5":
                                // MilagroMan milargoMan = new MilagroMan(currentLocation);
                                // milargoMan.modifyFoodPrices();
                                break;

                            case "6":
                                Back();
                                break;

                            case "7":
                                BackTownHall();
                                break;
                        }

                    case "Angelo Rock":
                        switch (input) {
                            case "2":
                                HeavensDoor heavensDoor = new HeavensDoor(hermitPurple, currentLocation, currentDay);
                                heavensDoor.printResidents();
                                heavensDoor.select();
                                visitedLocation.pop();
                                displayMenu();
                                break;

                            case "3":
                                RedHotChiliPepper rhcp = new RedHotChiliPepper();
                                rhcp.display(maps);
                                visitedLocation.pop();
                                displayMenu();
                                break;

                            case "4":
                                // Another One Bites the Dust

                            case "5":
                                Back();
                                break;

                            case "6":
                                BackTownHall();
                                break;
                        }
                        break;

                    case "DIO's Mansion":
                        switch (input) {
                            case "2":
                                HeavensDoor heavensDoor = new HeavensDoor(hermitPurple, currentLocation, currentDay);
                                heavensDoor.printResidents();
                                heavensDoor.select();
                                visitedLocation.pop();
                                displayMenu();
                                break;

                            case "3":
                                // Chase
                                System.out.print("Enter the initial location: ");
                                String initialLocation = sc.nextLine();

                                Chase.playChase(initialLocation);
                                visitedLocation.pop();
                                displayMenu();
                                break;
                            case "4":
                                // Another One Bites the Dust

                            case "5":
                                Back();
                                break;

                            case "6":
                                BackTownHall();
                                break;

                        }
                        break;

                    case "Green Dolphin Street Prison":
                        switch (input) {
                            case "2":
                                HeavensDoor heavensDoor = new HeavensDoor(hermitPurple, currentLocation, currentDay);
                                heavensDoor.printResidents();
                                heavensDoor.select();
                                visitedLocation.pop();
                                displayMenu();
                                break;

                            case "3":
                                // Extra feature 4
                                DirtyDeedsDoneDirtCheap DDDDC = new DirtyDeedsDoneDirtCheap(maps);
                                DDDDC.RunDDDDC();
                                visitedLocation.pop();
                                displayMenu();
                                break;

                            case "4":
                                Back();
                                break;

                            case "5":
                                BackTownHall();
                                break;
                        }
                        break;

                    case "Joestar Mansion":
                    switch (input){
                        case "2":
                                HeavensDoor heavensDoor = new HeavensDoor(hermitPurple, currentLocation, currentDay);
                                heavensDoor.printResidents();
                                heavensDoor.select();
                                visitedLocation.pop();
                                displayMenu();
                                break;
                        
                        case "3":
                                //The Golden Spirit

                                displayMenu();
                                break;

                        case "4":
                                Back();
                                break;

                        case "5":
                                BackTownHall();
                                break;
                        }
                        break;

                    // for other location that dont have mission
                    default:
                        switch (input) {

                            case "2":
                                HeavensDoor heavensDoor = new HeavensDoor(hermitPurple, currentLocation, currentDay);
                                heavensDoor.printResidents();
                                heavensDoor.select();
                                visitedLocation.pop();
                                displayMenu();
                                break;

                            case "3":
                                Back();
                                break;

                            case "4":
                                BackTownHall();
                                break;
                        }
                }
                break;
            }
        }
    }

    // display mission
    public void displayMission() {
        List<String> missionList = missionMap.get(currentLocation);

        if (missionList != null) {
            for (int i = 0; i < missionList.size(); i++) {
                System.out.println("[" + (i + 2) + "] " + missionList.get(i));
            }
            lastNumber = missionList.size() + 2;
        } else {
            lastNumber = 2;
            return;
        }
    }

    public int getLastNumber() {
        return lastNumber;
    }

    // assign mission to each location
    public void addMission(String location, String mission) {
        List<String> missions = missionMap.getOrDefault(location, new ArrayList<>());
        missions.add(mission);
        missionMap.put(location, missions);
    }

    public void storeMission() {
        addMission("Morioh Grand Hotel", "View Resident Information");
        addMission("Morioh Grand Hotel", "The Hand");
        addMission("Morioh Grand Hotel", "Thus Spoke Rohan Kishibe");

        addMission("Trattoria Trussardi", "View Waiting List and Order Processing List");
        addMission("Trattoria Trussardi", "View Menu");
        addMission("Trattoria Trussardi", "View Sales Information");
        addMission("Trattoria Trussardi", "Milagro Man");

        addMission("Town Hall", "Advance to Next Day");
        addMission("Town Hall", "Save Game");
        addMission("Town Hall", "Exit");

        addMission("Jade Garden", "View Waiting List and Order Processing List");
        addMission("Jade Garden", "View Menu");
        addMission("Jade Garden", "View Sales Information");
        addMission("Jade Garden", "Milagro Man");

        addMission("Cafe Deux Magots", "View Waiting List and Order Processing List");
        addMission("Cafe Deux Magots", "View Menu");
        addMission("Cafe Deux Magots", "View Sales Information");
        addMission("Cafe Deux Magots", "Milagro Man");

        addMission("Liberrio", "View Waiting List and Order Processing List");
        addMission("Liberrio", "View Menu");
        addMission("Liberrio", "View Sales Information");
        addMission("Liberrio", "Milagro Man");

        addMission("Savage Garden", "View Waiting List and Order Processing List");
        addMission("Savage Garden", "View Menu");
        addMission("Savage Garden", "View Sales Information");
        addMission("Savage Garden", "Milagro Man");

        addMission("Joestar Mansion", "View Resident Information");
        addMission("Joestar Mansion", "The Golden Spirit");

        addMission("Polnareff Land", "View Resident Information");

        addMission("DIO's Mansion", "View Resident Information");
        addMission("DIO's Mansion", "Chase");
        addMission("DIO's Mansion", "Another One Bites the Dust");

        addMission("Angelo Rock", "View Resident Information");
        addMission("Angelo Rock", "Red Hot Chili Pepper");
        addMission("Angelo Rock", "Another One Bites the Dust");

        addMission("Green Dolphin Street Prison", "View Resident Information");
        addMission("Green Dolphin Street Prison", "Dirty Deeds Done Dirt Cheap");

        addMission("Vineyard", "View Resident Information");

        addMission("San Giorgio Maggiore", "View Resident Information");

    }

    public String getCurrentLocation() {
        return this.currentLocation;
    }

    public Stack<String> getVisitedLocation() {
        return this.visitedLocation;
    }

    public String getPreviousLocation() {
        return this.previousLocation;
    }

    public ArrayList<String> getAdjacentVertices() {
        return this.adjacentVertices;
    }

    public Stack<String> getTemp() {
        return this.temp;
    }

}
class GameState implements Serializable{ // please dont remove this because it is for the save and load -Darwish-
    private Stack<String> visitedLocation;
    private String currentLocation;
    private String previousLocation;
    private int currentDay;

    public GameState(Stack<String> visitedLocation, String currentLocation, String previousLocation, int currentDay) {
        this.visitedLocation = visitedLocation;
        this.currentLocation = currentLocation;
        this.previousLocation = previousLocation;
        this.currentDay = currentDay;
    }

    public Stack<String> getVisitedLocation() {
        return visitedLocation;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public String getPreviousLocation() {
        return previousLocation;
    }

    public int getCurrentDay() {
        return currentDay;
    }
}
