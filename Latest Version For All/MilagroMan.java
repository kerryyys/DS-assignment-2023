package JOJOLands.JOJO;
import java.util.*;
import java.io.*;

class Food {
    private final double defaultPrice;
    private String name;
    private double price;

    public Food(String name, double defaultPrice) {
        this.name = name;
        this.price = defaultPrice;
        this.defaultPrice = defaultPrice;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

class Restaurant {
    String filename;
    String copyFilename;
    private int currentDay;
    private Menu menu;
    private Resident resident;
    private String currentLocation;
    private static List<String[]> MilagroManSalesInformation;
    private Map<String, List<Food>> salesByDay;
    private Map<String, List<Food>> originalSalesByDay;

    public Restaurant(String currentLocation) {
        this.currentLocation = currentLocation;
        salesByDay = new HashMap<>();
        originalSalesByDay = new HashMap<>();
        this.filename = filename;
        this.copyFilename = copyFilename;
        this.currentDay = currentDay;
        this.resident = new Resident();
    }

    public void addFood(String day, String foodName, double newPrice) {
        Food food = new Food(foodName, newPrice);
        salesByDay.computeIfAbsent(day, k -> new ArrayList<>()).add(food);
        originalSalesByDay.computeIfAbsent(day, k -> new ArrayList<>()).add(food);
        System.out.println("Menu item '" + currentLocation + "' added successfully");
        System.out.println("======================================================================");
    }

    public void modifyFoodPrice(String startDay, String endDay, String foodName, double newPrice) {
        int start = Integer.parseInt(startDay);
        int end = Integer.parseInt(endDay);

        for (int day = start; day <= end; day++) {
            String dayKey = String.valueOf(day);
            List<Food> sales = salesByDay.get(dayKey);

            if (sales != null) {
                for (Food food : sales) {
                    if (food.getName().equals(foodName)) {
                        food.setPrice(newPrice);
                        System.out.println("Menu item '" + currentLocation + "' price modified successfully.");
                        System.out.println("======================================================================");
                    } else {
                        System.out.println("Menu item '" + currentLocation + "' not found.");
                        System.out.println("======================================================================");
                    }
                }
            }
        }
    }

    public void removeFood(String foodName) {
        for (List<Food> sales : salesByDay.values()) {
            sales.removeIf(food -> food.getName().equals(foodName));
            if (sales.remove(foodName)) {
                System.out.println("Menu item '" + currentLocation + "' removed successfully.");
                System.out.println("======================================================================");
            } else {
                System.out.println("Menu item '" + currentLocation + "' not found.");
                System.out.println("======================================================================");
        }
        }
    }

    public void printSalesInformation() {
        for (Map.Entry<String, List<Food>> entry : salesByDay.entrySet()) {
            String day = entry.getKey();
            List<Food> sales = entry.getValue();
            System.out.println("Day " + day + ":");
            for (Food food : sales) {
                System.out.println(food.getName() + " - $" + food.getPrice());
            }
            System.out.println();
        }
    }

    public String getCuurentLocation() {
        return currentLocation;
    }
}

public class MilagroMan {
    private MoodyBlue moodyBlue;
    private Restaurant restaurant;
    private String currentLocation;
    private Scanner sc;
    private int currentDay;
    private HermitPurple hermit;
    private Map<String, Map<String, Map<String, SalesRecord>>> salesData;
    private Map<String, Map<String, Map<String, SalesRecord>>> modifiedSalesData;
    private Map<String, Double> foodPrices;

    public MilagroMan(String currentLocation) {
        this.currentLocation = currentLocation;
        this.sc = new Scanner(System.in);
        this.restaurant = new Restaurant(currentLocation);
        this.moodyBlue = new MoodyBlue(hermit, currentLocation, currentDay);
        this.salesData = new HashMap<>();
        this.modifiedSalesData = new HashMap<>();
        this.foodPrices = new HashMap<>();
    }

    public void enterExperimentalMode() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=======================================================================");
            System.out.println("Restaurant: " + currentLocation + " (Milagro Man mode) ");
            System.out.println("[1] Modify the Menu");
            System.out.println("[2] Modify Food Prices");
            System.out.println("[3] View Sales Information");
            System.out.println("[4] Exit Milagro Man");
            System.out.print("Select: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    modifyMenu();
                case "2":
                    modifyFoodPrice();
                    break;
                case "3":
                    viewSalesInformation();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
    private void modifyFoodPrice() {
        System.out.println("=======================================================================");
        System.out.print("Enter the food name: ");
        String foodName = sc.nextLine();
        System.out.print("Enter the new price: $");
        Double newPrice = sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter the starting day: ");
        String startDay = sc.nextLine();
        System.out.print("Enter the ending day: ");
        String endDay = sc.nextLine();

        for (int day = Integer.parseInt(startDay); day <= Integer.parseInt(endDay); day++) {
            String dayKey = String.valueOf(day);
            // Retrieve the sales data for the specified day and location
            Map<String, SalesRecord> salesByFood = salesData.getOrDefault(currentLocation, new HashMap<>())
                    .getOrDefault(dayKey, new HashMap<>());

            // Modify the food price in the original salesData map
            Map<String, Map<String, SalesRecord>> salesByLocation = salesData.getOrDefault(currentLocation, new HashMap<>());
            for (Map<String, SalesRecord> salesByDay : salesByLocation.values()) {
                if (salesByDay.containsKey(foodName)) {
                    SalesRecord record = salesByDay.get(foodName);
                    record.setPrice(newPrice);
                }
            }

            // Update the modifiedSalesData map with the modified prices
            Map<String, Map<String, SalesRecord>> modifiedSalesByLocation = modifiedSalesData.getOrDefault(currentLocation, new HashMap<>());
            for (Map<String, SalesRecord> modifiedSalesByDay : modifiedSalesByLocation.values()) {
                if (modifiedSalesByDay.containsKey(foodName)) {
                    SalesRecord modifiedRecord = modifiedSalesByDay.get(foodName);
                    modifiedRecord.setPrice(newPrice);
                }
            }
            // Store the modified sales data in the modifiedSalesData map
            modifiedSalesData.computeIfAbsent(currentLocation, k -> new HashMap<>())
                    .computeIfAbsent(dayKey, k -> new HashMap<>())
                    .put(foodName, salesByFood.get(foodName));
        }
    }
    private void viewSalesInformation() {
        String selection = "";
        while (true) {
            int startday;
            int endday;
            System.out.println("=======================================================================");
            System.out.println("Restaurant: " + currentLocation);
            System.out.println("Sales Information");
            System.out.println("[1] View Sales");
            System.out.println("[2] View Aggregated Information");
            System.out.println("\t[A] Minimum Sales");
            System.out.println("\t[B] Maximum Sales");
            System.out.println("\t[C] Top k Highest Sales");
            System.out.println("\t[D] Total and Average Sales");
            System.out.println("[3] Exit");
            System.out.print("Select: ");

            selection = sc.nextLine();
            System.out.println("======================================================================");
            switch (selection.toUpperCase()) {
                case "1":
                    System.out.print("Enter Day: ");
                    int day = sc.nextInt();
                    sc.nextLine(); // Consume the remaining newline character
                    System.out.println();
                    viewSales(day, foodPrices);
                    continue;
                case "2A":
                    System.out.print("Enter Start Day: ");
                    startday = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter End Day: ");
                    endday = sc.nextInt();
                    sc.nextLine();
                    System.out.println();
                    if (endday >= currentDay) {
                        System.out.println("End day should until the current day.");
                        continue;
                    }
                    minimumSales(startday, endday);
                    continue;
                case "2B":
                    System.out.print("Enter Start Day: ");
                    startday = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter End Day: ");
                    endday = sc.nextInt();
                    sc.nextLine();
                    System.out.println();
                    maximumSales(startday, endday);
                    continue;
                case "2C":
                    System.out.print("Enter Start Day: ");
                    startday = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter End Day: ");
                    endday = sc.nextInt();
                    sc.nextLine();
                    System.out.println();
                    if (endday >= currentDay) {
                        System.out.println("End day should until the current day.");
                        continue;
                    }
                    topKHighestSales(startday, endday);
                    continue;
                case "2D":
                    System.out.print("Enter Start Day: ");
                    startday = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter End Day: ");
                    endday = sc.nextInt();
                    sc.nextLine();
                    System.out.println();
                    if (endday >= currentDay) {
                        System.out.println("End day should until the current day.");
                        continue;
                    }
                    totalAndAverageSales(startday, endday);
                    continue;
                case "3":
                    // Exit the ViewSales
                    return;  //will return to the calling method
                default:
                    System.out.println("Invalid selection.");
                    continue; // Continue to the next iteration of the loop
            }
        }
    }

    public void viewSales(int day, Map<String, Double> foodPrices) {
        String dayKey = String.valueOf(day);
        // Retrieve the sales data for the specified day and location
        Map<String, SalesRecord> salesByFood = salesData.getOrDefault(currentLocation, new HashMap<>())
                .getOrDefault(dayKey, new HashMap<>());
        // Update the food prices based on the user's input
        for (Map.Entry<String, Double> entry : foodPrices.entrySet()) {
            String food = entry.getKey();
            double newPrice = entry.getValue();
            if (salesByFood.containsKey(food)) {
                SalesRecord record = salesByFood.get(food);
                record.setPrice(newPrice);
            }
        }
        // Print the sales table header
        System.out.println("======================================================================");
        System.out.println("Restaurant: " + currentLocation);
        System.out.println("Day " + day + " Sales");
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.println("| Food                                | Quantity | Total Price |");
        System.out.println("+-------------------------------------+----------+-------------+");

        double totalSales = 0.0;

        // Iterate over the updated sales data and print each food item
        for (Map.Entry<String, SalesRecord> entry : salesByFood.entrySet()) {
            String food = entry.getKey();
            SalesRecord record = entry.getValue();
            int quantity = record.getQuantity();
            double updatedPrice = foodPrices.getOrDefault(food, record.getPrice());
            double totalPrice = updatedPrice * quantity;

            System.out.printf("| %-35s | %8d | $%9.2f  |\n", food, quantity, totalPrice);

            totalSales += totalPrice;
        }
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.printf("| Total Sales                         |          | $%9.2f  |\n", totalSales);
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.println("======================================================================");
    }

    public void minimumSales(int startDay, int endDay) {
        Map<String, SalesRecord> totalSalesByFood = new HashMap<>();

        for (int day = startDay; day <= endDay; day++) {
            String dayKey = String.valueOf(day);
            Map<String, SalesRecord> salesByFood = modifiedSalesData.getOrDefault(currentLocation, new HashMap<>())
                    .getOrDefault(dayKey, new HashMap<>());

            for (Map.Entry<String, SalesRecord> entry : salesByFood.entrySet()) {
                String food = entry.getKey();
                SalesRecord record = entry.getValue();

                int quantity = record.getQuantity();
                double price = record.getPrice();

                if (totalSalesByFood.containsKey(food)) {
                    double updatedTotalPrice = totalSalesByFood.get(food).getPrice() + price;
                    totalSalesByFood.put(food, new SalesRecord(quantity, updatedTotalPrice));
                    // Update the original SalesRecord with the updated price
                    record.setPrice(updatedTotalPrice);
                } else {
                    totalSalesByFood.put(food, new SalesRecord(quantity, price));
                }
            }
        }
        double minimumSales = Double.MAX_VALUE;
        String foodWithMinimumSales = "";

        for (Map.Entry<String, SalesRecord> entry : totalSalesByFood.entrySet()) {
            String food = entry.getKey();
            double foodSales = entry.getValue().getPrice();
            if (foodSales < minimumSales) {
                minimumSales = foodSales;
                foodWithMinimumSales = food;
            }
        }
        System.out.println("======================================================");
        System.out.println("Restaurant: " + currentLocation);
        System.out.println("Minimum Sales (Day " + startDay + " - " + endDay + ")");
        System.out.println("+-------------------------------------+-------------+");
        System.out.println("| Food                                | Total Sales |");
        System.out.println("+-------------------------------------+-------------+");
        System.out.printf("| %-35s | $%9.2f  |\n", foodWithMinimumSales, minimumSales);
        System.out.println("+-------------------------------------+-------------+");
        System.out.println("======================================================");
    }

    public void maximumSales(int startDay, int endDay) {
        Map<String, SalesRecord> totalSalesByFood = new HashMap<>();

        for (int day = startDay; day <= endDay; day++) {
            String dayKey = String.valueOf(day);
            Map<String, SalesRecord> salesByFood = modifiedSalesData.getOrDefault(currentLocation, new HashMap<>())
                    .getOrDefault(dayKey, new HashMap<>());

            for (Map.Entry<String, SalesRecord> entry : salesByFood.entrySet()) {
                String food = entry.getKey();
                SalesRecord record = entry.getValue();

                int quantity = record.getQuantity();
                double price = record.getPrice();

                if (totalSalesByFood.containsKey(food)) {
                    double updatedTotalPrice = totalSalesByFood.get(food).getPrice() + price;
                    totalSalesByFood.put(food, new SalesRecord(quantity, updatedTotalPrice));
                    record.setPrice(updatedTotalPrice);
                } else {
                    totalSalesByFood.put(food, new SalesRecord(quantity, price));
                }
            }
        }

        double maximumSales = 0.0;
        String foodWithMaximumSales = "";

        for (Map.Entry<String, SalesRecord> entry : totalSalesByFood.entrySet()) {
            String food = entry.getKey();
            double foodSales = entry.getValue().getPrice();

            if (foodSales > maximumSales) {
                maximumSales = foodSales;
                foodWithMaximumSales = food;
            }
        }
        System.out.println("======================================================");
        System.out.println("Restaurant: " + currentLocation);
        System.out.println("Maximum Sales (Day " + startDay + " - " + endDay + ")");
        System.out.println("+-------------------------------------+-------------+");
        System.out.println("| Food                                | Total Sales |");
        System.out.println("+-------------------------------------+-------------+");
        System.out.printf("| %-35s | $%9.2f  |\n", foodWithMaximumSales, maximumSales);
        System.out.println("+-------------------------------------+-------------+");
        System.out.println("======================================================");
    }

    public void topKHighestSales(int startDay, int endDay) {
        Map<String, SalesRecord> totalSalesByFood = new HashMap<>();

        for (int day = startDay; day <= endDay; day++) {
            String dayKey = String.valueOf(day);
            Map<String, SalesRecord> salesByFood = modifiedSalesData.getOrDefault(currentLocation, new HashMap<>())
                    .getOrDefault(dayKey, new HashMap<>());

            for (Map.Entry<String, SalesRecord> entry : salesByFood.entrySet()) {
                String food = entry.getKey();
                SalesRecord record = entry.getValue();

                int quantity = record.getQuantity();
                double price = record.getPrice();

                if (totalSalesByFood.containsKey(food)) {
                    double updatedTotalPrice = totalSalesByFood.get(food).getPrice() + price;
                    totalSalesByFood.put(food, new SalesRecord(quantity, updatedTotalPrice));
                    record.setPrice(updatedTotalPrice);
                } else {
                    totalSalesByFood.put(food, new SalesRecord(quantity, price));
                    record.setPrice(price);
                }
            }
        }
        Map.Entry<String, SalesRecord> foodWithHighestPrice = null;
        for (Map.Entry<String, SalesRecord> entry : totalSalesByFood.entrySet()) {
            if (foodWithHighestPrice == null || entry.getValue().getPrice() > foodWithHighestPrice.getValue().getPrice()) {
                foodWithHighestPrice = entry;
            }
        }
        Map.Entry<String, SalesRecord> foodWithHighestQuantity = null;
        for (Map.Entry<String, SalesRecord> entry : totalSalesByFood.entrySet()) {
            if (foodWithHighestQuantity == null || entry.getValue().getQuantity() > foodWithHighestQuantity.getValue().getQuantity()) {
                foodWithHighestQuantity = entry;
            }
        }
        System.out.println("======================================================");
        System.out.println("Restaurant: " + currentLocation);
        System.out.println("Top K Highest Sales (Day " + startDay + " - " + endDay + ")");
        System.out.println("---------------------------------------------------------");
        System.out.println("Food with Highest Total Price:");
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.println("| Food                                | Quantity | Price       |");
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.printf("| %-35s | %8d | $%9.2f  |\n", foodWithHighestPrice.getKey(), foodWithHighestPrice.getValue().getQuantity(), foodWithHighestPrice.getValue().getPrice());
        System.out.println("\n+-------------------------------------+----------+-------------+");
        System.out.println("Food with Highest Quantity:");
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.println("| Food                                | Quantity | Price       |");
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.printf("| %-35s | %8d | $%9.2f  |\n", foodWithHighestQuantity.getKey(), foodWithHighestQuantity.getValue().getQuantity(), foodWithHighestQuantity.getValue().getPrice());
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.println("======================================================");
    }

    public void totalAndAverageSales(int startDay, int endDay) {
        Map<String, SalesRecord> totalSalesByFood = new HashMap<>();
        double totalPrice = 0.0;

        for (int day = startDay; day <= endDay; day++) {
            String dayKey = String.valueOf(day);
            Map<String, SalesRecord> salesByFood = modifiedSalesData.getOrDefault(currentLocation, new HashMap<>())
                    .getOrDefault(dayKey, new HashMap<>());

            for (Map.Entry<String, SalesRecord> entry : salesByFood.entrySet()) {
                String food = entry.getKey();
                SalesRecord record = entry.getValue();

                int quantity = record.getQuantity();
                double price = record.getPrice();

                if (totalSalesByFood.containsKey(food)) {
                    SalesRecord existingRecord = totalSalesByFood.get(food);
                    int updatedQuantity = existingRecord.getQuantity() + quantity;
                    double updatedTotalPrice = existingRecord.getPrice() + price;

                    totalSalesByFood.put(food, new SalesRecord(updatedQuantity, updatedTotalPrice));
                } else {
                    totalSalesByFood.put(food, new SalesRecord(quantity, price));
                }
                totalPrice += price;
            }
        }
        System.out.println("Restaurant: " + currentLocation);
        System.out.println("Total and Average Sales (Day " + startDay + " - " + endDay + ")");
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.println("| Food                                | Quantity | Total Price |");
        System.out.println("+-------------------------------------+----------+-------------+");
        double totalSales = 0;
        int totalQuantity = 0;
        for (Map.Entry<String, SalesRecord> entry : totalSalesByFood.entrySet()) {
            String food = entry.getKey();
            SalesRecord record = entry.getValue();
            totalPrice = record.getPrice();
            int quantity = record.getQuantity();
            totalSales += totalPrice;
            totalQuantity += quantity;

            double averageSales = totalSales / totalQuantity;
            System.out.println("+-------------------------------------+----------+-------------+");
            System.out.printf("| Total Sales                         |          | $%9.2f  |\n", totalPrice);
            System.out.printf("| Average Sales                       |          | $%9.2f  |\n", averageSales);
            System.out.println("+-------------------------------------+----------+-------------+");
            System.out.println("======================================================================");
        }
    }
        public void modifyMenu() {
            restaurant = new Restaurant(restaurant.toString());
            System.out.println(restaurant.getCuurentLocation() + "'s Menu");
            restaurant.printSalesInformation();
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
                    sc.nextLine();
                    restaurant.addFood("", newName, newPrice);
                    break;
                case 2:
                    System.out.print("Enter food name to modify : ");
                    String name = sc.nextLine();
                    System.out.print("Enter new food price : ");
                    newPrice = sc.nextDouble();
                    restaurant.modifyFoodPrice("", "", name, newPrice);
                    break;
                case 3:
                    System.out.print("Enter food name to remove : ");
                    name = sc.nextLine();
                    restaurant.removeFood(name);
                    break;
                case 4:
                    return;
                default:
            }
        }
    }