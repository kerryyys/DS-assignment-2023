package JOJOLands;

import java.util.*;

public class TheJoestars {
    private Menu MENU;
    private String Restaurant;
    private String newFood;
    private String price;
    private String currentLocation;
    private String nextJotaroKujoVisitedResturant;
    private int totalFoodInMenu;
    private int currentDay;
    private String filename;
    private List<String[]> WaitingList; // get from WaitingListGenerator
    private List<String[]> JonathanJoestarOrder;
    private List<String[]> JosephJoestarOrder;
    private List<String[]> JotaroKujoOrder;
    private List<String[]> JosukeHigashikataOrder;
    private List<String[]> GiornoGiovannaOrder;
    private List<String[]> JolyneCujohOrder;

    private WaitingListGenerator WaitingListGenerator;
    private TheJoestarsChecker JoestarsChecker;
    private Scanner sc = new Scanner(System.in);
    private Random rand = new Random();
    // direct access food in each restaurant
    Map<String, Double> JadeGardenMenu = MENU.getMenuByRestaurant("Jade Garden");
    Map<String, Double> CafeDeuxMagotsMenu = MENU.getMenuByRestaurant("Cafe Deux Magots");
    Map<String, Double> TrattoriaTrussardiMenu = MENU.getMenuByRestaurant("Trattoria Trussardi");
    Map<String, Double> LibeccioMenu = MENU.getMenuByRestaurant("Libeccio");
    Map<String, Double> SavageGardenMenu = MENU.getMenuByRestaurant("Savage Garden");

    public TheJoestars(String currentLocation, int currentDay) {
        this.currentLocation = currentLocation;
        this.currentDay = currentDay;
        filename = "resident_information_" + currentLocation + ".txt";
        Menu MENU = new Menu();
        this.totalFoodInMenu = JadeGardenMenu.size() + CafeDeuxMagotsMenu.size() + TrattoriaTrussardiMenu.size() + LibeccioMenu.size() + SavageGardenMenu.size();
        this.Restaurant = "";
        this.newFood = "";
        this.price = null;
        this.nextJotaroKujoVisitedResturant = null;
        WaitingListGenerator = new WaitingListGenerator(currentLocation, currentDay);
        JoestarsChecker = new TheJoestarsChecker(currentLocation);
        this.WaitingList = WaitingListGenerator.getResidentFullList();
        JoestarsChecker.readResidentInformation(filename);
        JoestarsChecker.displayResidentInformation();
    }

    public void Selection() {
        while (true) {
            System.out.println("[1] View Resident's Profile");
            System.out.println("[2] Sort");
            System.out.println("[3] Exit");

            System.out.println("Select");
            int input = sc.nextInt();

            switch (input) {
                case 1:
                    System.out.println("Enter resident's name: ");
                    String name = sc.nextLine();
                    String residentName = name.toLowerCase();
                    JoestarsChecker.readResidentProfiles(residentName);
                    JoestarsChecker.displayResidentProfiles(residentName);
                    break;

                // assume: sort the namelist of the resident following their name
                case 2:
                    System.out.println("Sort the list according: ");
                    System.out.println("[1]: Name");
                    System.out.println("[2]: Age");
                    System.out.println("Sort: ");
                    int sort = sc.nextInt();
                    switch (sort) {
                        case 1:
                            JoestarsChecker.sortResidentsByName();
                            break;
                        case 2:
                            JoestarsChecker.sortResidentsByAge();
                            break;
                    }
                    continue;

                case 3:
                    // Exit the TheJoestars, back to Hermit Purple display menu
                    return;

                default:
                    System.out.println("Invalid input");
            }
        }
    }

