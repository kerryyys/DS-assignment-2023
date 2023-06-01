package JOJOLands;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class ModifyMenu_MilargoMan {
    private String currentLocation; // restaurant's name
    private ArrayList<Food_MilargoMan> foodList; // list of menu

    public ModifyMenu_MilargoMan(String currentLocation) {
        this.currentLocation = currentLocation;
        Menu_PearlJam menu = new Menu_PearlJam();
        Map<String, Double> restaurantMenu = menu.getMenuByRestaurant(currentLocation);
        foodList = new ArrayList<>();
        for (Map.Entry<String, Double> entry : restaurantMenu.entrySet()) {
            String foodName = entry.getKey();
            Double foodPrice = entry.getValue();
            foodList.add(new Food_MilargoMan(foodName, foodPrice));
        }
    }

    public String getRestaurant() {
        return currentLocation;
    }

    public void setRestaurant(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public ArrayList<Food_MilargoMan> getFoodList() {
        return foodList;
    }

    public void setFoodList(ArrayList<Food_MilargoMan> foodList) {
        this.foodList = foodList;
    }

    public void addMenu(String name, double price) {
        Food_MilargoMan newFood = new Food_MilargoMan(name, price);
        foodList.add(newFood);
        System.out.println("Menu item '" + name + "' added successfully");
        System.out.println("======================================================================");
        printMenu();
    }

    public void removeMenu(String name) {
        int index = -1;
        for (int i = 0; i < foodList.size(); i++) {
            Food_MilargoMan food = foodList.get(i);
            if (food.getName().equals(name)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            foodList.remove(index);
            System.out.println("Menu item '" + name + "' removed successfully.");
            System.out.println("======================================================================");
        } else {
            System.out.println("Menu item '" + name + "' not found.");
            System.out.println("======================================================================");
        }
        printMenu();
        writeMenuToFile();
    }

    public void modifyMenu(String name, double newPrice) {
        boolean found = false;
        for (Food_MilargoMan food : foodList) {
            if (food.getName().equals(name)) {
                food.setPrice(newPrice);
                found = true;
                break;
            }
        }
        if (found) {
            System.out.println("Menu item '" + name + "' price modified successfully.");
            System.out.println("======================================================================");
        } else {
            System.out.println("Menu item '" + name + "' not found.");
            System.out.println("======================================================================");
        }
        writeMenuToFile();
    }

    public void printMenu() {
        System.out.println(getRestaurant() + "'s Menu");
        for (Food_MilargoMan item : foodList) {
            item.printFoodInfo();
        }
        System.out.println("\n[1] Add Food");
        System.out.println("[2] Modify Food Price");
        System.out.println("[3] Remove Food");
        System.out.println("[4] Exit View Menu");
        Scanner sc = new Scanner(System.in);
        System.out.println("Select : ");
        int choice = sc.nextInt();
        sc.nextLine();
        System.out.println("======================================================================");
        switch (choice) {
            case 1:
                System.out.print("Enter new food name to add : ");
                String newName = sc.nextLine();
                System.out.print("Enter new food price : ");
                double newPrice = sc.nextDouble();
                addMenu(newName, newPrice);
                break;
            case 2:
                System.out.print("Enter food name to modify : ");
                String name = sc.nextLine();
                System.out.print("Enter new food price : ");
                newPrice = sc.nextDouble();
                modifyMenu(name, newPrice);
                printMenu();
                break;
            case 3:
                System.out.print("Enter food name to remove : ");
                name = sc.nextLine();
                removeMenu(name);
                break;
            default:
        }
    }

    private void writeMenuToFile() {
        // Add code to write the menu to a file if needed
    }
}
