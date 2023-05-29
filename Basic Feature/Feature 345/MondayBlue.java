package JOJOLands;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

//just need to read and get information from the file
public class MondayBlue {
    private String currentLocation;
    private int currentDay;
    //private HermitPurple hermitPurple;
    private Scanner sc = new Scanner(System.in);

    private Map<String, Map<String, Map<String, SalesRecord>>> salesData;

    public MondayBlue(HermitPurple hermit, String currentLocation, int currentDay) {
        //this.hermitPurple = hermit;
        this.currentLocation = currentLocation;
        this.currentDay = currentDay;
        this.salesData = new HashMap<>();
        
    }

    public void readSalesDataFromFile() {
        for (int day = 1; day <= currentDay; day++) {
            String filename = "waiting_list_" + currentLocation + "_" + day + ".txt";
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                boolean startReading = false;

                // Inside the readSalesDataFromFile() method
                Map<String, SalesRecord> salesByFood = new HashMap<>();

                // To skip header and retrieve data
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Day " + day)) {
                        startReading = true;
                        continue;
                    }
                    if (line.startsWith("+----")) {
                        startReading = false;
                        continue;
                    }

                    if (startReading && line.startsWith("|")) {
                        String[] rowData = line.split("\\|");
                        for (int i = 0; i < rowData.length; i++) {
                            rowData[i] = rowData[i].trim();
                        }
                        String order = rowData[5];
                        double price = Double.parseDouble(rowData[7]);
                        int quantity = 1; // Set quantity to 1 for each line read

                        // Check if the order already exists in the salesByFood map
                        if (salesByFood.containsKey(order)) {
                            // If it exists, update the quantity
                            SalesRecord existingRecord = salesByFood.get(order);
                            int updatedQuantity = existingRecord.getQuantity() + quantity;
                            double totalPrice = existingRecord.getPrice() + price;

                            salesByFood.put(order, new SalesRecord(updatedQuantity, totalPrice));
                        } else {
                            // If it's a new order, add it to the salesByFood map
                            salesByFood.put(order, new SalesRecord(quantity, price));
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

    public void ViewSalesInformation() {
        String selection = "";
        while (true) {
            int startday;
            int endday;
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
            switch (selection) {
                case "1":
                    System.out.print("Enter Day: ");
                    int day = sc.nextInt();
                    System.out.println();
                    viewSales(day);
                    continue;
                case "2A":
                    System.out.print("Enter Start Day: ");
                    startday = sc.nextInt();
                    System.out.print("Enter End Day: ");
                    endday = sc.nextInt();
                    System.out.println();
                    minimumSales(startday, endday);
                    continue;
                case "2B":
                    System.out.print("Enter Start Day: ");
                    startday = sc.nextInt();
                    System.out.print("Enter End Day: ");
                    endday = sc.nextInt();
                    System.out.println();
                    maximumSales(startday, endday);
                    continue;
                case "2C":
                    System.out.print("Enter Start Day: ");
                    startday = sc.nextInt();
                    System.out.print("Enter End Day: ");
                    endday = sc.nextInt();
                    System.out.println();
                    topKHighestSales(startday, endday);
                    continue;
                case "2D":
                    System.out.print("Enter Start Day: ");
                    startday = sc.nextInt();
                    System.out.print("Enter End Day: ");
                    endday = sc.nextInt();
                    System.out.println();
                    totalAndAverageSales(startday, endday);
                    continue;
                case "3":
                    // Exit the ViewSales
                    //hermitPurple.displayMenu();
                    return;  //will return to the calling method
                default:
                    System.out.println("Invalid selection.");
                    continue; // Continue to the next iteration of the loop
                }
            }
        }

    // Used to print the Sales Record on certain Day of the restaurant
    public void viewSales(int day) {
        System.out.println("Restaurant: " + currentLocation);
        String dayKey = String.valueOf(day);

        // Retrieve the sales data for the specified day and location
        Map<String, SalesRecord> salesByFood = salesData.getOrDefault(currentLocation, new HashMap<>())
                .getOrDefault(dayKey, new HashMap<>());

        // Print the sales table header
        System.out.println("======================================================================");
        System.out.println("Restaurant: " + currentLocation);
        System.out.println("Day " + day + " Sales");
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.println("| Food                                | Quantity | Total Price |");
        System.out.println("+-------------------------------------+----------+-------------+");

        double totalSales = 0.0;

        // Iterate over the sales data and print each food item
        for (Map.Entry<String, SalesRecord> entry : salesByFood.entrySet()) {
            String food = entry.getKey();
            SalesRecord record = entry.getValue();

            int quantity = record.getQuantity();
            double totalPrice = record.getPrice(); // dont need to *quantity

            System.out.printf("| %-35s | %8d | $%9.2f |\n", food, quantity, totalPrice);

            totalSales += totalPrice;
        }

        // Print the total sales
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.printf("| Total Sales                         |          | $%9.2f |\n", totalSales);
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.println("======================================================================");
    }

    public void minimumSales(int startDay, int endDay) {
        // Retrieve the sales data for the specified range of days and location
        Map<String, SalesRecord> totalSalesByFood = new HashMap<>();

        // Iterate over the range of days and accumulate the total sales data
        for (int day = startDay; day <= endDay; day++) {
            String dayKey = String.valueOf(day);
            Map<String, SalesRecord> salesByFood = salesData.getOrDefault(currentLocation, new HashMap<>())
                    .getOrDefault(dayKey, new HashMap<>());

            // Iterate over the sales data for each day and update the total sales
            for (Map.Entry<String, SalesRecord> entry : salesByFood.entrySet()) {
                String food = entry.getKey();
                SalesRecord record = entry.getValue();

                int quantity = record.getQuantity();
                double price = record.getPrice();

                // Check if the food already exists in the total sales map
                if (totalSalesByFood.containsKey(food)) {
                    // If it exists, update the total price
                    double updatedTotalPrice = totalSalesByFood.get(food).getPrice() + price;
                    totalSalesByFood.put(food, new SalesRecord(quantity, updatedTotalPrice));
                } else {
                    // If it's a new food, add it to the total sales map
                    totalSalesByFood.put(food, new SalesRecord(quantity, price));
                }
            }
        }

        // Find the minimum sales (Assume max, then replace with smaller one)
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

        // Print the minimum sales
        System.out.println("======================================================");
        System.out.println("Restaurant: " + currentLocation);
        System.out.println("Minimum Sales (Day " + startDay + " - " + endDay + ")");
        System.out.println("+-------------------------------------+-------------+");
        System.out.println("| Food                                | Total Sales |");
        System.out.println("+-------------------------------------+-------------+");
        System.out.printf("| %-35s | $%9.2f |\n", foodWithMinimumSales, minimumSales);
        System.out.println("+-------------------------------------+-------------+");
        System.out.println("======================================================");
    }

    public void maximumSales(int startDay, int endDay) {
        // Retrieve the sales data for the specified range of days and location
        Map<String, SalesRecord> totalSalesByFood = new HashMap<>();

        // Iterate over the range of days and accumulate the total sales data
        for (int day = startDay; day <= endDay; day++) {
            String dayKey = String.valueOf(day);
            Map<String, SalesRecord> salesByFood = salesData.getOrDefault(currentLocation, new HashMap<>())
                    .getOrDefault(dayKey, new HashMap<>());

            // Iterate over the sales data for each day and update the total sales
            for (Map.Entry<String, SalesRecord> entry : salesByFood.entrySet()) {
                String food = entry.getKey();
                SalesRecord record = entry.getValue();

                int quantity = record.getQuantity();
                double price = record.getPrice();

                // Check if the food already exists in the total sales map
                if (totalSalesByFood.containsKey(food)) {
                    // If it exists, update the total price
                    double updatedTotalPrice = totalSalesByFood.get(food).getPrice() + price;
                    totalSalesByFood.put(food, new SalesRecord(quantity, updatedTotalPrice));
                } else {
                    // If it's a new food, add it to the total sales map
                    totalSalesByFood.put(food, new SalesRecord(quantity, price));
                }
            }
        }

        // Find the maximum sales
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

        // Print the maximum sales
        System.out.println("======================================================");
        System.out.println("Restaurant: " + currentLocation);
        System.out.println("Maximum Sales (Day " + startDay + " - " + endDay + ")");
        System.out.println("+-------------------------------------+-------------+");
        System.out.println("| Food                                | Total Sales |");
        System.out.println("+-------------------------------------+-------------+");
        System.out.printf("| %-35s | $%9.2f |\n", foodWithMaximumSales, maximumSales);
        System.out.println("+-------------------------------------+-------------+");
        System.out.println("======================================================");
    }

    public void topKHighestSales(int startDay, int endDay) {
        // Retrieve the sales data for the specified range of days and location
        Map<String, SalesRecord> totalSalesByFood = new HashMap<>();
    
        // Iterate over the range of days and accumulate the total sales data
        for (int day = startDay; day <= endDay; day++) {
            String dayKey = String.valueOf(day);
            Map<String, SalesRecord> salesByFood = salesData.getOrDefault(currentLocation, new HashMap<>())
                    .getOrDefault(dayKey, new HashMap<>());
    
            // Iterate over the sales data for each day and update the total sales
            for (Map.Entry<String, SalesRecord> entry : salesByFood.entrySet()) {
                String food = entry.getKey();
                SalesRecord record = entry.getValue();
    
                int quantity = record.getQuantity();
                double price = record.getPrice();
    
                // Check if the food already exists in the total sales map
                if (totalSalesByFood.containsKey(food)) {
                    // If it exists, update the total price
                    double updatedTotalPrice = totalSalesByFood.get(food).getPrice() + price;
                    totalSalesByFood.put(food, new SalesRecord(quantity, updatedTotalPrice));
                } else {
                    // If it's a new food, add it to the total sales map
                    totalSalesByFood.put(food, new SalesRecord(quantity, price));
                }
            }
        }
    
        // Find the food with the highest total price
        Map.Entry<String, SalesRecord> foodWithHighestPrice = null;
        for (Map.Entry<String, SalesRecord> entry : totalSalesByFood.entrySet()) {
            if (foodWithHighestPrice == null || entry.getValue().getPrice() > foodWithHighestPrice.getValue().getPrice()) {
                foodWithHighestPrice = entry;
            }
        }
    
        // Find the food with the highest quantity
        Map.Entry<String, SalesRecord> foodWithHighestQuantity = null;
        for (Map.Entry<String, SalesRecord> entry : totalSalesByFood.entrySet()) {
            if (foodWithHighestQuantity == null || entry.getValue().getQuantity() > foodWithHighestQuantity.getValue().getQuantity()) {
                foodWithHighestQuantity = entry;
            }
        }
    
        // Print the results
        System.out.println("======================================================");
        System.out.println("Restaurant: " + currentLocation);
        System.out.println("Top K Highest Sales (Day " + startDay + " - " + endDay + ")");
        System.out.println("---------------------------------------------------------");
        System.out.println("Food with Highest Total Price:");
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.println("| Food                                | Quantity | Price       |");
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.printf("| %-35s | %8d | $%9.2f |\n", foodWithHighestPrice.getKey(), foodWithHighestPrice.getValue().getQuantity(), foodWithHighestPrice.getValue().getPrice());
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.println("Food with Highest Quantity:");
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.println("| Food                                | Quantity | Price       |");
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.printf("| %-35s | %8d | $%9.2f |\n", foodWithHighestQuantity.getKey(), foodWithHighestQuantity.getValue().getQuantity(), foodWithHighestQuantity.getValue().getPrice());
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.println("======================================================");
    }

    public void totalAndAverageSales(int startDay, int endDay) {
        // Retrieve the sales data for the specified range of days and location
        Map<String, SalesRecord> totalSalesByFood = new HashMap<>();
        double totalPrice = 0.0;

        // Iterate over the range of days and accumulate the total sales data
        for (int day = startDay; day <= endDay; day++) {
            String dayKey = String.valueOf(day);
            Map<String, SalesRecord> salesByFood = salesData.getOrDefault(currentLocation, new HashMap<>())
                    .getOrDefault(dayKey, new HashMap<>());

            // Iterate over the sales data for each day and update the total sales
            for (Map.Entry<String, SalesRecord> entry : salesByFood.entrySet()) {
                String food = entry.getKey();
                SalesRecord record = entry.getValue();

                int quantity = record.getQuantity();
                double price = record.getPrice();


                // Check if the food already exists in the total sales map
                if (totalSalesByFood.containsKey(food)) {
                    // If it exists, update the quantity and total price
                    SalesRecord existingRecord = totalSalesByFood.get(food);
                    int updatedQuantity = existingRecord.getQuantity() + quantity;
                    double updatedTotalPrice = existingRecord.getPrice() + price;

                    totalSalesByFood.put(food, new SalesRecord(updatedQuantity, updatedTotalPrice));
                } else {
                    // If it's a new food, add it to the total sales map
                    totalSalesByFood.put(food, new SalesRecord(quantity, price));
                }
            }
        }

        // Print the total and average sales
        System.out.println("Restaurant: " + currentLocation);
        System.out.println("Total and Average Sales (Day " + startDay + " - " + endDay + ")");
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.println("| Food                                | Quantity | Total Price |");
        System.out.println("+-------------------------------------+----------+-------------+");

        for (Map.Entry<String, SalesRecord> entry : totalSalesByFood.entrySet()) {
            String food = entry.getKey();
            SalesRecord record = entry.getValue();

            int quantity = record.getQuantity();
            double totalFoodPrice = record.getPrice();

            System.out.printf("| %-35s | %8d | $%9.2f |\n", food, quantity, totalFoodPrice);
        }

        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.printf("| Total Sales                         |          | $%9.2f |\n", totalPrice);
        System.out.printf("| Average Sales                       |          | $%9.2f |\n",
                totalPrice / totalSalesByFood.size());
        System.out.println("+-------------------------------------+----------+-------------+");
        System.out.println("======================================================================");
    }

}

// used as Object to save the content from file
class SalesRecord {
    private int quantity;
    private double price;

    public SalesRecord(int quantity, double price) {
        this.quantity = quantity;
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}

/*
 * Assumption:
 * need use file to save the waiting list
 * use table
 * extract waiting list, then calculate total sales, if the search key match
 * with the FOOD, then count++, then total price add
 * create new sales list, then create method to view the total and type of food
 * and price each day
 * create a method that can specify the day range, then a method that used to
 * count the total sale food and price
 * create a method that can print the sales list for day range, loop day ? to
 * day ? then displayList
 * total sales and price for day range, add on all for the food
 * VIEW SALE = can only for one day
 * VIEW AGGREGATE Sale, here make assumption:
 * show whole record for ? day
 * max= choose day with highest sale
 * min = day with lowest sale
 * top k highest = top ?food to show it has highest sales, whch food sold
 * highest price
 * 
 * 
 * The code snippet salesData.computeIfAbsent(currentLocation, k -> new
 * HashMap<>()).put(String.valueOf(day), salesDataByDay); is responsible for
 * organizing and storing the sales data in a nested data structure.
 * 
 * Let's break down the code:
 * 
 * salesData: It is a Map object that stores the sales data for different
 * restaurants. It has the following structure: Map<String, Map<String,
 * List<String[]>>>. The outer Map is keyed by the restaurant's name (String),
 * and the corresponding value is an inner Map that stores the sales data for
 * each day.
 * 
 * computeIfAbsent(currentLocation, k -> new HashMap<>()): This method is used
 * to retrieve the inner Map for the given currentLocation (restaurant name). If
 * the inner Map already exists for the restaurant, it is returned. Otherwise, a
 * new empty HashMap is created and associated with the restaurant name.
 * 
 * .put(String.valueOf(day), salesDataByDay): Once we have the inner Map for the
 * restaurant, we put the salesDataByDay list (which contains the sales
 * information for a specific day) into the inner Map. The key for this entry is
 * the string representation of the day.
 * 
 * In summary, this code segment ensures that the sales data is organized and
 * stored in a nested structure, allowing easy retrieval and access to the sales
 * information for a specific restaurant and day.
 */