    //use when day !=1 since day 1 only need randomly generated
    public List<String[]> Filter() {
        // if day1, dont need apply filter**********************
        // take in parameter to append new content to the waiting list order history

        // Jonathan Joestar
        // does not repeat food that he has eaten after finishing all
        JoestarsChecker.readResidentProfiles("JonathanJoestar");
        JonathanJoestarOrder = JoestarsChecker.getPersonalOrder();
        JonathanJoestarFilter(JonathanJoestarOrder);

        // Joseph Joestar
        // wont eat twice no matter week until all have eaten
        JoestarsChecker.readResidentProfiles("JosephJoestar");
        JosephJoestarOrder = JoestarsChecker.getPersonalOrder();
        JosephJoestarFilter(JosephJoestarOrder);

        // Jotaro Kujo
        // x matter week, set the restaurant, then after every dish baru move on
        JoestarsChecker.readResidentProfiles("JotaroKujo");
        JotaroKujoOrder = JoestarsChecker.getPersonalOrder();
        JotaroKujoFilter(JotaroKujoOrder);

        // Josuke Higashikata
        // weekly budget 100, if exceed, then borrow least amount to eat
        JoestarsChecker.readResidentProfiles("JosukeHigashikata");
        JosukeHigashikataOrder = JoestarsChecker.getPersonalOrder();
        JosukeHigashikataFilter(JosukeHigashikataOrder);

        // Giorno Giovanna
        // visit Trattoria Trussardi twice a week, order different dish from last
        // visit(==dont repeat), except when only 1 option
        JoestarsChecker.readResidentProfiles("GiornoGiovanna");
        GiornoGiovannaOrder = JoestarsChecker.getPersonalOrder();
        GiornoGiovannaFilter(GiornoGiovannaOrder);

        // Jolyne Cujoh
        // avoid dine in same restaurant repeatedly. Saturday will eat with Jotaro Kujo
        // in same restaurant
        JoestarsChecker.readResidentProfiles("JolyneCujoh");
        JolyneCujohOrder = JoestarsChecker.getPersonalOrder();
        JolyneCujohFilter(JolyneCujohOrder);

        return WaitingList;
    }

    public void JonathanJoestarFilter(List<String[]> JonathanJoestarOrder) {

        List<String[]> originalWaitingList = new ArrayList<>(WaitingList);

        for (int i = 0; i < WaitingList.size(); i++) {
            String[] orderList = WaitingList.get(i);
            if (orderList[1].equals("Jonathan Joestar")) {
                String[] OrderHistory = orderList[15].split(","); // get the orderHistory part that have been generated
                                                                  // for that day
                boolean foodFound = false;
                int count = 1; // start already have 1 food at day 1

                // get the order history on that day
                String day = OrderHistory[0];
                String food = OrderHistory[1];
                String restaurant = OrderHistory[2];
                String price = OrderHistory[3];

                for (String[] JonathanJoestarOrderItem : JonathanJoestarOrder) { // loop each line
                    String FoodOrder = JonathanJoestarOrderItem[1]; // get the previous food order in that line

                    if (food.equalsIgnoreCase(FoodOrder)) { // if found out the order is the same as the previous
                                                            // order, then will return true
                        foodFound = true;
                        break;
                    }
                }
                if (foodFound) {
                    do {
                        setNewRestaurant();
                        restaurant = getNewRestaurant();
                        Map<String, Double> menu = MENU.getMenuByRestaurant(restaurant); // this.Restaurant is
                                                                                                 // updated here
                        newFood = getRandomOrder(menu);
                        price = Double.toString(menu.get(newFood));
                        foodFound = false; // Reset the foodFound flag

                        // Check if the newFood already exists in the JonathanJoestarOrder
                        for (String[] JonathanJoestarOrderItem : JonathanJoestarOrder) {
                            String foodOrder = JonathanJoestarOrderItem[1];

                            if (newFood.equalsIgnoreCase(foodOrder)) {
                                foodFound = true;
                                count++;
                                break;
                            }
                        }
                    } while (foodFound || count <= totalFoodInMenu); // total food number for all 5 restaurant
                } else {
                    // don't need make change, direct break so WaitingList will remain unchange
                    WaitingList = originalWaitingList;
                    return;
                }
                orderList[15] = day + "," + newFood + "," + restaurant + "," + price;
                orderList[11] = restaurant;
                orderList[12] = newFood;
                orderList[13] = price;

                // Update the orderList in the WaitingList
                WaitingList.set(i, orderList);
                break;
            }
        }
    }

