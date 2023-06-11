package JOJOLands.JOJO;

import java.util.*;

public class TheJoestars {
    private Menu MENU;
    private String Restaurant;
    private String newFood;
    private String nextJotaroKujoVisitedResturant;
    private int totalFoodInMenu;
    private int currentDay;
    private static List<String[]> WaitingList; // get from WaitingListGenerator
    private List<String[]> JonathanJoestarOrder;
    private List<String[]> JosephJoestarOrder;
    private List<String[]> JotaroKujoOrder;
    private List<String[]> JosukeHigashikataOrder;
    private List<String[]> GiornoGiovannaOrder;
    private List<String[]> JolyneCujohOrder;
    private Map<String, Double> JadeGardenMenu;
    private Map<String, Double> CafeDeuxMagotsMenu;
    private Map<String, Double> TrattoriaTrussardiMenu;
    private Map<String, Double> LibeccioMenu;
    private Map<String, Double> SavageGardenMenu;

    private WaitingListGenerator WaitingListGenerator;
    private TheJoestarsChecker JoestarsChecker;
    private Random rand = new Random();
    // direct access food in each restaurant

    public TheJoestars(String currentLocation, int currentDay) {
        this.currentDay = currentDay;
        this.MENU = new Menu();
        JadeGardenMenu = MENU.getMenuByRestaurant("Jade Garden");
        CafeDeuxMagotsMenu = MENU.getMenuByRestaurant("Cafe Deux Magots");
        TrattoriaTrussardiMenu = MENU.getMenuByRestaurant("Trattoria Trussardi");
        LibeccioMenu = MENU.getMenuByRestaurant("Libeccio");
        SavageGardenMenu = MENU.getMenuByRestaurant("Savage Garden");
        this.totalFoodInMenu = JadeGardenMenu.size() + CafeDeuxMagotsMenu.size() + TrattoriaTrussardiMenu.size()
                + LibeccioMenu.size() + SavageGardenMenu.size();
        this.Restaurant = "";
        this.newFood = "";
        this.nextJotaroKujoVisitedResturant = null;
        WaitingListGenerator = new WaitingListGenerator(currentLocation, currentDay);
        JoestarsChecker = new TheJoestarsChecker();
        WaitingListGenerator.addCustomerToWaitingList();
        WaitingList = WaitingListGenerator.getResidentFullList();
    }

    // use when day !=1 since day 1 only need randomly generated
    public void Filter() {
        // take in parameter to append new content to the waiting list order history
        if (currentDay != 1) {
            // Jonathan Joestar
            // does not repeat food that he has eaten after finishing all
            JonathanJoestarOrder = JoestarsChecker.readOrderHistory("jonathanjoestar");
            JonathanJoestarFilter(JonathanJoestarOrder);

            // Joseph Joestar
            // wont eat twice no matter week until all have eaten
            JosephJoestarOrder = JoestarsChecker.readOrderHistory("josephjoestar");
            JosephJoestarFilter(JosephJoestarOrder);

            // Jotaro Kujo
            // x matter week, set the restaurant, then after every dish baru move on
            JotaroKujoOrder = JoestarsChecker.readOrderHistory("jotarokujo");
            JotaroKujoFilter(JotaroKujoOrder);

            // Josuke Higashikata
            // weekly budget 100, if exceed, then borrow least amount to eat
            JosukeHigashikataOrder = JoestarsChecker.readOrderHistory("josukehigashikata");
            JosukeHigashikataFilter(JosukeHigashikataOrder);

            // Giorno Giovanna
            // visit Trattoria Trussardi twice a week, order different dish from last
            // visit(==dont repeat), except when only 1 option
            GiornoGiovannaOrder = JoestarsChecker.readOrderHistory("giornogiovanna");
            GiornoGiovannaFilter(GiornoGiovannaOrder);

            // Jolyne Cujoh
            // avoid dine in same restaurant repeatedly. Saturday will eat with Jotaro Kujo
            // in same restaurant
            JolyneCujohOrder = JoestarsChecker.readOrderHistory("jolynecujoh");
            JolyneCujohFilter(JolyneCujohOrder);

            if (currentDay % 7 == 0) // on Saturday
                CheckKujoCujohOrderList(); // final check saturday
        }
        WaitingListGenerator.WaitingList();
    }

    public static List<String[]> getWaitingList() {
        return WaitingList;
    }

