package JOJOLands;

import java.util.*;
import java.io.*;

public class HermitPurple extends JOJOMaps {
    private String currentLocation;
    private int day;
    private Stack<String> visitedLocation = new Stack<>();
    private Stack<String> temp = new Stack<>();
    private String previousLocation;
    private String MapName;
    private HashMap<String, List<String>> missionMap = new HashMap<>();
    private String selection;
    private int lastNumber;
    private ArrayList<String> adjacentVertices;
    private Scanner sc = new Scanner(System.in);
    private HermitPurple hermitPurple;
    private TheJoestars joestars;
    private Graph<String, Integer> maps;

    // add private Menu restaurant;
    /*
     * private TheWorld theworld;
     * private JOJOMaps jojomaps = new JOJOMaps();
     */
    public HermitPurple() {
        this.hermitPurple = this;
        temp = visitedLocation;
        currentLocation = "Town Hall";
        storeMission();
        start();
        displayMenu();
    }

    public Graph<String, Integer> getMapType(Graph<String, Integer> mapsGraph){
        return this.maps = mapsGraph;
    }
    public String getMapName(String MapName) {
        // get Map type after the player choose to enter which Map

        return this.MapName = MapName;
    }

    // used to display option
    public void displayMenu() {

        if (currentLocation.equals("Town Hall")) {
            if (previousLocation != null) {
                moveTo();
                System.out.println("[2] Advance to Next Day");
                System.out.println("[3] Save Game");
                System.out.println("[4] Back" + "(" + previousLocation + ")");
                System.out.println("[5] Exit");
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

        }
        Select();
    }

    // used show adjacent location
    public void moveTo() {
        adjacentVertices = maps.getNeighbours(currentLocation);
        System.out.println("Current Location: " + currentLocation);
        System.out.print("[1] Move to: \n\t");
        for (int i = 0; i < adjacentVertices.size(); i++) {
            char alphabet = (char) (i + 'A');
            System.out.printf("[" + alphabet + "] %-23s", adjacentVertices.get(i));
        }
        System.out.println();
    }

    // travel back to the most recent location visited
    public void Back() {
        if (visitedLocation.isEmpty()) {
            System.out.println("You haven't visited any locations yet.");
            return;
        }
        String forwardLocation = temp.pop();
        previousLocation = temp.peek();
        currentLocation = previousLocation;
        System.out.println("Back (" + previousLocation + ")");
        System.out.println("[Forward(" + forwardLocation + ")]: Do you want to move forward?: yes / no");
        String input = sc.nextLine();
        if (input.equalsIgnoreCase("yes")) {
            moveForward();
        } else if (input.equalsIgnoreCase("no")) {
            Select();
        } else {
            System.out.println("Invalid input");
            boolean validInput = false;
            while (!validInput) {
                System.out.println("Do you want to move forward? Enter 'yes' or 'no':");
                input = sc.nextLine();
                if (input.equalsIgnoreCase("yes")) {
                    moveForward();
                    validInput = true; // valid input, exit the loop
                } else if (input.equalsIgnoreCase("no")) {
                    Select();
                    validInput = true; // valid input, exit the loop
                }
                // else, continue the loop to prompt for input again
            }
        }
    }

    // A player can move forward in history if he chooses the back option.(AS ABOVE)
    public void moveForward() {
        visitedLocation.pop();

        adjacentVertices = maps.getNeighbours(currentLocation);
        // Check if the selected option matches a location name
        boolean locationFound = false;
        for (int i = 0; i < adjacentVertices.size(); i++) {
            char alphabet = (char) (i + 'A');
            if (selection.equalsIgnoreCase(alphabet + ""))
                locationFound = true;
            String selectedLocation = adjacentVertices.get(i);
            currentLocation = selectedLocation;

            if (currentLocation.equals(previousLocation)) {
                System.out.println("Moving forward to " + previousLocation);
            } else {
                clearForwardHistory();
                System.out.println("Moving to a new location: " + currentLocation);
            }
            temp.push(visitedLocation.peek()); // push back the popped element in TB
            previousLocation = visitedLocation.peek(); // since now in the last location visited
            visitedLocation.push(selectedLocation);
        }
        Select(); // select to continue
    }

    // clears a player’s forward history when he decides to move to a new location
    public void clearForwardHistory() {
        visitedLocation.pop();
        temp.pop();
    }

    // travel back to the Town Hall directly
    public void BackTownHall() {
        visitedLocation.clear();
        temp.clear();
        previousLocation = null;

        currentLocation = "Town Hall";
        System.out.println("You back to Town Hall already.");
        Select();
    }

    // starts at the Town Hall at the start of each day
    public void start() {
        startNewDay();
        if (currentDay != 1) {
            joestars = new TheJoestars(currentLocation, currentDay);
            joestars.Filter();
        }
        // `System.out.println("Current Location: "+currentLocation);
    }

    private int currentDay;

    // recognises the first day as Day 1, which represents Sunday
    public void startNewDay() {
        currentDay = 1;
        String dayofWeek = getDay(currentDay);
        System.out.println("It's Day " + currentDay + "(" + dayofWeek + ") of our journey in JOJOLands!");
        this.day = currentDay; // *****need to confirm
        currentDay++;
    }

    private String getDay(int day) {
        String[] daysofWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
        int index = (day - 1) % 7; // modulo 7 to handle days beyond a week
        return daysofWeek[index];
    }

    // advance to the next day by selecting the corresponding option at the Town
    // Hall
    public void advanceToNextDay() {
        start();
        moveTo();
    }

    // Used to terminate the program
    public void Exit() {
        System.out.println("Exiting the game...");
        // Perform any necessary cleanup or saving operations here
        System.exit(0);
    }

    public void SaveGame(String mapIdentifier) {
        // Create a directory to store the game progress according to the Map
        String directoryPath = MapName + " directory";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
        // Save the game progress
        try {
            FileOutputStream fileOut = new FileOutputStream(directoryPath + "/game_save.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(visitedLocation);
            objectOut.writeObject(currentLocation);
            objectOut.writeObject(previousLocation);
            objectOut.writeObject(currentDay);
            objectOut.close();
            fileOut.close();

            // Move the waiting list of each location file to the game directory
            List<File> waitingListForEachLocation = new ArrayList<>();

            // actually not every visited location has waiting list
            for (int i = 0; i < visitedLocation.size(); i++) {
                // Generate the file name
                String fileName = "waiting_list_" + visitedLocation.get(i) + ".txt";

                // Create the File object
                File waitingListFile = new File(fileName);

                // Add the File object to the list
                waitingListForEachLocation.add(waitingListFile);

                // Move the file to the destination directory
                File destinationFile = new File(directoryPath + "/" + fileName);
                waitingListFile.renameTo(destinationFile);
            }
            // Create a list to store the File objects
            List<File> residentInformationFiles = new ArrayList<>();

            for (int i = 0; i < visitedLocation.size(); i++) {
                String fileName = "resident_information_" + visitedLocation.get(i) + ".txt";
                File residentInformationFile = new File(fileName);
                residentInformationFiles.add(residentInformationFile);
                File destinationFiles = new File(directoryPath + "/" + fileName);
                residentInformationFile.renameTo(destinationFiles);
            }
            File FullWaitingListFile = new File("FullWaitingList.txt");
            File FullWatingListDestinationFile = new File(directoryPath + "/FullWaitingList.txt");
            FullWaitingListFile.renameTo(FullWatingListDestinationFile);

            System.out.println("Game progress for map " + mapIdentifier + " saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save the game progress: " + e.getMessage());
        }
    }

        /*
public void LoadGame(String filePath) {
    try {
        FileInputStream fileIn = new FileInputStream(filePath+"game_save.ser");
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);

        // Read the serialized object from the file
        objectIn.readObject();

        objectIn.close();
        fileIn.close();
        System.out.println("Game progress loaded successfully.");
    } catch (IOException e) {
        System.out.println("Failed to load the game progress: " + e.getMessage());
    } catch (ClassNotFoundException e) {
        System.out.println("Failed to load the game progress: " + e.getMessage());
    }
}
*/

    public void Select() {
        while (true) {
            System.out.print("Select: ");
            String input = sc.nextLine().toUpperCase();
            System.out.println("================================================================================");

            adjacentVertices = maps.getNeighbours(currentLocation);
            visitedLocation.push(currentLocation);
            previousLocation = currentLocation;

            // if the player choose moveTo
            // move to adjacent locations connected from current location
            if (input.length() > 1 && input.length() < 3) {
                int optionIndex = input.charAt(1) - 'A';
                if (optionIndex >= 0 && optionIndex < adjacentVertices.size()) {
                    selection = adjacentVertices.get(optionIndex);
                    currentLocation = selection;

                    // System.out.println("Current Location: " + currentLocation);
                    if (currentLocation.equals("Town Hall")) {
                        if (previousLocation != null) {
                            moveTo();
                            advanceToNextDay();
                            SaveGame(MapName);
                            System.out.println("[4] Back" + "(" + previousLocation + ")");
                            System.out.println("[5] Exit");
                        } else {
                            moveTo();
                            System.out.println("[2] Advance to Next Day");
                            System.out.println("[3] Save Game");
                            System.out.println("[4] Exit");
                        }
                    } else {
                        if (selection.equals(previousLocation)) {

                        } else {
                            moveTo();
                            displayMission();
                            System.out.println("[" + lastNumber + "] Back" + "(" + previousLocation + ")");
                            System.out.println("[" + (lastNumber + 1) + "] Back To Town Hall");
                        }
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
                                break;

                            case "3":
                                SaveGame(MapName);
                                break;

                            case "4":
                                if (previousLocation == null) {
                                    Exit();
                                } else
                                    Back();
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

                                break;

                            case "3":
                                TheHand theHand = new TheHand();
                                theHand.display();
                                break;

                            case "4":
                                Back();
                                break;

                            case "5":
                                BackTownHall();
                                break;

                            case "6":
                                Exit();
                                break;
                        }
                        break;
                    case "Cafe Deux Magots":
                        switch (input) {

                            case "2":
                                PearlJam pearlJam = new PearlJam(currentLocation, day);
                                visitedLocation.pop(); // to pop the currentLocation that have been added since it will
                                                       // back to here to avoid previous location=currentLocation
                                temp.pop();
                                previousLocation = visitedLocation.peek();
                                hermitPurple.displayMenu();
                                break;

                            case "3":
                                Menu_PearlJam menuPearlJam = new Menu_PearlJam();
                                menuPearlJam.displayMenu(currentLocation); 
                                break;

                            case "4":
                                MondayBlue mondayBlues = new MondayBlue(hermitPurple, input, currentDay);
                                mondayBlues.readSalesDataFromFile();
                                mondayBlues.ViewSalesInformation();
                                hermitPurple.displayMenu();
                                break;

                            case "5":
                                MilagroMan milargoMan = new MilagroMan(currentLocation);
                                milargoMan.modifyFoodPrices();
                                break;

                            case "6":
                                Back();
                                break;

                            case "7":
                                BackTownHall();
                                break;

                            case "8":
                                Exit();
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
                                visitedLocation.pop(); // to pop the currentLocation that have been added since it will
                                                       // back to here to avoid previous location=currentLocation
                                temp.pop();
                                previousLocation = visitedLocation.peek();
                                hermitPurple.displayMenu();
                                break;

                            case "3":
                            Menu_PearlJam menuPearlJam = new Menu_PearlJam();
                            menuPearlJam.displayMenu(currentLocation); 
                                break;

                            case "4":
                                MondayBlue mondayBlues = new MondayBlue(hermitPurple, input, currentDay);
                                mondayBlues.readSalesDataFromFile();
                                mondayBlues.ViewSalesInformation();
                                hermitPurple.displayMenu();
                                break;

                            case "5":
                                MilagroMan milargoMan = new MilagroMan(currentLocation);
                                milargoMan.modifyFoodPrices();
                                break;

                            case "6":
                                Back();
                                break;

                            case "7":
                                BackTownHall();
                                break;

                            case "8":
                                Exit();
                                break;
                        }
                        break;

                    case "Jade Garden":
                        switch (input) {

                            case "2":
                                PearlJam pearlJam = new PearlJam(currentLocation, day);
                                visitedLocation.pop(); // to pop the currentLocation that have been added since it will
                                                       // back to here to avoid previous location=currentLocation
                                temp.pop();
                                previousLocation = visitedLocation.peek();
                                hermitPurple.displayMenu();
                                break;

                            case "3":
                            Menu_PearlJam menuPearlJam = new Menu_PearlJam();
                            menuPearlJam.displayMenu(currentLocation);
                                break;

                            case "4":
                                MondayBlue mondayBlues = new MondayBlue(hermitPurple, input, currentDay);
                                mondayBlues.readSalesDataFromFile();
                                mondayBlues.ViewSalesInformation();
                                hermitPurple.displayMenu();
                                break;

                            case "5":
                                MilagroMan milargoMan = new MilagroMan(currentLocation);
                                milargoMan.modifyFoodPrices();
                                break;

                            case "6":
                                Back();
                                break;

                            case "7":
                                BackTownHall();
                                break;

                            case "8":
                                Exit();
                                break;
                        }
                        break;

                    case "Libeccio":
                        switch (input) {
                            case "2":
                                PearlJam pearlJam = new PearlJam(currentLocation, day);
                                visitedLocation.pop(); // to pop the currentLocation that have been added since it will
                                                       // back to here to avoid previous location=currentLocation
                                temp.pop();
                                previousLocation = visitedLocation.peek();
                                hermitPurple.displayMenu();
                                break;

                            case "3":
                                Menu_PearlJam menuPearlJam = new Menu_PearlJam();
                                menuPearlJam.displayMenu(currentLocation);
                                break;

                            case "4":
                                MondayBlue mondayBlues = new MondayBlue(hermitPurple, input, currentDay);
                                mondayBlues.readSalesDataFromFile();
                                mondayBlues.ViewSalesInformation();
                                hermitPurple.displayMenu();
                                break;

                            case "5":
                                MilagroMan milargoMan = new MilagroMan(currentLocation);
                                milargoMan.modifyFoodPrices();
                                break;

                            case "6":
                                Back();
                                break;

                            case "7":
                                BackTownHall();
                                break;

                            case "8":
                                Exit();
                                break;
                        }
                        break;

                    case "Savage Garden":
                        switch (input) {
                            case "2":
                                PearlJam pearlJam = new PearlJam(currentLocation, day);
                                visitedLocation.pop(); // to pop the currentLocation that have been added since it will
                                                       // back to here to avoid previous location=currentLocation
                                temp.pop();
                                previousLocation = visitedLocation.peek();
                                hermitPurple.displayMenu();
                                break;

                            case "3":
                                Menu_PearlJam menuPearlJam = new Menu_PearlJam();
                                menuPearlJam.displayMenu(currentLocation);

                            case "4":
                                MondayBlue mondayBlues = new MondayBlue(hermitPurple, input, currentDay);
                                mondayBlues.readSalesDataFromFile();
                                mondayBlues.ViewSalesInformation();
                                hermitPurple.displayMenu();
                                break;

                            case "5":
                                MilagroMan milargoMan = new MilagroMan(currentLocation);
                                milargoMan.modifyFoodPrices();
                                break;

                            case "6":
                                Back();
                                break;

                            case "7":
                                BackTownHall();
                                break;

                            case "8":
                                Exit();
                                break;
                        }

                    case "Angelo Rock":
                        switch (input) {
                            case "2":
                                HeavensDoor heavensDoor = new HeavensDoor(hermitPurple, currentLocation, currentDay);
                                heavensDoor.printResidents();
                                heavensDoor.select();
                                break;

                            case "3":
                                RedHotChiliPepper rhcp = new RedHotChiliPepper();
                                rhcp.display();
                                break;
                            case "4":
                                Back();
                                break;

                            case "5":
                                BackTownHall();
                                break;

                            case "6":
                                Exit();
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
                                break;

                            case "3":
                                Back();
                                break;

                            case "4":
                                BackTownHall();
                                break;

                            case "5":
                                Exit();
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

        addMission("Polnareff Land", "View Resident Information");

        addMission("DIO's Mansion", "View Resident Information");

        addMission("Angelo Rock", "View Resident Information");
        addMission("Angelo Rock", "Red Hot Chili Pepper");

        addMission("Green Dolphin Street Prison", "View Resident Information");

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
