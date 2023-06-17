package JOJOLands.JOJO;

import java.util.*;

public class viewMenu {
    private Menu menu;
    private String currentLocation;
    private String foodName;
    private Double price;
    private Double newPrice;

    public viewMenu(String currentLocation) {
        this.menu = new Menu();
         menu.loadMenu();
        this.currentLocation = currentLocation;
    }

    public void addNewFoodFromUser(String foodName, double price) {
        Map<String, Double> restaurantMenu = menu.getMenuByRestaurant(currentLocation);
        boolean exists = false;

        for (String menuFood : restaurantMenu.keySet()) {
            if (menuFood.equalsIgnoreCase(foodName)) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            menu.addFoodItem(currentLocation, foodName, price);
            System.out.println("Menu item '" + foodName + "' added successfully");
            System.out.println("================================================================================");
        } else {
            System.out.println("Menu item '" + foodName + "' already exists.");
            System.out.println("================================================================================");
        }

        displayMenu(currentLocation);
    }

    public void removeFoodFromUser(String foodName) {
        Map<String, Double> restaurantMenu = menu.getMenuByRestaurant(currentLocation);
        boolean found = false;

        for (String menuFood : restaurantMenu.keySet()) {
            if (menuFood.equalsIgnoreCase(foodName)) {
                menu.deleteFoodItem(currentLocation, menuFood);
                System.out.println("Menu item '" + menuFood + "' removed successfully.");
                System.out.println("================================================================================");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Menu item '" + foodName + "' not found.");
            System.out.println("================================================================================");
        }

        displayMenu(currentLocation);
    }

    public void modifyFoodFromUser(String foodName, double newPrice) {
        Map<String, Double> restaurantMenu = menu.getMenuByRestaurant(currentLocation);
        boolean found = false;

        for (String menuFood : restaurantMenu.keySet()) {
            if (menuFood.equalsIgnoreCase(foodName)) {
                menu.setFoodPrice(currentLocation, menuFood, newPrice);
                System.out.println("Menu item '" + menuFood + "' price modified successfully.");
                System.out.println("================================================================================");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Menu item '" + foodName + "' not found.");
            System.out.println("================================================================================");
        }

        displayMenu(currentLocation);
    }

    public void displayMenu(String currentLocation) {
        System.out.println("Menu for " + currentLocation + ":");
        Map<String, Double> restaurantMenu = menu.getMenuByRestaurant(currentLocation);

        System.out.println("+-----------------------------------------------+-----------+");
        System.out.println("Food                                            |   Price   |");
        System.out.println("+-----------------------------------------------+-----------+");
        for (Map.Entry<String, Double> entry : restaurantMenu.entrySet()) {
            String menuItem = entry.getKey();
            Double price = entry.getValue();
            System.out.printf("| %-45s | $%8.2f |\n", menuItem, price);
        }
        System.out.println("+-----------------------------------------------+-----------+");
        System.out.println("================================================================================");
        System.out.println(currentLocation + "'s Menu");
        System.out.println("\n[1] Add Food");
        System.out.println("[2] Modify Food Price");
        System.out.println("[3] Remove Food");
        System.out.println("[4] Exit View Menu");
        Scanner sc = new Scanner(System.in);
        System.out.print("Select : ");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1:
                System.out.println("================================================================================");
                System.out.print("Enter new food name to add : ");
                foodName = sc.nextLine();
                System.out.print("Enter new food price : $");
                price = sc.nextDouble();
                addNewFoodFromUser(foodName, price);
                break;
            case 2:
                System.out.println("================================================================================");
                System.out.print("Enter food name to modify : ");
                foodName = sc.nextLine();
                System.out.print("Enter new food price : $");
                newPrice = sc.nextDouble();
                modifyFoodFromUser(foodName, newPrice);
                break;
            case 3:
                System.out.println("================================================================================");
                System.out.print("Enter food name to remove : ");
                foodName = sc.nextLine();
                removeFoodFromUser(foodName);
                break;
            case 4:
                System.out.println("================================================================================");
                return;
            default:
        }
    }
}