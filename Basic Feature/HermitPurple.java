package JOJOLands;

import java.util.Stack;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class HermitPurple extends JOJOMaps{
    private String currentLocation;
    private int day;
    private Stack<String> visitedLocation = new Stack<>();  
    private Stack<String> temp = new Stack<>();
    private String previousLocation;
    private HashMap<String, List<String>> missionMap = new HashMap<>();
    private String selection;
    private int lastNumber;
    private ArrayList<String> adjacentVertices;
    private Scanner sc = new Scanner(System.in);
    private HermitPurple hermitPurple;

    public HermitPurple() {
        this.hermitPurple = this;
        temp = visitedLocation;
        currentLocation = "Town Hall";
        storeMission();
        start();
        displayMenu();

    }

    //used to display option
    public void displayMenu() {
        
        if(currentLocation.equals("Town Hall")){
            if(previousLocation!=null){
                moveTo();
                System.out.println("[2] Advance to Next Day");
                System.out.println("[3] Save Game");
                System.out.println("[4] Back" + "(" + previousLocation + ")");
                System.out.println("[5] Exit");
            }
            else if(previousLocation == null){
                moveTo();
                System.out.println("[2] Advance to Next Day");
                System.out.println("[3] Save Game");
                System.out.println("[4] Exit");
            }
        }
        else{
            moveTo();
            displayMission();
            System.out.println("["+ lastNumber +"] Back" + "(" + previousLocation + ")");
            System.out.println("["+ (lastNumber+1) +"] Back To Town Hall"); 
            
        }
        Select();
    }

    //used show adjacent location
    public void moveTo(){
        adjacentVertices = maps.getNeighbours(currentLocation);
        System.out.println("Current Location: "+currentLocation);
        System.out.print("[1] Move to: \n\t");
        for(int i = 0; i< adjacentVertices.size(); i++){
            char alphabet=(char)(i+'A');
            System.out.printf("[" + alphabet + "] %-23s", adjacentVertices.get(i));
        }
        System.out.println();
    }

    //travel back to the most recent location visited
    public void BackRecent(){
        if(visitedLocation.isEmpty()){
            System.out.println("You haven't visited any locations yet.");
            return;
        }
        previousLocation = temp.pop();
        previousLocation = temp.peek(); 
        currentLocation = previousLocation;

        System.out.println("You have traveled back to " + currentLocation);
    }

    //A player can move forward in history if he chooses the back option.
    public void Back(){
        if(! visitedLocation.isEmpty()){
        previousLocation = temp.peek();
        System.out.println("Back ("+previousLocation+")");
        moveForward();
        }
    }

    //this method has problem, it does not pop visitedLocation, then the output is wrong, do not show choice but show move to new location....
    public void moveForward(){
        adjacentVertices = maps.getNeighbours(currentLocation);
        // Check if the selected option matches a location name
        boolean locationFound = false;
        for (int i = 0; i < adjacentVertices.size(); i++) {
            char alphabet = (char) (i + 'A');
            if (selection.equalsIgnoreCase(alphabet + "")) 
                locationFound = true;
                String selectedLocation = adjacentVertices.get(i);
                currentLocation = selectedLocation;

                if(currentLocation.equals(previousLocation)){
                    System.out.println("Moving forward to " + previousLocation);
                }
                else{
                    clearForwardHistory();
                    System.out.println("Moving to a new location: " + currentLocation);
                }
        temp.push(visitedLocation.peek()); //push back the popped element in TB
        previousLocation = visitedLocation.peek(); // since now in the last location visited
        visitedLocation.push(selectedLocation);
    }
}

    //clears a player’s forward history when he decides to move to a new location
    public void clearForwardHistory() {
        visitedLocation.pop();
        temp.pop();
    }

    //travel back to the Town Hall directly
    public void BackTownHall(){
        visitedLocation.clear();
        temp.clear();

        currentLocation = "Town Hall";
        System.out.println("You back to Town Hall already.");
    }

    //starts at the Town Hall at the start of each day
    public void start(){
        startNewDay();
        //`System.out.println("Current Location: "+currentLocation);
        }

        private int currentDay;
        //recognises the first day as Day 1, which represents Sunday
        public void startNewDay (){
            currentDay = 1;
            String dayofWeek = getDay(currentDay);
            System.out.println("It's Day " + currentDay + "(" + dayofWeek + ") of our journey in JOJOLands!");
            this.day = currentDay;  //*****need to confirm
            currentDay++;
        }
        private String getDay( int day){
            String[] daysofWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
            int index = (day - 1) % 7; //modulo 7 to handle days beyond a week
            return daysofWeek[index];
        }

    //advance to the next day by selecting the corresponding option at the Town Hall
    public void advanceToNextDay(){
        start();
        moveTo();
    }

    //Used to terminate the program
    public void Exit() {
        System.out.println("Exiting the game...");
        // Perform any necessary cleanup or saving operations here
        System.exit(0); 
    }

    public void SaveGame() {
        // Save the game progress
        try {
            FileOutputStream fileOut = new FileOutputStream("game_save.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(visitedLocation);  //missionMap is the progress of player where the location he visited and currentLocation
            objectOut.close();
            fileOut.close();
            System.out.println("Game progress saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save the game progress: " + e.getMessage());
        }
    }

    public void Select(){
        while(true){
        System.out.print("Select: ");
        String input = sc.nextLine().toUpperCase();
        System.out.println("================================================================================");

        adjacentVertices = maps.getNeighbours(currentLocation);
        visitedLocation.push(currentLocation);
        previousLocation = currentLocation;

        //if the player choose moveTo
        //move to adjacent locations connected from current location
        if(input.length()> 1 && input.length() < 3){
        int optionIndex = input.charAt(1) - 'A';
        if (optionIndex >= 0 && optionIndex < adjacentVertices.size()) {
                selection = adjacentVertices.get(optionIndex);
                currentLocation = selection;
                
              //  System.out.println("Current Location: " + currentLocation);
            if(currentLocation.equals("Town Hall")){
                if(previousLocation!=null){
                    moveTo();
                    advanceToNextDay();
                    SaveGame();
                    System.out.println("[4] Back" + "(" + previousLocation + ")"); 
                    System.out.println("[5] Exit");
                }
                else{
                    moveTo();
                    System.out.println("[2] Advance to Next Day");
                    System.out.println("[3] Save Game");
                    System.out.println("[4] Exit");
                }
            }
            else {
                if(selection.equals(previousLocation)){

                }
                else{
                    moveTo();
                    displayMission();
                    System.out.println("["+ lastNumber +"] Back" + "(" + previousLocation + ")" );
                    System.out.println("[" + (lastNumber+1) + "] Back To Town Hall");
                }
            }
        }
        visitedLocation.push(currentLocation); //can only be updated during this time
    }
            //other mission   
        else{
            switch(currentLocation){
                case "Town Hall":
                    switch(input){
                        case "2":
                        advanceToNextDay();
                        break;

                        case "3":
                        SaveGame();
                        break;

                        case "4":
                        if(previousLocation==null){
                        Exit();                        
                        }else
                            Back();
                            break;

                        case "5":
                        Exit();
                        break;
                    }
                break;

                case "Morioh Grand Hotel":
                    switch(input){
                        case "2":
                        HeavensDoor heavensDoor = new HeavensDoor(hermitPurple, currentLocation);
                        heavensDoor.select();
/* public void performAction(String input) {
        switch (currentLocation) {
            case "Morioh Grand Hotel":
                switch (input) {
                    case "2":
                        HeavenDoor heavenDoor = new HeavenDoor(currentLocation);
                        heavenDoor.viewResidentInformation();
                        // If the user chooses to exit HeavenDoor, control will return here
                        // Handle any necessary logic after returning from HeavenDoor
                        break;
                    // Other cases and logic for the current location
                }
                break;
            // Other locations and their respective cases
        }
    }

    // Other methods and logic for the HermitPurple class
} */

                        break;

                        case "3":
                        //TheHand();
                        break;

                        case "4":
                        Back();
                        break;

                        case "5":
                        BackTownHall();
                        break;

                        case "6":

                        break;
                    }

                break;
                case "Cafe Deux Magots":
                    switch(input){

                        case "2":
                        PearlJam pearlJam = new PearlJam(currentLocation, day);
                        visitedLocation.pop(); //to pop the currentLocation that have been added since it will back to here to avoid previous location=currentLocation
                        temp.pop();
                        previousLocation = visitedLocation.peek();
                        hermitPurple.displayMenu();
                        break;

                        case "3":

                        break;

                        case "4":
                        MondayBlue mondayBlues = new MondayBlue(hermitPurple, input, currentDay);
                        mondayBlues.readSalesDataFromFile();
                        mondayBlues.ViewSalesInformation();
                        hermitPurple.displayMenu();
                        break;

                        case "5":
                            
                        break;

                        case "6":

                        break;
                    }
                break;

                case "Trattoria Trussardi":
                    switch(input){
                        case "1":
                        moveTo();
                        break;

                        case "2":
                        PearlJam pearlJam = new PearlJam(currentLocation, day);
                        visitedLocation.pop(); //to pop the currentLocation that have been added since it will back to here to avoid previous location=currentLocation
                        temp.pop();
                        previousLocation = visitedLocation.peek();
                        hermitPurple.displayMenu();
                        break;

                        case "3":

                        break;

                        case "4":
                        MondayBlue mondayBlues = new MondayBlue(hermitPurple, input, currentDay);
                        mondayBlues.readSalesDataFromFile();
                        mondayBlues.ViewSalesInformation();
                        hermitPurple.displayMenu();
                        break;

                        case "5":
                            
                        break;

                        case "6":

                        break;
                    }
                break;

                case "San Giorgio Maggiore":
                    switch(input){

                        case "2":
                        PearlJam pearlJam = new PearlJam(currentLocation, day);
                        visitedLocation.pop(); //to pop the currentLocation that have been added since it will back to here to avoid previous location=currentLocation
                        temp.pop();
                        previousLocation = visitedLocation.peek();
                        hermitPurple.displayMenu();
                        break;

                        case "3":

                        break;

                        case "4":
                        break;

                        case "5":
                            
                        break;

                        case "6":

                        break;

                    }
                break;

                case "Jade Garden":
                    switch(input){

                        case "2":
                        PearlJam pearlJam = new PearlJam(currentLocation, day);
                        visitedLocation.pop(); //to pop the currentLocation that have been added since it will back to here to avoid previous location=currentLocation
                        temp.pop();
                        previousLocation = visitedLocation.peek();
                        hermitPurple.displayMenu();
                        break;

                        case "3":

                        break;

                        case "4":
                        MondayBlue mondayBlues = new MondayBlue(hermitPurple, input, currentDay);
                        mondayBlues.readSalesDataFromFile();
                        mondayBlues.ViewSalesInformation();
                        hermitPurple.displayMenu();
                        break;

                        case "5":
                            
                        break;

                        case "6":

                        break;
                    }
                break;

                case "Liberrio":
                    switch(input){
                        case "2":
                        PearlJam pearlJam = new PearlJam(currentLocation, day);
                        visitedLocation.pop(); //to pop the currentLocation that have been added since it will back to here to avoid previous location=currentLocation
                        temp.pop();
                        previousLocation = visitedLocation.peek();
                        hermitPurple.displayMenu();
                        break;

                        case "3":

                        break;

                        case "4":
                        MondayBlue mondayBlues = new MondayBlue(hermitPurple, input, currentDay);
                        mondayBlues.readSalesDataFromFile();
                        mondayBlues.ViewSalesInformation();
                        hermitPurple.displayMenu();
                        break;

                        case "5":
                            
                        break;

                        case "6":

                        break;
                    }
                break;

                case "Savage Garden":
                    switch(input){
                        case "2":
                        PearlJam pearlJam = new PearlJam(currentLocation, day);
                        visitedLocation.pop(); //to pop the currentLocation that have been added since it will back to here to avoid previous location=currentLocation
                        temp.pop();
                        previousLocation = visitedLocation.peek();
                        hermitPurple.displayMenu();
                        break;

                        case "3":

                        break;

                        case "4":
                        MondayBlue mondayBlues = new MondayBlue(hermitPurple, input, currentDay);
                        mondayBlues.readSalesDataFromFile();
                        mondayBlues.ViewSalesInformation();
                        hermitPurple.displayMenu();
                        break;

                        case "5":
                            
                        break;

                        case "6":

                        break;
                    }

                break;

                //for other location that dont have mission
                default:
                switch(input){

                        case "2":
                        HeavensDoor heavensDoor = new HeavensDoor(hermitPurple, currentLocation);
                        heavensDoor.select();
                        break;

                        case "3":

                        break;

                        case "4":
                        break;

                        case "5":
                            
                        break;
                        
                }
                    //default:
                    //System.out.println("Invalid option. Please try again.");
            } 
            break;

        }  
    }
}
            

    //display mission
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

    //assign mission to each location
    public void addMission(String location, String mission){
        List<String> missions = missionMap.getOrDefault(location, new ArrayList<>());
        missions.add(mission);
        missionMap.put(location, missions);
    }
    
    public void storeMission(){
        addMission("Morioh Grand Hotel","View Resident Information");
        addMission("Morioh Grand Hotel","The Hand");

        addMission("Trattoria Trussardi", "View Waiting List and Order Processing List");
        addMission("Trattoria Trussardi","View Menu");
        addMission("Trattoria Trussardi", "View Sales Information");
        addMission("Trattoria Trussardi", "Milagro Man");

        addMission("Town Hall", "Advance to Next Day");
        addMission("Town Hall", "Save Game");
        addMission("Town Hall", "Exit");

        addMission("Jade Garden", "View Waiting List and Order Processing List");
        addMission("Jade Garden","View Menu");
        addMission("Jade Garden", "View Sales Information");
        addMission("Jade Garden", "Milagro Man");

        addMission("Cafe Deux Magots", "View Waiting List and Order Processing List");
        addMission("Cafe Deux Magots","View Menu");
        addMission("Cafe Deux Magots", "View Sales Information");
        addMission("Cafe Deux Magots", "Milagro Man");

        addMission("Liberrio", "View Waiting List and Order Processing List");
        addMission("Liberrio","View Menu");
        addMission("Liberrio", "View Sales Information");
        addMission("Liberrio", "Milagro Man");

        addMission("Savage Garden", "View Waiting List and Order Processing List");
        addMission("Savage Garden","View Menu");
        addMission("Savage Garden", "View Sales Information");
        addMission("Savage Garden", "Milagro Man");

        addMission("Joestar Mansion", "View Resident Information");

        addMission("Polnareff Land", "View Resident Information");

        addMission("DIO's Mansion", "View Resident Information");

        addMission("Angelo Rock", "View Resident Information");

        addMission("Green Dolphin Street Prison", "View Resident Information");

        addMission("Vineyard", "View Resident Information");

        addMission("San Giorgio Maggiore", "View Resident Information");
        
    }

    public String getCurrentLocation(){
        return this.currentLocation;
    }
    public Stack<String> getVisitedLocation(){
        return this.visitedLocation;
    }
    public String getPreviousLocation(){
        return this.previousLocation;
    }
    public ArrayList<String> getAdjacentVertices(){
        return this.adjacentVertices;
    }
    public Stack<String> getTemp(){
        return this.temp;
    }

}
