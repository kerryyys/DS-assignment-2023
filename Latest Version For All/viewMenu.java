package JOJOLands.JOJO;

import java.util.*;

public class viewMenu extends Menu {
    private String currentLocation;
    private String foodName;
    private Double price;
    private Map<String, Double> restaurantMenu;

    public viewMenu(String currentLocation) {
        this.currentLocation = currentLocation;
        this.restaurantMenu = new HashMap<>();
        this.foodName = foodName;
        this.price = price;
    }

    public String getRestaurant() {
        return currentLocation;
    }

    public void setRestaurant(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void addFoodItem(String foodName, double price) {
        Map<String, Double> restaurantMenu = getMenuByRestaurant(currentLocation);
        if (!restaurantMenu.containsKey(foodName)) {
            restaurantMenu.put(foodName, price);
            System.out.println("======================================================================");
            System.out.println("Menu item '" + foodName + "' added successfully");
            System.out.println("======================================================================");
        } else {
            System.out.println("======================================================================");
            System.out.println("Menu item '" + foodName + "' already exists.");
            System.out.println("======================================================================");
        }
        displayMenu(currentLocation);
    }
        public void removeFoodItem (String foodName){
            Map<String, Double> restaurantMenu = getMenuByRestaurant(currentLocation);
           if (restaurantMenu.containsKey(foodName)) {
               restaurantMenu.remove(foodName);
                System.out.println("======================================================================");
                System.out.println("Menu item '" + foodName + "' removed successfully.");
                System.out.println("======================================================================");
            } else {
                System.out.println("======================================================================");
                System.out.println("Menu item '" + foodName + "' not found.");
                System.out.println("======================================================================");
            }
            displayMenu(currentLocation);
        }

        public void modifyFoodItem (String foodName,double price) {
            Map<String, Double> restaurantMenu = getMenuByRestaurant(currentLocation);
            if (restaurantMenu.containsKey(foodName)) {
                restaurantMenu.put(foodName, price);
                System.out.println("======================================================================");
                System.out.println("Menu item '" + foodName + "' price modified successfully.");
                System.out.println("======================================================================");
            } else {
                System.out.println("======================================================================");
                System.out.println("Menu item '" + foodName + "' not found.");
                System.out.println("======================================================================");
            }
            displayMenu(currentLocation);
        }

        public void displayMenu (String currentLocation){
            Map<String, Double> restaurantMenu = getMenuByRestaurant(currentLocation);
            System.out.println("Menu for " + currentLocation + ":");
            System.out.println("+-----------------------------------------------+-----------+");
            System.out.println("Food                                            |   Price   |");
            System.out.println("+-----------------------------------------------+-----------+");
            for (Map.Entry<String, Double> entry : restaurantMenu.entrySet()) {
                String menuItem = entry.getKey();
                Double price = entry.getValue();
                System.out.printf("| %-45s | $%8.2f |\n", menuItem, price);
            }
            System.out.println("+-----------------------------------------------+-----------+");
            System.out.println(getRestaurant() + "'s Menu");
            System.out.println("\n[1] Add Food");
            System.out.println("[2] Modify Food Price");
            System.out.println("[3] Remove Food");
            System.out.println("[4] Exit View Menu");
            Scanner sc = new Scanner(System.in);
            System.out.println("Select : ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("=====================================================");
                    System.out.print("Enter new food name to add : ");
                    foodName = sc.nextLine();
                    System.out.print("Enter new food price : $");
                    price = sc.nextDouble();
                    addFoodItem(foodName, price);
                    break;
                case 2:
                    System.out.println("=====================================================");
                    System.out.print("Enter food name to modify : ");
                    foodName = sc.nextLine();
                    System.out.print("Enter new food price : $");
                    price = sc.nextDouble();
                    modifyFoodItem(foodName, price);
                    break;
                case 3:
                    System.out.println("=====================================================");
                    System.out.print("Enter food name to remove : ");
                    foodName = sc.nextLine();
                    removeFoodItem(foodName);
                    break;
                case 4:
                    System.out.println("======================================================================");
                    return;
                default:
            }
    }
}
