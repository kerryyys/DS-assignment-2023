package JOJOLands.JOJO;

import java.io.*;
import java.util.*;

//Once this class is call, it will work to assign list for all resident, just call the method to generate the waiting list
public class WaitingListGenerator {
    private List<String[]> ResidentFullList;
    private List<Resident> residents;
    private Menu MENU;
    private Random rand;
    private int currentDay;
    private String[] restaurantLocation;
    private static List<String[]> FilteredWaitingList;
    public static List<String[]> JadeGardenWaitingList;
    public static List<String[]> SavageGardenWaitingList;
    public static List<String[]> CafeDeuxMagotsWaitingList;
    public static List<String[]> TrattoriaTrussardiWaitingList;
    public static List<String[]> LibeccioWaitingList;

    public WaitingListGenerator(String currentLocation, int currentDay) {
        // Instantiate the Resident class and read the CSV file
        Resident resident = new Resident();
        resident.readResidentsCSV("residents.csv");
        resident.readStandsCSV("stands.csv");
        resident.combineResidentsAndStands();

        // Access resident information using getter methods
        residents = resident.getCombinedResidents();

        restaurantLocation = new String[] { "Jade Garden", "Cafe Deux Magots", "Trattoria Trussardi", "Libeccio",
                "Savage Garden" };
        this.ResidentFullList = new ArrayList<>();
        JadeGardenWaitingList = new ArrayList<>();
        CafeDeuxMagotsWaitingList = new ArrayList<>();
        TrattoriaTrussardiWaitingList = new ArrayList<>();
        LibeccioWaitingList = new ArrayList<>();
        SavageGardenWaitingList = new ArrayList<>();
        this.currentDay = currentDay;
        MENU = new Menu();
        this.rand = new Random();
    }

    // need to call in HermitPurple whenever start a new day, cannot call at other
    // time
    public void addCustomerToWaitingList() {
        assignArrivalTimes();

        for (Resident resident : residents) {
            resident.setVisitedRestaurant();
            Map<String, Double> menu = MENU.getMenuByRestaurant(resident.getVisitedRestaurant());

            if (!menu.isEmpty()) {
                String order = getRandomOrder(menu);
                Double price = menu.get(order);

                resident.setOrder(order);
                resident.setPriceOfFood(Double.toString(price));

                resident.setOrderToHistory(currentDay, order, resident.getPriceOfFood(),
                        resident.getVisitedRestaurant());
            }

            String[] residentInfo = new String[16];

            residentInfo[0] = resident.getName();
            residentInfo[1] = resident.getAge();
            residentInfo[2] = resident.getGender();
            residentInfo[3] = resident.getStandInfo();
            residentInfo[4] = resident.getDestructivePower();
            residentInfo[5] = resident.getSpeed();
            residentInfo[6] = resident.getRange();
            residentInfo[7] = resident.getStamina();
            residentInfo[8] = resident.getPrecision();
            residentInfo[9] = resident.getDevelopmentPotential();
            List<String> parentNames = resident.getParents();

            if (parentNames.size() == 1) {
                residentInfo[10] = parentNames.get(0); // Assign the single parent name
            } else if (parentNames.size() > 1) {
                residentInfo[10] = String.join(", ", parentNames); // Join multiple parent names
            }
            residentInfo[11] = resident.getVisitedRestaurant();
            residentInfo[12] = resident.getOrder();
            residentInfo[13] = resident.getPriceOfFood();
            residentInfo[14] = resident.getArrivalTime();
            StringBuilder orderHistoryStringBuilder = new StringBuilder();
            List<String> orderHistory = resident.getOrderHistory();

            String day = orderHistory.get(0);
            String food = orderHistory.get(1);
            String restaurant = orderHistory.get(2);
            String price = orderHistory.get(3);

            orderHistoryStringBuilder.append(day)
                    .append(",")
                    .append(food)
                    .append(",")
                    .append(price)
                    .append(",")
                    .append(restaurant)
                    .append(";");

            residentInfo[15] = orderHistoryStringBuilder.toString();

            ResidentFullList.add(residentInfo); // =waiting list
        }
        sortWaitingListByArrivalTime();
    }

    public List<String[]> getResidentFullList() {
        return ResidentFullList;
    }

    private void assignArrivalTimes() {
        int baseTime = rand.nextInt(24);
        for (Resident resident : residents) {

            // Generate a random arrival time between 1 and 60 minutes
            int minutes = rand.nextInt(60);
            String arrivalTime = String.format("%02d:%02d", baseTime, minutes);
            resident.setArrivalTime(arrivalTime);
        }
    }

    private String getRandomOrder(Map<String, Double> menu) {
        List<String> orders = new ArrayList<>(menu.keySet());
        int randomIndex = rand.nextInt(orders.size());
        return orders.get(randomIndex);
    }

    public void sortWaitingListByArrivalTime() {
        Collections.sort(ResidentFullList, Comparator.comparing(c -> c[14]));
    }