    public void JonathanJoestarFilter(List<String[]> JonathanJoestarOrder) {

        List<String[]> originalWaitingList = new ArrayList<>(WaitingList);
        ArrayList<String> PreviousFoodList = new ArrayList<>();

        for (int i = 0; i < WaitingList.size(); i++) {
            String[] orderList = WaitingList.get(i);
            if (orderList[1].equals("Jonathan Joestar")) {
                String[] OrderHistory = orderList[15].split(","); // get the orderHistory part that have been generated
                                                                  // for that day
                boolean foodFound = false;

                // get the order history on that day
                String day = OrderHistory[0];
                String food = OrderHistory[1];
                String restaurant = OrderHistory[2];
                String price = OrderHistory[3];

                for (String[] JonathanJoestarOrderItem : JonathanJoestarOrder) { // loop each line
                    String FoodOrder = JonathanJoestarOrderItem[1]; // get the previous food order in that line
                    PreviousFoodList.add(FoodOrder);
                }
                if (PreviousFoodList.contains(food)) { // if found out the order is the same as the previous order, then will return true
                        foodFound = true;
                        break;
                }
                if (foodFound && PreviousFoodList.size() < totalFoodInMenu) {
                    do {
                        setNewRestaurant();
                        restaurant = getNewRestaurant();
                        Map<String, Double> menu = MENU.getMenuByRestaurant(restaurant); 
                        newFood = getRandomOrder(menu);
                        price = Double.toString(menu.get(newFood));

                    } while (PreviousFoodList.contains(newFood));
                } else if(!foodFound || PreviousFoodList.size() >=totalFoodInMenu){
                    // don't need make change, direct break so WaitingList will remain unchange
                    WaitingList = originalWaitingList;
                    break;
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

    // same as JonathanJoestarFilter
    public void JosephJoestarFilter(List<String[]> JosephJoestarOrder) {

        List<String[]> originalWaitingList = new ArrayList<>(WaitingList);
        ArrayList<String> PreviousFoodList = new ArrayList<>();

        for (int i = 0; i < WaitingList.size(); i++) {
            String[] orderList = WaitingList.get(i);
            if (orderList[1].equals("Joseph Joestar")) {
                String[] OrderHistory = orderList[15].split(","); 
                boolean foodFound = false;

                // get the order history on that day
                String day = OrderHistory[0];
                String food = OrderHistory[1];
                String restaurant = OrderHistory[2];
                String price = OrderHistory[3];

                for (String[] JosephJoestarOrderItem : JosephJoestarOrder) { 
                    String FoodOrder = JosephJoestarOrderItem[1]; 
                    PreviousFoodList.add(FoodOrder);
                }
                if (PreviousFoodList.contains(food)) { 
                        foodFound = true;
                        break;
                }
                if (foodFound && PreviousFoodList.size() < totalFoodInMenu) {
                    do {
                        setNewRestaurant();
                        restaurant = getNewRestaurant();
                        Map<String, Double> menu = MENU.getMenuByRestaurant(restaurant); 
                        newFood = getRandomOrder(menu);
                        price = Double.toString(menu.get(newFood));

                    } while (PreviousFoodList.contains(newFood)); 
                } else if(!foodFound || PreviousFoodList.size() >=totalFoodInMenu){
                    WaitingList = originalWaitingList;
                    break;
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

    public void JotaroKujoFilter(List<String[]> JotaroKujoOrder) {
        List<String[]> originalWaitingList = new ArrayList<>(WaitingList);
        String latestRestaurant = JotaroKujoOrder.get(JotaroKujoOrder.size() - 1)[2];
        ArrayList<String> checkTasteFoodofRestaurant = new ArrayList<>();
        int today = 0;
        if (currentDay > 7) {
            today = currentDay % 7;
        } else {
            today = currentDay;
        }
        int sunday=currentDay - today;
        String[] OrderHistory = null;
        String day = null;
        String food = null;
        String restaurant = null;
        String price = null;

        for (int i = 0; i < WaitingList.size(); i++) {
            String[] orderList = WaitingList.get(i);
            if (orderList[1].equals("Jotaro Kujo")) {
                OrderHistory = orderList[15].split(",");
                day = OrderHistory[0];
                food = OrderHistory[1];
                restaurant = OrderHistory[2];
                price = OrderHistory[3];

                if (restaurant.equals(latestRestaurant)) { // see now's same as previous last or not
                    for (int sundayNext = sunday; sundayNext<currentDay;sundayNext++) {
                        String[] JotaroOrderHistory = JotaroKujoOrder.get(sundayNext);
                        if (JotaroOrderHistory[2].equals(restaurant)) {
                            checkTasteFoodofRestaurant.add(JotaroOrderHistory[1]);
                        }
                    }
                    // not all food has been tasted
                    if (checkTasteFoodofRestaurant.size() < MENU.getMenuByRestaurant(latestRestaurant).size()) {
                        do { // assign new food of restaurant
                            food = getRandomOrder(MENU.getMenuByRestaurant(latestRestaurant));
                        } while (checkTasteFoodofRestaurant.contains(food));

                        orderList[12] = food;
                        orderList[13] = Double.toString(MENU.getMenuByRestaurant(latestRestaurant).get(food));
                        price = orderList[13];
                        orderList[15] = day + "," + food + "," + restaurant + "," + price;
                        WaitingList.set(i, orderList);
                        break;
                    }
                    // food fully claimed
                    else if (checkTasteFoodofRestaurant.size() >= MENU.getMenuByRestaurant(latestRestaurant).size()) {
                        setNewRestaurant();
                        if (!getNewRestaurant().equals(latestRestaurant)) {
                            Map<String, Double> menu = MENU.getMenuByRestaurant(getNewRestaurant());
                            food = getRandomOrder(menu);
                            orderList[11] = getNewRestaurant();
                            restaurant = orderList[11];
                            orderList[12] = food;
                            orderList[13] = Double.toString(menu.get(food));
                            price = orderList[13];
                            orderList[15] = day + "," + food + "," + restaurant + "," + price;
                            WaitingList.set(i, orderList);
                            break;
                        }
                    }
                } else { // assign to same previous restaurant
                    MENU.getMenuByRestaurant(latestRestaurant);
                    for (int sundayNext = sunday; sundayNext<currentDay;sundayNext++) {
                        String[] JotaroOrderHistory = JotaroKujoOrder.get(sundayNext);
                        if (JotaroOrderHistory[2].equals(restaurant)) {
                            checkTasteFoodofRestaurant.add(JotaroOrderHistory[1]);
                        }
                    }
                    Map<String, Double> menu = MENU.getMenuByRestaurant(latestRestaurant);
                    if(checkTasteFoodofRestaurant.size() < MENU.getMenuByRestaurant(latestRestaurant).size()){
                    do {
                        food = getRandomOrder(menu);
                    } while (!checkTasteFoodofRestaurant.contains(food));
                
                    orderList[11] = latestRestaurant;
                    orderList[12] = food;
                    orderList[13] = Double.toString(menu.get(food));
                    price = orderList[13];
                    orderList[15] = day + "," + food + "," + latestRestaurant + "," + price;
                    WaitingList.set(i, orderList);
                    break;
                }else{
                    WaitingList = originalWaitingList;
                }
                }
                if (currentDay % 6 == 0 && checkTasteFoodofRestaurant.size() < MENU.getMenuByRestaurant(latestRestaurant).size()) {
                    nextJotaroKujoVisitedResturant = orderList[11];
                } else {
                    nextJotaroKujoVisitedResturant = null;
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
        String latestRestaurant;
        String[] latestOrderHistory;
        double totalSpent = 0;
        double price = 0;
        double averageSpendable;
        int remainingDay = 7;
        remainingDay -= today;

        for (int i = 0; i < WaitingList.size(); i++) {
            String[] orderList = WaitingList.get(i);
            if (orderList[1].equals("Josuke Higashikata")) {
                latestOrderHistory = orderList[15].split(",");
                price = Double.parseDouble(latestOrderHistory[3]); // New order's price
            }
        }
        // Iterate through the JosukeHigashikataOrder
        for (String[] orderHistory : JosukeHigashikataOrder) {
            int day = Integer.parseInt(orderHistory[0]);
            if (day == currentDay - today + 1) { // need add 1, if not wont start from the day1 of that week
                // add on price of each day
                for (int j = day; j < currentDay; j++) {
                    // Get the order history for the specific day
                    double specificDayPrice = Double.parseDouble(JosukeHigashikataOrder.get(day - 1)[3]);

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
                if (price <= averageSpendable + 5) { // assumption max +-5$
                    do {
                        // get the minimum priced food from the menu
                        setNewRestaurant();
                        latestRestaurant = getNewRestaurant();
                        Map<String, Double> menu = MENU.getMenuByRestaurant(latestRestaurant);
                        minFood = getRandomOrder(menu);
                        minPrice = menu.get(minFood);

                        for (Map.Entry<String, Double> entry : menu.entrySet()) {
                            if (entry.getValue() < minPrice) {
                                newPrice = entry.getValue(); // Update the new price
                                minPrice = newPrice; // Update the minimum price
                                minFood = entry.getKey(); // Update the minimum priced food
                            }
                        }
                    } while (minPrice != newPrice);

                    // Assign the NEW minimum priced food to the order
                    for (int i = 0; i < WaitingList.size(); i++) {
                        String[] orderList = WaitingList.get(i);
                        if (orderList[1].equals("Josuke Higashikata")) {
                            orderList[11] = latestRestaurant;
                            orderList[12] = minFood;
                            orderList[13] = Double.toString(minPrice);
                            orderList[15] = currentDay + "," + minFood + "," + latestRestaurant + "," + minPrice;
                            WaitingList.set(i, orderList);
                            break;
                        }
                    }
                }

            } else {
                WaitingList = originalWaitingList;
                break;
            }
        }
    }

    public void GiornoGiovannaFilter(List<String[]> GiornoGiovannaOrder) {

        List<String[]> originalWaitingList = new ArrayList<>(WaitingList);
        String food = null;
        String LatestRestaurant = null;
        String theDay = null;
        String thePrice = null;
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
            int startday = Integer.parseInt(orderList[0]);
            if (startday == currentDay - today + 1) { // =Sunday

                for (int i = (startday - 1); i < currentDay-1; i++) {
                    // Get the order history for the specific day
                    String specificOrderFood = GiornoGiovannaOrder.get(i)[1];
                    String VisitedRestaurant = GiornoGiovannaOrder.get(i)[2];
                    if (VisitedRestaurant.equals("Trattoria Trussardi")) {
                        foodinTrattoriaTrussardi.add(specificOrderFood);
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
                LatestRestaurant = orderHistory[2];

                if (visitedTrattoriaTrussardiCount == 0) {

                    Map<String, Double> menu = MENU.getMenuByRestaurant("Trattoria Trussardi");
                    Restaurant = "Trattoria Trussardi";
                    newFood = getRandomOrder(menu);
                    thePrice = Double.toString(menu.get(newFood));
                    orderList[15] = theDay + "," + newFood + "," + Restaurant + "," + thePrice;
                    orderList[11] = Restaurant;
                    orderList[12] = newFood;
                    orderList[13] = thePrice;
                    WaitingList.set(i, orderList);
                    break;

                } else if (visitedTrattoriaTrussardiCount == 1 && currentDay % 7 != 0) { // not last day

                    if (LatestRestaurant.equals("Trattoria Trussardi")) {
                        if (foodinTrattoriaTrussardi.contains(food)) {
                            Map<String, Double> menu = MENU.getMenuByRestaurant("Trattoria Trussardi");
                            Restaurant = "Trattoria Trussardi";
                            newFood = getRandomOrder(menu);
                            thePrice = Double.toString(menu.get(newFood));
                            orderList[15] = theDay + "," + newFood + "," + Restaurant + "," + thePrice;
                            orderList[11] = Restaurant;
                            orderList[12] = newFood;
                            orderList[13] = thePrice;
                            WaitingList.set(i, orderList);
                            break;
                        }
                    } else if (!LatestRestaurant.equals("Trattoria Trussardi")) {
                        for (int x = currentDay - today; x < GiornoGiovannaOrder.size(); x++) {
                            if (GiornoGiovannaOrder.get(x)[1].equals(food)) {
                                do {
                                    Map<String, Double> menu = MENU.getMenuByRestaurant(LatestRestaurant);
                                    newFood = getRandomOrder(menu);
                                    thePrice = Double.toString(menu.get(newFood));
                                    orderList[15] = theDay + "," + newFood + "," + thePrice + "," + LatestRestaurant;
                                    orderList[11] = Restaurant;
                                    orderList[12] = newFood;
                                    orderList[13] = thePrice;
                                    WaitingList.set(i, orderList);
                                } while (GiornoGiovannaOrder.get(x)[1].equals(newFood));
                            } else {
                                WaitingList = originalWaitingList;
                                break;
                            }
                        }
                    }
                } else if (visitedTrattoriaTrussardiCount == 2) {
                    if (LatestRestaurant.equals("Trattoria Trussardi")) {
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
                        } while (orderList[11].equals("Trattoria Trussardi"));

                    } else if (!LatestRestaurant.equals("Trattoria Trussardi")) {
                        for (int x = currentDay - today; x < GiornoGiovannaOrder.size(); x++) {
                            if (GiornoGiovannaOrder.get(x)[1].equals(food)) {
                                do {
                                    setNewRestaurant();
                                    Restaurant = getNewRestaurant();
                                    Map<String, Double> menu = MENU.getMenuByRestaurant(Restaurant);
                                    newFood = getRandomOrder(menu);
                                    thePrice = Double.toString(menu.get(newFood));
                                    orderList[15] = theDay + "," + newFood + "," + Restaurant + "," + thePrice;
                                    orderList[11] = Restaurant;
                                    orderList[12] = newFood;
                                    orderList[13] = thePrice;
                                    WaitingList.set(i, orderList);
                                } while (GiornoGiovannaOrder.get(x)[1].equals(food));
                            } else {
                                WaitingList = originalWaitingList;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
//assume for 1 week only
    public void JolyneCujohFilter(List<String[]> JolyneCujohOrder) {

        List<String[]> originalWaitingList = new ArrayList<>(WaitingList);

        String day = null;
        String Newrestaurant = null;
        String[] OrderHistory;

        for (int i = 0; i < WaitingList.size(); i++) {
            String[] orderList = WaitingList.get(i);
            if (orderList[1].equals("Jolyne Cujoh")) {
                OrderHistory = orderList[15].split(",");
                day = OrderHistory[0];
                Newrestaurant = OrderHistory[2];

                // get the latest order only
                String visitedRestaurant = JolyneCujohOrder.get(currentDay - 2)[2]; // because the index is start from 0

                if (Newrestaurant.equals(visitedRestaurant) && currentDay % 7 != 0 || nextJotaroKujoVisitedResturant == null) {
                    // Assign a new restaurant since it's the same as the previous one and not
                    // saturday, dad next visit is unknown
                    do {
                        setNewRestaurant();
                        Map<String, Double> newMenu = MENU.getMenuByRestaurant(orderList[11]);
                        orderList[11] = getNewRestaurant(); // new restaurant
                        orderList[12] = getRandomOrder(newMenu); // new food
                        orderList[13] = Double.toString(newMenu.get(orderList[12])); // new price
                        orderList[15] = day + "," + orderList[12] + "," + orderList[11] + "," + orderList[13];
                        WaitingList.set(i, orderList);
                    } while (orderList[11].equals(visitedRestaurant)
                            && !orderList[11].equals(nextJotaroKujoVisitedResturant)); // to avoid crash in saturday
                } else if (currentDay % 7 == 0 && nextJotaroKujoVisitedResturant != null
                        && !visitedRestaurant.equals(nextJotaroKujoVisitedResturant)) {
                    // It's Saturday and the last visited restaurant is same as Jotaro Kujo's next
                    // Saturday restaurant. Assign a new restaurant
                    setNewRestaurant();
                    Map<String, Double> newMenu = MENU.getMenuByRestaurant(nextJotaroKujoVisitedResturant);
                    orderList[11] = nextJotaroKujoVisitedResturant; // new restaurant
                    orderList[12] = getRandomOrder(newMenu); // new food
                    orderList[13] = Double.toString(newMenu.get(orderList[12])); // new price
                    orderList[15] = day + "," + orderList[12] + "," + orderList[11] + "," + orderList[13];
                    WaitingList.set(i, orderList);

                } else { // means pass, saturday same as dad's, not same as last visited
                    WaitingList = originalWaitingList;
                    break;
                }
            }
        }
    }

    private void CheckKujoCujohOrderList() {
        String CujohRestaurant = "";
        String KujoRestaurant = "";
        String[] CujohOrderHistory;
        String[] KujoOrderHistory;

        for (int i = 0; i < WaitingList.size(); i++) {
            String[] orderList = WaitingList.get(i);
            if (orderList[1].equals("Jolyne Cujoh")) {
                CujohOrderHistory = orderList[15].split(",");
                CujohRestaurant = CujohOrderHistory[2];
            } else if (orderList[1].equals("Jotaro Kujo")) {
                KujoOrderHistory = orderList[15].split(",");
                KujoRestaurant = KujoOrderHistory[2];
            }
        }

            for (int i = 0; i < WaitingList.size(); i++) {
                String[] orderList = WaitingList.get(i);
                        if (!CujohRestaurant.equals(KujoRestaurant)) {
            setNewRestaurant();
            String newRestaurant = getNewRestaurant();
            Map<String, Double> newMenu = MENU.getMenuByRestaurant(newRestaurant);
                        
                if (orderList[1].equals("Jolyne Cujoh")) {
                    CujohOrderHistory = orderList[15].split(",");
                    if (CujohOrderHistory[2].equals(CujohRestaurant)) {
                        orderList[11] = newRestaurant;
                        orderList[12] = getRandomOrder(newMenu);
                        orderList[13] = Double.toString(newMenu.get(orderList[12]));
                        orderList[15] = CujohOrderHistory[0] + "," + orderList[12] + "," + orderList[11] + ","
                                + orderList[13];
                        WaitingList.set(i, orderList);
                    }
                }
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