    // same as JonathanJoestarFilter
    public void JosephJoestarFilter(List<String[]> JosephJoestarOrder) {

        List<String[]> originalWaitingList = new ArrayList<>(WaitingList);

        for (int i = 0; i < WaitingList.size(); i++) {
            String[] orderList = WaitingList.get(i);
            if (orderList[1].equals("Joseph Joestar")) {
                String[] OrderHistory = orderList[15].split(",");
                boolean foodFound = false;
                int count = 1;

                String day = OrderHistory[0];
                String food = OrderHistory[1];
                String restaurant = OrderHistory[2];
                String price = OrderHistory[3];

                for (String[] JosephJoestarOrderItem : JosephJoestarOrder) {
                    String FoodOrder = JosephJoestarOrderItem[1];
                    if (food.equalsIgnoreCase(FoodOrder)) {
                        foodFound = true;
                    }
                }
                if (foodFound) {
                    do {
                        setNewRestaurant();
                        restaurant = getNewRestaurant();
                        Map<String, Double> menu = MENU.getMenuByRestaurant(restaurant);
                        newFood = getRandomOrder(menu);
                        price = Double.toString(menu.get(newFood));
                        foodFound = false;

                        for (String[] JosephJoestarOrderItem : JosephJoestarOrder) {
                            String foodOrder = JosephJoestarOrderItem[1];

                            if (newFood.equalsIgnoreCase(foodOrder)) {
                                foodFound = true;
                                count++;
                            }
                        }
                    } while (foodFound || count <= 26);
                } else {
                    WaitingList = originalWaitingList;
                    return;
                }

                orderList[15] = day + "," + newFood + "," + restaurant + "," + price;
                orderList[11] = restaurant;
                orderList[12] = newFood;
                orderList[13] = price;

                WaitingList.set(i, orderList);
                break;
            }
        }
    }