    // will be used to compare the data to avoid crash
    public void WaitingList() {
        FilteredWaitingList = TheJoestars.getWaitingList();

        StringBuilder waitingListContent = new StringBuilder();
        waitingListContent.append("Day ").append(currentDay).append("\n");
        waitingListContent.append("Waiting List\n");
        waitingListContent.append(
                "+----+------------------------+-----+--------+---------------+------------------------------------+-------------------------+----------+\n");
        waitingListContent.append(
                "| No |         Name           | Age | Gender | Arrival Time  |           Order                    |   Visited Restaurant    | Price $  |\n");
        waitingListContent.append(
                "+----+------------------------+-----+--------+---------------+------------------------------------+-------------------------+----------+\n");

        int count = 1;
        for (String[] residentInfo : FilteredWaitingList) {
            String visitedRestaurant = residentInfo[11];
            String number = String.valueOf(count);
            String name = residentInfo[0];
            String age = residentInfo[1];
            String gender = residentInfo[2];
            String arrivalTime = residentInfo[14];
            String order = residentInfo[12];
            String price = residentInfo[13];

            waitingListContent.append(String.format("| %-3s| %-23s| %-4s| %-7s| %-14s| %-35s| %-24s| $%-7s |\n",
                    number, name, age, gender, arrivalTime, order, visitedRestaurant, price));

            count++;

            ResidentProfile(residentInfo[0]);
            appendOrderHistoryToFile(residentInfo[15], residentInfo[0]);
        }
        waitingListContent.append(
                "+----+-------------------------+-----+--------+---------------+-------------------------------------+-------------------------+----------+\n");
        String content = waitingListContent.toString();
        writeWaitingListToFile(content, "FullWaitingList.txt");

        // append each restaurant waiting list into file
        for (int i = 0; i < restaurantLocation.length; i++) {
            WaitingList(restaurantLocation[i]);
        }
    }

    // for specific restaurant waiting list of each day
    public void WaitingList(String Restaurant) {
        FilteredWaitingList = TheJoestars.getWaitingList();
        StringBuilder waitingList = new StringBuilder();
        waitingList.append("Day ").append(currentDay).append("\n");
        waitingList.append("Waiting List ").append(Restaurant).append(" Day ").append(currentDay).append("\n");
        waitingList.append(
                "+----+------------------------+-----+--------+---------------+------------------------------------+-------------------------+---------+\n");
        waitingList.append(
                "| No |          Name          | Age | Gender | Arrival Time  |   Order                            |   Visited Restaurant    | Price $ |\n");
        waitingList.append(
                "+----+------------------------+-----+--------+---------------+------------------------------------+-------------------------+---------+\n");

        int count = 1;
        for (String[] residentInfo : FilteredWaitingList) {
            if (residentInfo[11].equals(Restaurant)) {
                String visitedRestaurant = residentInfo[11];
                String number = String.valueOf(count);
                String name = residentInfo[0];
                String age = residentInfo[1];
                String gender = residentInfo[2];
                String arrivalTime = residentInfo[14];
                String order = residentInfo[12];
                String price = residentInfo[13];

                waitingList.append(String.format("| %-3s| %-23s| %-4s| %-7s| %-14s| %-35s| %-24s| $%-6s |\n",
                        number, name, age, gender, arrivalTime, order, visitedRestaurant, price));

                count++;

                // Store the resident info in waitingListOut
                String[] residentData = { number, name, age, gender, arrivalTime, order, visitedRestaurant };
                if (visitedRestaurant.equals("Jade Garden")) {
                    JadeGardenWaitingList.add(residentData);
                } else if (visitedRestaurant.equals("Cafe Deux Magots")) {
                    CafeDeuxMagotsWaitingList.add(residentData);
                } else if (visitedRestaurant.equals("Trattoria Trussardi")) {
                    TrattoriaTrussardiWaitingList.add(residentData);
                } else if (visitedRestaurant.equals("Libeccio")) {
                    LibeccioWaitingList.add(residentData);
                } else if (visitedRestaurant.equals("Savage Garden")) {
                    SavageGardenWaitingList.add(residentData);
                }

            }
        }
        waitingList.append(
                "+----+------------------------+-----+--------+---------------+-------------------------------------+-------------------------+-------+\n");
        String content = waitingList.toString();
        String filename = "waiting_list_" + Restaurant + "_" + currentDay + ".txt";
        writeWaitingListToFile(content, filename);
    }

    public static List<String[]> getWaitingListPearlJam(String currentLocation) {
        if (currentLocation.equals("Jade Garden")) {
            return JadeGardenWaitingList;
        } else if (currentLocation.equals("Savage Garden")) {
            return SavageGardenWaitingList;
        } else if (currentLocation.equals("Libeccio")) {
            return LibeccioWaitingList;
        } else if (currentLocation.equals("Trattoria Trussardi")) {
            return TrattoriaTrussardiWaitingList;
        } else if (currentLocation.equals("Cafe Deux Magots")) {
            return CafeDeuxMagotsWaitingList;
        }
        return null;
    }

