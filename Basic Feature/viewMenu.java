package MilargoMan;

import java.util.*;
public class viewMenu {
    private String restaurant;
    private ArrayList<ArrayList<Food>> foodList;

    public viewMenu(String restaurant) {
        this.restaurant = restaurant;
        foodList = new ArrayList<>();
    }
    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public ArrayList<ArrayList<Food>> getFoodList(){
        return foodList;
    }

    public void setFoodList(ArrayList<ArrayList<Food>> foodList) {
        this.foodList = foodList;
    }

    public void addFoodItem(String name,double price) {
        ArrayList<Food> restaurantFoodList = new ArrayList<>();
        restaurantFoodList.add(new Food(name, price));
        foodList.add(restaurantFoodList);
        System.out.println("Menu item '"+name+"' added successfully");
        System.out.println("======================================================================");
        printMenu();
    }
    public void removeFoodItem(String name) {
        boolean removed = false;
        for (ArrayList<Food> restaurantFoodList : foodList) {
            int index = -1;
            for (int i = 0; i < restaurantFoodList.size(); i++) {
                Food food = restaurantFoodList.get(i);
                if (food.getName().equals(name)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                restaurantFoodList.remove(index);
                removed = true;
                break;
            }
        }
        if (removed) {
            System.out.println("Menu item '" + name + "' removed successfully.");
            System.out.println("======================================================================");
        } else {
            System.out.println("Menu item '" + name + "' not found.");
            System.out.println("======================================================================");
        }
        printMenu();
    }

    public void modifyFoodItem(String name, double newPrice) {
        boolean found = false;
        for (ArrayList<Food> restaurantFoodList : foodList) {
            for (Food food : restaurantFoodList) {
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
        }
    }

    public void printMenu() {
        System.out.println(getRestaurant()+"'s Menu");
        for (ArrayList<Food> restaurantFoodList : foodList) {
            for (Food food : restaurantFoodList) {
                food.printFoodInfo();
            }
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
        switch(choice){
            case 1:
                System.out.print("Enter new food name to add : ");
                String newName = sc.nextLine();
                System.out.print("Enter new food price : ");
                double newPrice = sc.nextDouble();
                addFoodItem(newName,newPrice);
                break;
            case 2:
                System.out.print("Enter food name to remove : ");
                String name = sc.nextLine();
                removeFoodItem(name);
                break;
            default:
                break;
        }
    }
}