    // read from last back to previous visitedRestaurant
    public void JotaroKujoFilter(List<String[]> JotaroKujoOrder) {
        List<String[]> originalWaitingList = new ArrayList<>(WaitingList);

        //get size of each menu
        int lengthOfJG = JadeGardenMenu.size();
        int lengthOfCafe = CafeDeuxMagotsMenu.size();
        int lengthOfTT = TrattoriaTrussardiMenu.size();
        int lengthOfLibeccio = LibeccioMenu.size();
        int lengthOfSG = SavageGardenMenu.size();
        String previousRestaurant = null; 
        String previousFood = null;
        String latestRestaurant = JotaroKujoOrder.get(JotaroKujoOrder.size())[2];  //get the last visited Restaurant
        int lengthOfFood = 0;
        int totalOrder = JotaroKujoOrder.size();
        List<String> JGFoodinOrderHistory = new ArrayList<String>();
        List<String> CafeFoodinOrderHistory = new ArrayList<String>();
        List<String> TTFoodinOrderHistory = new ArrayList<String>();
        List<String> LibeccioFoodinOrderHistory = new ArrayList<String>();
        List<String> SGFoodinOrderHistory = new ArrayList<String>();

        for (int i = 0; i < WaitingList.size(); i++) {
            String[] orderList = WaitingList.get(i);
            if (orderList[1].equals("Jotaro Kujo")) {
                String[] OrderHistory = orderList[15].split(",");
                String day = OrderHistory[0];
                String food = OrderHistory[1];
                String restaurant = OrderHistory[2];
                String price = OrderHistory[3];

                for (int j = JotaroKujoOrder.size(); j >=0 ; j--) { //loop from back to check
                    String[] JotaroKujoOrderItem = JotaroKujoOrder.get(j);
                    previousFood = JotaroKujoOrderItem[1];
                    previousRestaurant = JotaroKujoOrderItem[2];

                    if(previousRestaurant.equalsIgnoreCase("Jade Garden")){
                        JGFoodinOrderHistory.add(previousFood);
                    }
                    else if(previousRestaurant.equalsIgnoreCase("Cafe Deux Magots")){
                        CafeFoodinOrderHistory.add(previousFood);
                    }
                    else if(previousRestaurant.equalsIgnoreCase("Trattoria Trussardi")){
                        TTFoodinOrderHistory.add(previousFood);
                    }
                    else if(previousRestaurant.equalsIgnoreCase("Libeccio")){
                        LibeccioFoodinOrderHistory.add(previousFood);
                    }
                    else if(previousRestaurant.equalsIgnoreCase("Savage Garden")){
                        SGFoodinOrderHistory.add(previousFood);
                    }
                }

                    // if latest same as previous restaurant, check food, same, assign, not same, pass original
                    if(restaurant.equals(latestRestaurant)){
                        if(restaurant.equals("Jade Garden")){
                            if (JGFoodinOrderHistory.contains(food)) {
                                //assign new food
                                do{
                                food = getRandomOrder(JadeGardenMenu);
                                price = Double.toString(JadeGardenMenu.get(food));
                                lengthOfFood++;
                                }while(!JGFoodinOrderHistory.contains(food));
                                if(lengthOfFood == lengthOfJG){ //means food fully claim, can move to next
                                    setNewRestaurant();
                                    if(getNewRestaurant() != "Jade Garden"){
                                    Map<String, Double> menu = MENU.getMenuByRestaurant(getNewRestaurant());
                                    food = getRandomOrder(menu);
                                    price = Double.toString(menu.get(food));
                                    }
                                }
                            }else{
                                WaitingList = originalWaitingList;
                                return;
                            }
                        }else if(restaurant.equalsIgnoreCase("Cafe Deux Magots")){
                            if (food.equals(previousFood)) {
                                //assign new food
                                do{
                                food = getRandomOrder(CafeDeuxMagotsMenu);
                                price = Double.toString(CafeDeuxMagotsMenu.get(food));
                                lengthOfFood++;
                                }while(!CafeFoodinOrderHistory.contains(food));
                                if(lengthOfFood == lengthOfCafe){ 
                                    setNewRestaurant();
                                    if(getNewRestaurant() != "Cafe Deux Magots"){
                                    Map<String, Double> menu = MENU.getMenuByRestaurant(getNewRestaurant());
                                    food = getRandomOrder(menu);
                                    price = Double.toString(menu.get(food));
                                    }
                                }
                            }else{
                                WaitingList = originalWaitingList;
                                return;
                            }
                        }else if(restaurant.equalsIgnoreCase("Trattoria Trussardi")){
                            if (food.equals(previousFood)) {
                                do{
                                food = getRandomOrder(TrattoriaTrussardiMenu);
                                price = Double.toString(TrattoriaTrussardiMenu.get(food));
                                lengthOfFood++;
                                }while(!TTFoodinOrderHistory.contains(food));
                            }if(lengthOfFood > lengthOfTT){ 
                                setNewRestaurant();
                                if(getNewRestaurant() != "Trattoria Trussardi"){
                                Map<String, Double> menu = MENU.getMenuByRestaurant(getNewRestaurant());
                                food = getRandomOrder(menu);
                                price = Double.toString(menu.get(food));
                            }
                        }else{
                            WaitingList = originalWaitingList;
                            return;
                        }
                        }else if(restaurant.equalsIgnoreCase("Libeccio")){
                            if (food.equals(previousFood)) {
                                do{
                                food = getRandomOrder(LibeccioMenu);
                                price = Double.toString(LibeccioMenu.get(food));
                                lengthOfFood++;
                                }while(!LibeccioFoodinOrderHistory.contains(food));
                            }if(lengthOfFood == lengthOfLibeccio){ 
                                setNewRestaurant();
                                if(getNewRestaurant() != "Libeccio"){
                                Map<String, Double> menu = MENU.getMenuByRestaurant(getNewRestaurant());
                                food = getRandomOrder(menu);
                                price = Double.toString(menu.get(food));
                                }
                        }else{
                            WaitingList = originalWaitingList;
                            return;
                        }
                        }else if(restaurant.equalsIgnoreCase("Savage Garden")){
                            if (food.equals(previousFood)) {
                                do{
                                food = getRandomOrder(SavageGardenMenu);
                                price = Double.toString(SavageGardenMenu.get(food));
                                lengthOfFood++;
                                }while(!SGFoodinOrderHistory.contains(food));
                            }if(lengthOfFood == lengthOfSG){ 
                                setNewRestaurant();
                                if(getNewRestaurant() != "Savage Garden"){
                                Map<String, Double> menu = MENU.getMenuByRestaurant(getNewRestaurant());
                                food = getRandomOrder(menu);
                                price = Double.toString(menu.get(food));
                                break;
                            }
                        }else{
                            WaitingList = originalWaitingList;
                            return;
                        }
                        }else if(totalOrder % totalFoodInMenu ==0){ //all food finished looping, just continue next
                            WaitingList = originalWaitingList;
                            return;
                        }
                        orderList[11] = previousRestaurant;
                        orderList[12] = food;
                        orderList[13] = price;
                        orderList[15] = day + "," + food + "," + previousRestaurant + "," + price;
                        WaitingList.set(i, orderList);
                    }
                    else if(!restaurant.equals(latestRestaurant)){
                        boolean JG = latestRestaurant.equals("Jade Garden");
                        boolean Cafe = latestRestaurant.equals("Cafe Deux Magots");
                        boolean TT = latestRestaurant.equals("Trattoria Trussardi");
                        boolean Libeccio = latestRestaurant.equals("Libeccio");
                        boolean SG = latestRestaurant.equals("Savage Garden");
                            if(JG){
                                if(JGFoodinOrderHistory.size() == JadeGardenMenu.size()){
                                    WaitingList = originalWaitingList;
                                    return;
                                } else{ //have not finished loop the restaurant menu
                                    if(!JGFoodinOrderHistory.contains(food)){
                                        food = getRandomOrder(JadeGardenMenu);
                                        price = Double.toString(JadeGardenMenu.get(food));
                                        break;
                                        }
                                }
                            }
                            else if(Cafe){
                                if(CafeFoodinOrderHistory.size() == CafeDeuxMagotsMenu.size()){
                                    WaitingList = originalWaitingList;
                                    return;
                                } else{ //have not finished loop the restaurant menu
                                    if(!CafeFoodinOrderHistory.contains(food)){
                                        food = getRandomOrder(CafeDeuxMagotsMenu);
                                        price = Double.toString(CafeDeuxMagotsMenu.get(food));
                                        break;
                                        }
                                }
                            }
                            else if(TT){
                                if(TTFoodinOrderHistory.size() == TrattoriaTrussardiMenu.size()){
                                    WaitingList = originalWaitingList;
                                    return;
                                } else{ //have not finished loop the restaurant menu
                                    if(!TTFoodinOrderHistory.contains(food)){
                                        food = getRandomOrder(TrattoriaTrussardiMenu);
                                        price = Double.toString(TrattoriaTrussardiMenu.get(food));
                                        break;
                                        }
                                }
                            }
                            else if(Libeccio){
                                if(LibeccioFoodinOrderHistory.size() == LibeccioMenu.size()){
                                    WaitingList = originalWaitingList;
                                    return;
                                } else{ //have not finished loop the restaurant menu
                                    if(!LibeccioFoodinOrderHistory.contains(food)){
                                        food = getRandomOrder(LibeccioMenu);
                                        price = Double.toString(LibeccioMenu.get(food));
                                        break;
                                        }
                                }
                            }
                            else if(SG){
                                if(SGFoodinOrderHistory.size() == SavageGardenMenu.size()){
                                    WaitingList = originalWaitingList;
                                    return;
                                } else{ //have not finished loop the restaurant menu
                                    if(!SGFoodinOrderHistory.contains(food)){
                                        food = getRandomOrder(SavageGardenMenu);
                                        price = Double.toString(SavageGardenMenu.get(food));
                                        break;
                                        }
                                }
                            }
                            else{
                                WaitingList = originalWaitingList;
                                return;
                            }
                            orderList[11] = latestRestaurant;
                            nextJotaroKujoVisitedResturant = latestRestaurant;
                            orderList[12] = food;
                            orderList[13] = price;
                            orderList[15] = day + "," + food + "," + previousRestaurant + "," + price;
                            WaitingList.set(i, orderList);
                        }
                    }
            }
        }

