package JOJOLands;

import java.util.*;

public class MilagroMan {
    private String currentLocation;
    private Map<String, Map<String, SalesRecord>> salesByDay = new HashMap<>();
    private int day;
    private MoodyBlue MoodyBlue;
    public MilagroMan(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void enterExperimentalMode() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=======================================================================");
            System.out.println("Restaurant: " + currentLocation);
            System.out.println("[1] Modify Food Prices");
            System.out.println("[2] View Sales Information");
            System.out.println("[3] Exit Milagro Man");
            System.out.print("Select: ");
            String option = scanner.nextLine();

            if (option.equals("1")) {
                modifyFoodPrices();
            } else if (option.equals("2")) {
                viewSalesInformation();
            } else if (option.equals("3")) {
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void modifyFoodPrices() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=======================================================================");
        System.out.println("Restaurant: " + currentLocation);
        System.out.print("Enter food name: ");
        String foodName = scanner.nextLine();
        System.out.print("Enter new price: $");
        double newPrice = scanner.nextDouble();
        System.out.print("Enter Start Day: ");
        int startDay = scanner.nextInt();
        System.out.print("Enter End Day: ");
        int endDay = scanner.nextInt();

        Map<String, SalesRecord> salesByFood = new HashMap<>();

        for (int day = startDay; day <= endDay; day++) {
            String dayKey = String.valueOf(day);
            if (salesByDay.containsKey(dayKey)) {
                salesByFood = salesByDay.get(dayKey);
            }
        }
        SalesRecord salesRecord = salesByFood.get(foodName);
        if (salesRecord != null) {
            double modifiedPrice = scanner.nextDouble();
            // Update the price for the food item
            salesRecord.setPrice(modifiedPrice);
            salesByFood.put(foodName, salesRecord);
        } else {
            System.out.println("No sales record found for food: " + foodName + " on Day " + day);
        }
    }

    public void viewSalesInformation() {
        Scanner sc = new Scanner(System.in);
        String selection = "";
        while (true) {
            int startday;
            int endday;
            System.out.println("=======================================================================");
            System.out.println("Restaurant: " + currentLocation);
            System.out.println("Sales Information");
            System.out.println("[1] View Sales");
            System.out.println("[2] View Aggregated Information");
            System.out.println("[A] Minimum Sales");
            System.out.println("[B] Maximum Sales");
            System.out.println("[C] Top k Highest Sales");
            System.out.println("[D] Total and Average Sales");
            System.out.println("[3] Exit");

            System.out.print("Select: ");
            String option = sc.nextLine();

            switch (selection) {
                case"1":
                    System.out.print("Enter Day: ");
                    int day = sc.nextInt();
                    System.out.println();
                    MoodyBlue.viewSales(day);
                    continue;
                case "2A":
                    System.out.print("Enter Start Day: ");
                    startday = sc.nextInt();
                    System.out.print("Enter End Day: ");
                    endday = sc.nextInt();
                    System.out.println();
                    MoodyBlue.minimumSales(startday, endday);
                    continue;
                case "2B":
                    System.out.print("Enter Start Day: ");
                    startday = sc.nextInt();
                    System.out.print("Enter End Day: ");
                    endday = sc.nextInt();
                    System.out.println();
                    MoodyBlue.maximumSales(startday, endday);
                    continue;
                case "2C":
                    System.out.print("Enter Start Day: ");
                    startday = sc.nextInt();
                    System.out.print("Enter End Day: ");
                    endday = sc.nextInt();
                    System.out.println();
                    MoodyBlue.topKHighestSales(startday, endday);
                    continue;
                case "2D":
                    System.out.print("Enter Start Day: ");
                    startday = sc.nextInt();
                    System.out.print("Enter End Day: ");
                    endday = sc.nextInt();
                    System.out.println();
                    MoodyBlue.totalAndAverageSales(startday, endday);
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
}
