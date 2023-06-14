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
    }
}