    // to save the profile of the person
    public void ResidentProfile(String ResidentName) {
        FilteredWaitingList = TheJoestars.getWaitingList();

        for (String[] residentInfo : FilteredWaitingList) {
            String name = residentInfo[0];
            String nameSaved = String.join("", name.split(" ")).toLowerCase();
            String age = residentInfo[1];
            String gender = residentInfo[2];
            List<String> parents = new ArrayList<>();
            if (residentInfo[10] != null) {
                parents = Arrays.asList(residentInfo[10].split(","));
            }
            String standInfo = residentInfo[3];
            String destructivePower = residentInfo[4];
            String speed = residentInfo[5];
            String range = residentInfo[6];
            String stamina = residentInfo[7];
            String precision = residentInfo[8];
            String developmentPotential = residentInfo[9];
            String orderHistoryString = residentInfo[15]; // Extract the order history string

            StringBuilder profile = new StringBuilder();

            if (currentDay == 1) {
                profile.append("\nProfiles:\n");
                int labelWidth = 25; // Adjust the width as needed

                profile.append("\n").append(name).append("'s Profile\n");
                profile.append(String.format("%-" + labelWidth + "s: %s\n", "Name", name));
                profile.append(String.format("%-" + labelWidth + "s: %s\n", "Age", age));
                profile.append(String.format("%-" + labelWidth + "s: %s\n", "Gender", gender));
                profile.append(String.format("%-" + labelWidth + "s: %s\n", "Parents", String.join(", ", parents)));
                profile.append(String.format("%-" + labelWidth + "s: %s\n", "Stand", standInfo));
                profile.append(String.format("%-" + labelWidth + "s: %s\n", "Destructive Power", destructivePower));
                profile.append(String.format("%-" + labelWidth + "s: %s\n", "Speed", speed));
                profile.append(String.format("%-" + labelWidth + "s: %s\n", "Range", range));
                profile.append(String.format("%-" + labelWidth + "s: %s\n", "Stamina", stamina));
                profile.append(String.format("%-" + labelWidth + "s: %s\n", "Precision", precision));
                profile.append(
                        String.format("%-" + labelWidth + "s: %s\n", "Development Potential", developmentPotential));

                // Order History
                profile.append("Order History\n");
                profile.append(
                        "+-----+---------------------------------------+------------+------------------------+\n");
                profile.append(
                        "| Day |               Order                   |   Price    |        Restaurant      |\n");
                profile.append(
                        "+-----+---------------------------------------+------------+------------------------+\n");
                String[] orders = orderHistoryString.split(";");

                for (String order : orders) {
                    String[] orderInfo = order.split(",");
                    String day = orderInfo[0];
                    String food = orderInfo[1];
                    String price = orderInfo[3];
                    String restaurant = orderInfo[2];

                    profile.append("| ")
                            .append(String.format("%-3s", day))
                            .append(" | ")
                            .append(String.format("%-37s", food))
                            .append(" | ")
                            .append(String.format("%-10s", price))
                            .append(" | ")
                            .append(String.format("%-22s", restaurant))
                            .append(" |\n");
                    profile.append(
                            "+-----+---------------------------------------+------------+------------------------+\n");
                }
            }

            if (name.equals(ResidentName)) {
                // Split the order history string into individual orders
                String[] orders = orderHistoryString.split(";");

                for (String order : orders) {
                    String[] orderInfo = order.split(",");
                    String day = orderInfo[0];
                    String food = orderInfo[1];
                    String price = orderInfo[3];
                    String restaurant = orderInfo[2];

                    profile.append("| ")
                            .append(String.format("%-3s", day))
                            .append(" | ")
                            .append(String.format("%-37s", food))
                            .append(" | ")
                            .append(String.format("%-10s", price))
                            .append(" | ")
                            .append(String.format("%-22s", restaurant))
                            .append(" |\n");
                    profile.append(
                            "+-----+---------------------------------------+------------+------------------------+\n");
                }

            }

            String content = profile.toString();
            String filename = "profile_" + nameSaved + ".txt";
            if (currentDay == 1) {
                writeProfileToFile(content, filename, false); // Overwrite the file
            } else {
                writeProfileToFile(content, filename, true); // Append to the file
            }
        }
    }

    public void appendOrderHistoryToFile(String orderHistory, String residentname) {
        String fileName = residentname.replaceAll(" ", "").toLowerCase() + "_order_history.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(orderHistory);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeWaitingListToFile(String content, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(content);
            writer.newLine(); // Add a new line after appending the content
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeProfileToFile(String content, String fileName, boolean append) {
        try {
            FileWriter writer = new FileWriter(fileName, append);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
