package JOJOLands.JOJO;

import java.util.Map;
import java.util.HashMap;
import java.io.*;

public class Menu {
    protected Map<String, Map<String, Double>> menu;
    private String menuFilePath;
    private String directory = HermitPurple.directoryPath;

    public Menu() {
        this.menuFilePath = directory + "/menu.txt";
    }

    public void loadMenu(boolean newGame) {
        // Load the menu data from the file
        try {
            File file = new File(menuFilePath);

            if (newGame && file.exists()) {
                // Clear the file content if it's a new game
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write("");
                fileWriter.close();
            }

            FileInputStream fis = new FileInputStream(menuFilePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            menu = (Map<String, Map<String, Double>>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            initializeMenu(); // Initialize the menu with default values
            saveMenu(); // Save the initial menu to the file
        }
    }

    private void saveMenu() {
        try (FileOutputStream fos = new FileOutputStream(menuFilePath);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeMenu() {
        menu = new HashMap<>();

        Map<String, Double> JadeGardenMenu = new HashMap<>();
        JadeGardenMenu.put("Braised Chicken in Black Bean Sauce", 15.00);
        JadeGardenMenu.put("Braised Goose Web with Vermicelli", 21.00);
        JadeGardenMenu.put("Deep-fried Hiroshima Oysters", 17.00);
        JadeGardenMenu.put("Poached Tofu with Dried Shrimps", 12.00);
        JadeGardenMenu.put("Scrambled Egg White with Milk", 10.00);
        menu.put("Jade Garden", JadeGardenMenu);

        Map<String, Double> CafeDeuxMagotsMenu = new HashMap<>();
        CafeDeuxMagotsMenu.put("Sampling Matured Cheese Platter", 23.00);
        CafeDeuxMagotsMenu.put("Spring Lobster Salad", 35.00);
        CafeDeuxMagotsMenu.put("Spring Organic Omelette", 23.00);
        CafeDeuxMagotsMenu.put("Truffle-flavoured Poultry Supreme", 34.00);
        CafeDeuxMagotsMenu.put("White Asparagus", 26.00);
        menu.put("Cafe Deux Magots", CafeDeuxMagotsMenu);

        Map<String, Double> TrattoriaTrussardiMenu = new HashMap<>();
        TrattoriaTrussardiMenu.put("Caprese Salad", 10.00);
        TrattoriaTrussardiMenu.put("Creme caramel", 6.50);
        TrattoriaTrussardiMenu.put("Lamb Chops with Apple Sauce", 25.00);
        TrattoriaTrussardiMenu.put("Spaghetti alla Puttanesca", 15.00);
        menu.put("Trattoria Trussardi", TrattoriaTrussardiMenu);

        Map<String, Double> LibeccioMenu = new HashMap<>();
        LibeccioMenu.put("Formaggio", 12.50);
        LibeccioMenu.put("Ghiaccio", 1.01);
        LibeccioMenu.put("Melone", 5.20);
        LibeccioMenu.put("Prosciutto and Pesci", 20.23);
        LibeccioMenu.put("Risotto", 13.14);
        LibeccioMenu.put("Zucchero and Sale", 0.60);
        menu.put("Libeccio", LibeccioMenu);

        Map<String, Double> SavageGardenMenu = new HashMap<>();
        SavageGardenMenu.put("Abbacchio's Tea", 1.00);
        SavageGardenMenu.put("DIO's Bread", 36.14);
        SavageGardenMenu.put("Giorno's Donuts", 6.66);
        SavageGardenMenu.put("Joseph's Tequila", 35.00);
        SavageGardenMenu.put("Kakyoin's Cherry", 3.50);
        SavageGardenMenu.put("Kakyoin's Porridge", 4.44);
        menu.put("Savage Garden", SavageGardenMenu);
    }

    public void addFoodItem(String restaurant, String foodItem, double price) {
        Map<String, Double> restaurantMenu = menu.getOrDefault(restaurant, new HashMap<>());
        restaurantMenu.put(foodItem, price);
        menu.put(restaurant, restaurantMenu);
        saveMenu(); // Save the updated menu
    }

    public void deleteFoodItem(String restaurant, String foodItem) {
        Map<String, Double> restaurantMenu = menu.get(restaurant);
        if (restaurantMenu != null) {
            restaurantMenu.remove(foodItem);
            saveMenu(); // Save the updated menu
        }
    }

    public void setFoodPrice(String restaurant, String foodItem, double price) {
        Map<String, Double> restaurantMenu = menu.get(restaurant);
        if (restaurantMenu != null) {
            restaurantMenu.put(foodItem, price);
            saveMenu(); // Save the updated menu
        }
    }

    public Map<String, Double> getMenuByRestaurant(String restaurant) {
        return menu.getOrDefault(restaurant, new HashMap<>());
    }
}