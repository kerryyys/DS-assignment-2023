package JOJOLands.JOJO;

import java.util.*;

public class viewMenu extends Menu {
    private String restaurant;
    private ArrayList<Food> foodList;
    private String currentLocation;

    public viewMenu(String restaurant) {
        this.restaurant = restaurant;
        foodList = new ArrayList<>();
        this.currentLocation = currentLocation;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public void setFoodList(ArrayList<Food> foodList) {
        this.foodList = foodList;
    }

    public void addFoodItem(String name, double price) {
        Food newFood = new Food(name, price);
        foodList.add(newFood);
        System.out.println("======================================================================");
        System.out.println("Menu item '" + name + "' added successfully");
        System.out.println("======================================================================");
        displayMenu(currentLocation);
    }

    public void removeFoodItem(String name) {
        int index = -1;
        for (int i = 0; i < foodList.size(); i++) {
            Food food = foodList.get(i);
            if (food.getName().equals(name)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            foodList.remove(index);
            System.out.println("======================================================================");
            System.out.println("Menu item '" + name + "' removed successfully.");
            System.out.println("======================================================================");
        } else {
            System.out.println("======================================================================");
            System.out.println("Menu item '" + name + "' not found.");
            System.out.println("======================================================================");
        }
        displayMenu(currentLocation);
    }

    public void modifyFoodItem(String name, double newPrice) {
        boolean found = false;
        for (Food food : foodList) {
            if (food.getName().equals(name)) {
                food.setPrice(newPrice);
                found = true;
                break;
            }
        }
        if (found) {
            System.out.println("======================================================================");
            System.out.println("Menu item '" + name + "' price modified successfully.");
            System.out.println("======================================================================");
        } else {
            System.out.println("======================================================================");
            System.out.println("Menu item '" + name + "' not found.");
            System.out.println("======================================================================");
        }
    }

    public void printMenu() {
        System.out.println("==============================================================================");
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
                System.out.print("Enter new food name to add : ");
                String newName = sc.nextLine();
                System.out.print("Enter new food price : ");
                double newPrice = sc.nextDouble();
                addFoodItem(newName, newPrice);
                break;
            case 2:
                System.out.print("Enter food name to modify : ");
                String name = sc.nextLine();
                System.out.print("Enter new food price : ");
                newPrice = sc.nextDouble();
                modifyFoodItem(name, newPrice);
                printMenu();
                break;
            case 3:
                System.out.print("Enter food name to remove : ");
                name = sc.nextLine();
                removeFoodItem(name);
                break;
            default:
        }
    }

    public void displayMenu(String currentLocation) {
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
    }
}