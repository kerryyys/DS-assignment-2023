package JOJOLands.JOJO;

import java.util.*;
import java.io.*;

class Food {
    private String name;
    private double price;
    public Food(String name, double defaultPrice) {
        this.name = name;
        this.price = defaultPrice;
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
        this.currentDay = currentDay;
        this.resident = new Resident();
    }
}
public class MilagroMan {
    private Restaurant restaurant;
    private String currentLocation;
    private Scanner sc;
    private int currentDay;
    private String foodName;
    private Double foodPrices;
    private int sDay;
    private int eDay;
    private Map<String, Map<String, Map<String, SalesRecord>>> salesData;
    private String directory = HermitPurple.directoryPath;

    public MilagroMan(HermitPurple hermit, String currentLocation, int currentDay) {
        this.currentLocation = currentLocation;
        this.currentDay = currentDay;
        this.foodName = "";
        this.foodPrices = 0.00;
        this.sDay = 0;
        this.eDay = 0;
        this.sc = new Scanner(System.in);
        this.restaurant = new Restaurant(currentLocation);
        this.salesData = new HashMap<>();
    }
    public void enterExperimentalMode() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=======================================================================");
            System.out.println("Restaurant: " + currentLocation + " (Milagro Man mode) ");
            System.out.println("[1] Modify Food Prices");
            System.out.println("[2] Add Food");
            System.out.println("[3] Remove Food");
            System.out.println("[4] View Sales Information");
            System.out.println("[5] Exit Milagro Man");
            System.out.print("Select: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    modifyFoodPrice();
                    break;
                case "2":
                    addFood();
                    break;
                case "3":
                    removeFood();
                case "4":
                    viewSalesInformation();
                case "5":
                    System.out.println("=======================================================================");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
    public void readSalesDataFromFile() {
        for (int day = 1; day < currentDay; day++) {
            String filename = directory + "\\waiting_list_" + currentLocation + "_" + day + ".txt";
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                boolean startReading = false;
                boolean isFirstLine = true; // skip header
                Map<String, SalesRecord> salesByFood = new HashMap<>();

                // To skip header and retrieve data
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Waiting List")) {
                        startReading = false;
                        continue;
                    }
                    if (line.startsWith("+----")) {
                        startReading = true;
                        continue;
                    }
                    if (startReading && line.startsWith("|") && !line.contains("Order")) {
                        if (isFirstLine) {
                            isFirstLine = false;
                            continue; // Skip the first line
                        }
                        // Modify the split regex to handle inconsistent column spacing
                        String[] rowData = line.split("\\|");

                        // Trim each element in rowData and remove leading/trailing whitespaces
                        for (int i = 0; i < rowData.length; i++) {
                            rowData[i] = rowData[i].trim();
                        }

                        // Retrieve the order and price from the corresponding columns
                        String order = rowData[6];
                        double price = Double.parseDouble(rowData[8].replaceAll("[^\\d.]", ""));

                        // Check if the order already exists in the salesByFood map
                        if (salesByFood.containsKey(order)) {
                            // If it exists, update the quantity
                            SalesRecord existingRecord = salesByFood.get(order);
                            int updatedQuantity = existingRecord.getQuantity() + 1;
                            double totalPrice = existingRecord.getPrice() + price;

                            salesByFood.put(order, new SalesRecord(updatedQuantity, totalPrice));
                        } else {
                            // If it's a new order, add it to the salesByFood map
                            salesByFood.put(order, new SalesRecord(1, price));
                        }
                    }
                }

                // Store the salesByFood map in the salesData map
                salesData.computeIfAbsent(currentLocation, k -> new HashMap<>())
                        .put(String.valueOf(day), salesByFood);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void modifyFoodPrice() {
        System.out.println("=======================================================================");
        System.out.print("Enter the food name: ");
        this.foodName = sc.nextLine();
        System.out.print("Enter the new price: $");
        this.foodPrices = sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter the starting day: ");
        this.sDay = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the ending day: ");
        this.eDay = sc.nextInt();
        sc.nextLine();
        for (int day = sDay + 1; day <= eDay; day++) {
            String dayKey = String.valueOf(day);
            Map<String, SalesRecord> salesByFood = salesData.getOrDefault(currentLocation, new HashMap<>()).
                    getOrDefault(dayKey, new HashMap<>());
                SalesRecord existingRecord = salesByFood.get(foodName);
                if (existingRecord != null) {
                    existingRecord.setPrice(foodPrices);
                    salesByFood.put(foodName, existingRecord);
                }
            }
            System.out.println("Food price modified successfully!");
    }
    private void addFood() {
        System.out.println("=======================================================================");
        System.out.print("Enter the food name: ");
        this.foodName = sc.nextLine();
        System.out.print("Enter the price: $");
        this.foodPrices = sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter the starting day: ");
        this.sDay = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the ending day: ");
        this.eDay = sc.nextInt();
        sc.nextLine();
        Map<String, SalesRecord> newFoodSales = new HashMap<>();
        int quantity = new Random().nextInt(8) + 1;
        for (int day = sDay + 1; day <= eDay; day++) {
            String dayKey = String.valueOf(day);
            newFoodSales.put(String.valueOf(day), new SalesRecord(quantity, foodPrices));
            Map<String, SalesRecord> salesByFood = salesData.computeIfAbsent(currentLocation, k -> new HashMap<>()).
                    computeIfAbsent(dayKey, k -> new HashMap<>());
            salesByFood.put(foodName, newFoodSales.get(dayKey));
        }
        System.out.println("Food added successfully!");
    }
    private void removeFood() {
        System.out.println("=======================================================================");
        System.out.print("Enter the food name: ");
        this.foodName = sc.nextLine();
        System.out.print("Enter the starting day: ");
        this.sDay = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the ending day: ");
        this.eDay = sc.nextInt();
        sc.nextLine();
        for (int day = sDay + 1; day <= eDay; day++) {
            String dayKey = String.valueOf(day);
            Map<String, SalesRecord> salesByFood = salesData.getOrDefault(currentLocation, new HashMap<>()).
                    getOrDefault(dayKey, new HashMap<>());
            // Remove the food from salesByFood if it exists
            if (salesByFood.containsKey(foodName)) {
                salesByFood.remove(foodName);
                System.out.println("Food " + foodName + " removed from the menu for day " + day);
            } else {
                System.out.println("Food " + foodName + " not found on the menu for day " + day);
            }
        }
    }
    private void viewSalesInformation() {
        String selection = "";
        while (true) {
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
                    viewSales();
                    continue;
                case "2A":
                    minimumSales();
                    continue;
                case "2B":
                    maximumSales();
                    continue;
                case "2C":
                    topKHighestSales();
                    continue;
                case "2D":
                    totalAndAverageSales();
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

    public void viewSales() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter day : ");
        int day = sc.nextInt();
        // Retrieve the sales data for the specified day and location
        if (day >= sDay + 1 && day <= eDay) {
            String dayKey = String.valueOf(day);
            Map<String, SalesRecord> salesByFood = salesData.getOrDefault(currentLocation, new HashMap<>()).getOrDefault(dayKey, new HashMap<>());
            // Print the sales table header
            System.out.println("======================================================================");
            System.out.println("Restaurant: " + currentLocation);
            System.out.println("Day " + day + " Sales");
            System.out.println("+-------------------------------------+----------+-------------+");
            System.out.println("| Food                                | Quantity | Total Price |");
            System.out.println("+-------------------------------------+----------+-------------+");

            double totalSales = 0.0;
            int quantity = 0;

            // Iterate over the sales data and print each food item
            for (Map.Entry<String, SalesRecord> entry : salesByFood.entrySet()) {
                String food = entry.getKey();
                SalesRecord record = entry.getValue();
                double totalPrice = 0.00;
                    quantity = record.getQuantity();
                    totalPrice = record.getPrice();
                System.out.printf("| %-35s | %8d | $%9.2f  |\n", food, quantity, totalPrice);
                totalSales += totalPrice;
            }
            // Print the total sales
            System.out.println("+-------------------------------------+----------+-------------+");
            System.out.printf("| Total Sales                         |          | $%9.2f  |\n", totalSales);
            System.out.println("+-------------------------------------+----------+-------------+");
            System.out.println("======================================================================");
        }
    }

    public void minimumSales() {
        Map<String, SalesRecord> totalSalesByFood = new HashMap<>();
        System.out.print("Enter Start Day: ");
        sDay = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter End Day: ");
        eDay = sc.nextInt();
        sc.nextLine();
        System.out.println();
        if (eDay >= currentDay) {
            System.out.println("End day should until the current day.");
        }
        for (int day = sDay + 1; day <= eDay; day++){
            String dayKey = String.valueOf(day);
            Map<String, SalesRecord> salesByFood = salesData.getOrDefault(currentLocation, new HashMap<>())
                    .getOrDefault(dayKey, new HashMap<>());
            int quantity = 0;
            double price = 0.0;
            for (Map.Entry<String, SalesRecord> entry : salesByFood.entrySet()) {
                String food = entry.getKey();
                SalesRecord record = entry.getValue();
                if (food.equals(foodName)) {
                    quantity = record.getQuantity();
                    price = foodPrices;
                } else {
                    quantity = record.getQuantity();
                    price = record.getPrice();
                }
                if (totalSalesByFood.containsKey(food)) {
                    if (food.equals(foodName)) {
                        double updatedTotalPrice = totalSalesByFood.get(foodName).getPrice() + foodPrices;
                        totalSalesByFood.put(foodName, new SalesRecord(quantity, updatedTotalPrice));
                        // Update the original SalesRecord with the updated price
                        record.setPrice(updatedTotalPrice);
                    } else {
                        double updatedTotalPrice = totalSalesByFood.get(food).getPrice() + price;
                        totalSalesByFood.put(food, new SalesRecord(quantity, updatedTotalPrice));
                        // Update the original SalesRecord with the updated price
                        record.setPrice(updatedTotalPrice);
                    }
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
        System.out.println("Minimum Sales (Day " + sDay + " - " + eDay + ")");
        System.out.println("+-------------------------------------+-------------+");
        System.out.println("| Food                                | Total Sales |");
        System.out.println("+-------------------------------------+-------------+");
        System.out.printf("| %-35s | $%9.2f  |\n", foodWithMinimumSales, minimumSales);
        System.out.println("+-------------------------------------+-------------+");
        System.out.println("======================================================");
    }

    public void maximumSales() {
        Map<String, SalesRecord> totalSalesByFood = new HashMap<>();
        System.out.print("Enter Start Day: ");
        sDay = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter End Day: ");
        eDay = sc.nextInt();
        sc.nextLine();
        System.out.println();
        for (int day = sDay + 1; day <= eDay; day++) {
            String dayKey = String.valueOf(day);
            Map<String, SalesRecord> salesByFood = salesData.getOrDefault(currentLocation, new HashMap<>())
                    .getOrDefault(dayKey, new HashMap<>());
            int quantity = 0;
            double price = 0.0;
            for (Map.Entry<String, SalesRecord> entry : salesByFood.entrySet()) {
                String food = entry.getKey();
                SalesRecord record = entry.getValue();
                if (food.equals(foodName)) {
                    quantity = record.getQuantity();
                    price = foodPrices;
                } else {
                    quantity = record.getQuantity();
                    price = record.getPrice();
                }
                if (totalSalesByFood.containsKey(food)) {
                    if (food.equals(foodName)) {
                        double updatedTotalPrice = totalSalesByFood.get(foodName).getPrice() + foodPrices;
                        totalSalesByFood.put(foodName, new SalesRecord(quantity, updatedTotalPrice));
                        record.setPrice(updatedTotalPrice);
                    } else {
                        double updatedTotalPrice = totalSalesByFood.get(food).getPrice() + price;
                        totalSalesByFood.put(food, new SalesRecord(quantity, updatedTotalPrice));
                        record.setPrice(updatedTotalPrice);
                    }
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
        System.out.println("Maximum Sales (Day " + sDay + " - " + eDay + ")");
        System.out.println("+-------------------------------------+-------------+");
        System.out.println("| Food                                | Total Sales |");
        System.out.println("+-------------------------------------+-------------+");
        System.out.printf("| %-35s | $%9.2f  |\n", foodWithMaximumSales, maximumSales);
        System.out.println("+-------------------------------------+-------------+");
        System.out.println("======================================================");
    }

    public void topKHighestSales() {
        Map<String, SalesRecord> totalSalesByFood = new HashMap<>();
        System.out.print("Enter Start Day: ");
        sDay = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter End Day: ");
        eDay = sc.nextInt();
        sc.nextLine();
        System.out.println();
        if (eDay >= currentDay) {
            System.out.println("End day should until the current day.");
        }
        for (int day = sDay + 1; day <= eDay; day++) {
            String dayKey = String.valueOf(day);
            Map<String, SalesRecord> salesByFood = salesData.getOrDefault(currentLocation, new HashMap<>())
                    .getOrDefault(dayKey, new HashMap<>());

            for (Map.Entry<String, SalesRecord> entry : salesByFood.entrySet()) {
                String food = entry.getKey();
                SalesRecord record = entry.getValue();
                int quantity = 0;
                double price = 0.0;
                if (food.equals(foodName)) {
                    quantity = record.getQuantity();
                    price = foodPrices;
                } else {
                    quantity = record.getQuantity();
                    price = record.getPrice();
                }
                if (totalSalesByFood.containsKey(food)) {
                    if (food.equals(foodName)) {
                        double updatedTotalPrice = totalSalesByFood.get(foodName).getPrice() + foodPrices;
                        totalSalesByFood.put(foodName, new SalesRecord(quantity, updatedTotalPrice));
                        record.setPrice(updatedTotalPrice);
                    } else {
                        double updatedTotalPrice = totalSalesByFood.get(food).getPrice() + price;
                        totalSalesByFood.put(food, new SalesRecord(quantity, updatedTotalPrice));
                        record.setPrice(updatedTotalPrice);
                    }
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
        System.out.println("Top K Highest Sales (Day " + sDay + " - " + eDay + ")");
        System.out.println("---------------------------------------------------------");
        System.out.println("Food with Highest Total Price:");
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.println("| Food                                | Quantity | Price       |");
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.printf("| %-35s | %8d | $%9.2f  |\n", foodWithHighestPrice.getKey(), foodWithHighestPrice.getValue().getQuantity(), foodWithHighestPrice.getValue().getPrice());
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.println("Food with Highest Quantity:");
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.println("| Food                                | Quantity | Price       |");
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.printf("| %-35s | %8d | $%9.2f  |\n", foodWithHighestQuantity.getKey(), foodWithHighestQuantity.getValue().getQuantity(), foodWithHighestQuantity.getValue().getPrice());
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.println("======================================================");
    }

    public void totalAndAverageSales() {
        Map<String, SalesRecord> totalSalesByFood = new HashMap<>();
        double totalPrice = 0.0;
        System.out.print("Enter Start Day: ");
        sDay = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter End Day: ");
        eDay = sc.nextInt();
        sc.nextLine();
        System.out.println();
        if (eDay >= currentDay) {
            System.out.println("End day should until the current day.");
        }
        int quantity = 0;
        double price = 0.0;
        for (int day = sDay; day <= eDay; day++) {
            String dayKey = String.valueOf(day);
            Map<String, SalesRecord> salesByFood = salesData.getOrDefault(currentLocation, new HashMap<>())
                    .getOrDefault(dayKey, new HashMap<>());
            for (Map.Entry<String, SalesRecord> entry : salesByFood.entrySet()) {
                String food = entry.getKey();
                SalesRecord record = entry.getValue();
                int updatedQuantity = 0;
                double updatedTotalPrice = 0.0;
                if (food.equals(foodName)) {
                    quantity = record.getQuantity();
                    price = foodPrices;
                } else {
                    quantity = record.getQuantity();
                    price = record.getPrice();
                }
                if (totalSalesByFood.containsKey(food)) {
                    if (food.equals(foodName)) {
                        SalesRecord existingRecord = totalSalesByFood.get(foodName);
                        updatedQuantity = existingRecord.getQuantity() + quantity;
                        updatedTotalPrice = existingRecord.getPrice() + foodPrices * quantity;
                        totalSalesByFood.put(food, new SalesRecord(updatedQuantity, updatedTotalPrice));
                    } else {
                        SalesRecord existingRecord = totalSalesByFood.get(food);
                        updatedQuantity = existingRecord.getQuantity() + quantity;
                        updatedTotalPrice = existingRecord.getPrice() + price * quantity;
                        totalSalesByFood.put(food, new SalesRecord(updatedQuantity, updatedTotalPrice));
                    }
                } else {
                    totalSalesByFood.put(food, new SalesRecord(quantity, price));
                }
                totalPrice += price + updatedTotalPrice;
            }
        }
        System.out.println("Restaurant: " + currentLocation);
        System.out.println("Total and Average Sales (Day " + sDay + " - " + eDay + ")");
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.println("| Food                                | Quantity | Total Price |");
        System.out.println("+-------------------------------------+----------+-------------+");
        for (Map.Entry<String, SalesRecord> entry : totalSalesByFood.entrySet()) {
            String food = entry.getKey();
            SalesRecord record = entry.getValue();
            double totalFoodPrice = 0.0;
            if (food.equals(foodName)) {
                quantity = record.getQuantity();
                totalFoodPrice = foodPrices * quantity;
            } else {
                quantity = record.getQuantity();
                totalFoodPrice = record.getPrice();
            }
            System.out.printf("| %-35s | %8d | $%9.2f  |\n", food, quantity, totalFoodPrice);
        }
            System.out.println("+-------------------------------------+----------+-------------+");
            System.out.printf("| Total Sales                         |          | $%9.2f  |\n", totalPrice);
            System.out.printf("| Average Sales                       |          | $%9.2f  |\n", totalPrice / totalSalesByFood.size());
            System.out.println("+-------------------------------------+----------+-------------+");
            System.out.println("======================================================================");
    }
}