    public void JosukeHigashikataFilter(List<String[]> JosukeHigashikataOrder) {

        List<String[]> originalWaitingList = new ArrayList<>(WaitingList);

        // get the day of the week
        int today = 0;
        if (currentDay > 7) {
            today = currentDay % 7;
        } else {
            today = currentDay;
        }

        double totalSpent = 0;
        double price = 0;
        double averageSpendable;
        int remainingDay = 7;
        remainingDay -= today;

        for (int i = 0; i < WaitingList.size(); i++) {
            String[] orderList = WaitingList.get(i);
            if (orderList[1].equals("Josuke Higashikata")) {
                String[] orderHistory = orderList[15].split(",");
                price = Double.parseDouble(orderHistory[3]); // New order's price
            }
        }

        // Iterate through the JosukeHigashikataOrder
        for (String[] orderList : JosukeHigashikataOrder) {
            String[] PreviousOrderHistory = orderList[15].split(",");
            int day = Integer.parseInt(PreviousOrderHistory[0]);
            if (day == currentDay - 1) {
                // add on price of each day
                for (int i = 0; i < today; i++) {
                    int continueDay = currentDay - today;

                    // Get the order history for the specific day
                    String[] specificOrderHistory = JosukeHigashikataOrder.get(continueDay)[15].split(",");
                    double specificDayPrice = Double.parseDouble(specificOrderHistory[3]);

                    totalSpent += specificDayPrice;
                }

            }

            averageSpendable = (100 - totalSpent) / remainingDay;
            String minFood;
            double minPrice = 0;
            double newPrice = 0;

            // Check if the price addition exceeds the $100 budget
            if (totalSpent + price > 100) {
                // Check if it's possible to avoid exceeding the budget
                if (price <= averageSpendable * remainingDay) {
                    do {
                        // get the minimum priced food from the menu
                        setNewRestaurant();
                        Map<String, Double> menu = MENU.getMenuByRestaurant(getNewRestaurant());
                        minFood = getRandomOrder(menu);
                        minPrice = menu.get(newFood);

                        for (Map.Entry<String, Double> entry : menu.entrySet()) {
                            if (entry.getValue() < minPrice) {
                                newPrice = entry.getValue(); // Update the new price
                                minPrice = newPrice; // Update the minimum price
                                minFood = entry.getKey(); // Update the minimum priced food
                            }
                        }
                    } while (minPrice != newPrice);

                    // Assign the minimum priced food to the order
                    orderList[12] = minFood;
                    orderList[13] = Double.toString(minPrice);
                }
            } else {
                WaitingList = originalWaitingList;
                return;
            }
        }
    }

