package JOJOLands;

//this class seems need to use for the main class
public class Player extends JOJOMaps{
    
    public static void main(String[] args){
        System.out.println("Welcome to JOJOLands!");
        HermitPurple hermitPurple = new HermitPurple();
    }



/* 
    protected String currentLocation;
    protected Stack<String> visitedLocation;  
    protected Stack<String> temp;
    protected String previousLocation;
    private int currentDay;
    protected HashMap<String, List<String>> missionMap;
    public Scanner sc = new Scanner(System.in);

    Player(){
        System.out.println("Welcome to JOJOLands!");

    }

    //move to adjacent locations connected from current location
    public void moveTo(){
        ArrayList <String> adjacentVertices = maps.getNeighbours(currentLocation);
        System.out.println("[1] Move to ");
        for(int i = 0; i< adjacentVertices.size(); i++){
            char alphabet=(char)(i+'A');
            System.out.printf("[" + alphabet + "] %-22s", adjacentVertices.get(i));
        }
        System.out.println();
    }

    //travel back to the most recent location visited
    public void travelBack(){
        if(visitedLocation.isEmpty()){
            System.out.println("You haven't visited any locations yet.");
            return;
        }
        previousLocation = temp.pop();
        previousLocation = temp.peek(); 
        currentLocation = previousLocation;

        System.out.println("You have traveled back to " + currentLocation);
    }

    //move forward in history if he chooses the back option
    public void moveForward(){
        temp.push(visitedLocation.peek()); //push back the popped element in TB
        previousLocation = visitedLocation.peek(); // since now in the last location visited
        System.out.println("Moving forward to " + previousLocation);
        currentLocation = previousLocation;
    }

    //clears a player’s forward history when he decides to move to a new location
    public Stack<String> clearForwardHistory(){
        visitedLocation.pop();
        temp.pop();
        return visitedLocation;
    }

    //travel back to the Town Hall directly
    public void toTownHall(){
        visitedLocation.clear();
        temp.clear();

        currentLocation = "Town Hall";
        System.out.println("You back to Town Hall already.");
    }

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

    }

    //displays the available missions in each location , maybe can use hashMap//case
    public void displayMission(String location){
    List<String> missions = missionMap.get(location);
    if (missions == null) {
        return;
    } else if (!missions.isEmpty()){
        for(int i = 0; i < missions.size(); i++){
            String mission = missions.get(i);
            System.out.println("[" + (i+2) + "]" + mission);
        }
    }
}

    //recognises the first day as Day 1, which represents Sunday
    public void startNewDay(){
        currentDay = 1;
        String dayofWeek = getDay(currentDay);
        System.out.println("It’s Day " + currentDay + "(" + dayofWeek + ") of our journey in JOJOLands!");
        
        currentDay++;
    }

    private String getDay( int day){
        String[] daysofWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        int index = (day - 1) % 7; //modulo 7 to handle days beyond a week
        return daysofWeek[index];
    }

    //starts at the Town Hall at the start of each day
    public void start(){
        currentLocation = "Town Hall";
        startNewDay();
        System.out.println("Current Location: "+ currentLocation);
        moveTo();
    }

    //advance to the next day by selecting the corresponding option at the Town Hall
    public void advancedToNextDay(){
        start();
        moveTo();
    }
    */
    
}
