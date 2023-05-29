package JOJOLands;

import java.util.Map;
import java.util.HashMap;

public class Menu {

    protected Map<String, Map<String, Double>> menu;

    public Menu(){
        menu = new HashMap<>(); // Initialize the menu map

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
    
    public Map<String, Double> getMenuByRestaurant(String restaurant) {
        return menu.getOrDefault(restaurant, new HashMap<>());
    }
}