    public void GiornoGiovannaFilter(List<String[]> GiornoGiovannaOrder) {

        List<String[]> originalWaitingList = new ArrayList<>(WaitingList);
        String food = null;
        String restaurant = null;
        String theDay = null;
        String thePrice;
        int visitedTrattoriaTrussardiCount = 0;
        ArrayList<String> foodinTrattoriaTrussardi = new ArrayList<>();

        // get the day of the week
        int today = 0;
        if (currentDay > 7) {
            today = currentDay % 7;
        } else {
            today = currentDay;
        }

        // Iterate through the GiornoGiovannaOrder
        for (String[] orderList : GiornoGiovannaOrder) {
            String[] PreviousOrderHistory = orderList[15].split(",");
            int day = Integer.parseInt(PreviousOrderHistory[0]);
            if (day == currentDay - 1) {
                // add on price of each day
                for (int i = 0; i < today; i++) {
                    int continueDay = currentDay - today;

                    // Get the order history for the specific day
                    String[] specificOrderHistory = JosukeHigashikataOrder.get(continueDay)[15].split(",");
                    String OrderFood = specificOrderHistory[1];
                    String VisitedRestaurant = specificOrderHistory[2];
                    if (VisitedRestaurant.equals("Trattoria Trussardi")) {
                        foodinTrattoriaTrussardi.add(OrderFood);
                        visitedTrattoriaTrussardiCount++;
                    }
                }
            }
        }

        for (int i = 0; i < WaitingList.size(); i++) {
            String[] orderList = WaitingList.get(i);
            if (orderList[1].equals("Giorno Giovanna")) {
                String[] orderHistory = orderList[15].split(",");
                theDay = orderHistory[0];
                food = orderHistory[1];
                restaurant = orderHistory[2];
                price = orderHistory[3]; // didnot use
            }

            if (visitedTrattoriaTrussardiCount == 0 || visitedTrattoriaTrussardiCount == 1 && currentDay % 7 == 0) {

                Map<String, Double> menu = MENU.getMenuByRestaurant("Trattoria Trussardi");
                newFood = getRandomOrder(menu);
                thePrice = Double.toString(menu.get(newFood));
                orderList[15] = theDay + "," + newFood + "," + Restaurant + "," + thePrice;
                orderList[11] = Restaurant;
                orderList[12] = newFood;
                orderList[13] = thePrice;
                WaitingList.set(i, orderList);
                break;

            } else if (visitedTrattoriaTrussardiCount == 1 && currentDay % 7 != 0) { // not last day

                if (restaurant.equals("Trattoria Trussardi")) {
                    if (foodinTrattoriaTrussardi.contains(food)) {
                        Map<String, Double> menu = MENU.getMenuByRestaurant("Trattoria Trussardi");
                        newFood = getRandomOrder(menu);
                        thePrice = Double.toString(menu.get(newFood));
                        orderList[15] = theDay + "," + newFood + "," + Restaurant + "," + thePrice;
                        orderList[11] = Restaurant;
                        orderList[12] = newFood;
                        orderList[13] = thePrice;
                        WaitingList.set(i, orderList);
                        break;
                    }
                } else {
                    WaitingList = originalWaitingList;
                    return;
                }
            } else if (visitedTrattoriaTrussardiCount == 2) {
                if (restaurant.equals("Trattoria Trussardi")) {
                    do {
                        setNewRestaurant();
                        Map<String, Double> menu = MENU.getMenuByRestaurant(getNewRestaurant());
                        newFood = getRandomOrder(menu);
                        thePrice = Double.toString(menu.get(newFood));
                        orderList[15] = theDay + "," + newFood + "," + Restaurant + "," + thePrice;
                        orderList[11] = Restaurant;
                        orderList[12] = newFood;
                        orderList[13] = thePrice;
                        WaitingList.set(i, orderList);

                    } while (orderList[11] == "Trattoria Trussardi"); // if assign this restaurant, then repeat to get
                                                                      // different restaurant
                } else {
                    WaitingList = originalWaitingList;
                    return;
                }
            }
        }
    }

    public void JolyneCujohFilter(List<String[]> JolyneCujohOrder) {

        List<String[]> originalWaitingList = new ArrayList<>(WaitingList);

        String day = null;
        String food = null;
        String restaurant = null;
        String[] OrderHistory;

        for (int i = 0; i < WaitingList.size(); i++) {
            String[] orderList = WaitingList.get(i);
            if (orderList[1].equals("Jolyne Cujoh")) {
                OrderHistory = orderList[15].split(",");
                day = OrderHistory[0];
                food = OrderHistory[1]; // did not use
                restaurant = OrderHistory[2];
                price = OrderHistory[3]; //the one in class
            }

            // get the latest order only
            String[] orderHistory = JolyneCujohOrder.get(currentDay - 1);
            String visitedRestaurant = orderHistory[15].split(",")[2];

            if (restaurant.equals(visitedRestaurant) && currentDay % 7 != 6) {
                // Assign a new restaurant since it's the same as the previous one and not
                // saturday
                do {
                    setNewRestaurant();
                    Map<String, Double> newMenu = MENU.getMenuByRestaurant(orderList[11]);
                    orderList[11] = getNewRestaurant(); // new restaurant
                    orderList[12] = getRandomOrder(newMenu); // new food
                    orderList[13] = Double.toString(newMenu.get(orderList[12])); // new price
                    orderList[15] = day + "," + orderList[12] + "," + orderList[11] + "," + orderList[13];
                } while (orderList[11] == visitedRestaurant && orderList[11] != nextJotaroKujoVisitedResturant); // to avoid
                                                                                                       // crash in
                                                                                                       // saturday
            } else if (currentDay % 7 == 6 && !visitedRestaurant.equals(nextJotaroKujoVisitedResturant)) {
                // It's Saturday and the last visited restaurant is same as Jotaro Kujo's next
                // Saturday restaurant
                // Assign a new restaurant
                setNewRestaurant();
                Map<String, Double> newMenu = MENU.getMenuByRestaurant(nextJotaroKujoVisitedResturant);
                orderList[11] = nextJotaroKujoVisitedResturant; // new restaurant
                orderList[12] = getRandomOrder(newMenu); // new food
                orderList[13] = Double.toString(newMenu.get(orderList[12])); // new price
                orderList[15] = day + "," + orderList[12] + "," + orderList[11] + "," + orderList[13];
            } else { // means pass, saturday same as dad's, not same as last visited
                WaitingList = originalWaitingList;
                return;
            }
        }
    }

    private String getRandomOrder(Map<String, Double> menu) {
        List<String> orders = new ArrayList<>(menu.keySet());
        int randomIndex = rand.nextInt(orders.size());
        return orders.get(randomIndex);
    }

    private void setNewRestaurant() {
        String[] locations = { "Jade Garden", "Cafe Deux Magots", "Trattoria Trussardi", "Libeccio", "Savage Garden" };
        this.Restaurant = locations[rand.nextInt(locations.length)];
    }

    public String getNewRestaurant() {
        return this.Restaurant;
    }
}

/*
 * Jonathan Joestar
 * does not repeat food that he has eaten in one week //assume one week
 * 
 * Joseph Joestar
 * wont eat twice no matter week until all have eaten
 * 
 * Jotaro Kujo
 * x matter week, set the restaurant, then after every dish baru move on
 * 
 * Josuke Higashikata
 * weekly budget 100, if exceed, then borrow least amount to eat
 * 
 * Giorno Giovanna
 * visit Trattoria Trussardi twice a week, order different dish from last
 * visit(==dont repeat), except when only 1 option
 * 
 * Jolyne Cujoh
 * avoid dine in same restaurant repeatedly. Saturday will eat with Jotaro Kujo
 * in same restaurant
 * 
 * 
 * Here need to have method that can combined the residents and stands csv,
 * 1 method to show the general in fo from combined csv
 * then if choose view resident profile, need enter name(ignorecase), then run
 * all info, then have order history
 * (means here can search waiting list that include the current day for the
 * resident name then take out the day, food, restaurant, price)
 * can match resident name, then get the info of the resident
 */
