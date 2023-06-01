package JOJOLands;

import java.io.*;
import java.util.*;

//Once this class is call, it will work to assign list for all resident, just call the method to generate the waiting list
public class WaitingListGenerator {
    private List<String[]> ResidentFullList;
    private LinkedList<String[]> waitingListPearlJam;
    private LinkedList<String[]> waitingListJoestar;
    private List<Resident> residents;
    private Menu MENU;
    private Random rand;
    private String currentLocation;
    private int currentDay;

    public WaitingListGenerator(String currentLocation, int currentDay) {
        this.ResidentFullList = new ArrayList<>();
        this.waitingListPearlJam = new LinkedList<>();
        this.waitingListJoestar = new LinkedList<>();
        this.currentLocation = currentLocation;
        this.currentDay = currentDay;
        this.MENU = new Menu();
        this.rand = new Random();

        // Instantiate the Resident class and read the CSV file
        Resident resident = new Resident();
        resident.readResidentsCSV("residents.csv");
        resident.readStandsCSV("stands.csv");
        resident.combineResidentsAndStands();

        // Access resident information using getter methods
        residents = resident.getCombinedResidents();
    }

    //need to call in HermitPurple whenever start a new day, cannot call at other time
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

                resident.setOrderToHistory(currentDay, order, resident.getVisitedRestaurant(),
                        resident.getPriceOfFood());
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
            if (resident.getParents().size() == 1) {
                residentInfo[10] = resident.getParents().get(0);
            } else if (resident.getParents().size() > 1) {
                residentInfo[10] = String.join(", ", resident.getParents());
            }
            residentInfo[11] = resident.getVisitedRestaurant();
            residentInfo[12] = resident.getOrder();
            residentInfo[13] = resident.getPriceOfFood();
            residentInfo[14] = resident.getArrivalTime();
            StringBuilder orderHistoryStringBuilder = new StringBuilder();
            List<String[]> orderHistory = resident.getOrderHistory();

            for (String[] orderInfo : orderHistory) {
                String day = orderInfo[0];
                String food = orderInfo[1];
                String restaurant = orderInfo[2];
                String price = orderInfo[3];

                orderHistoryStringBuilder.append(day)
                        .append(",")
                        .append(food)
                        .append(",")
                        .append(price)
                        .append(",")
                        .append(restaurant)
                        .append(";");
            }

            residentInfo[15] = orderHistoryStringBuilder.toString();

            ResidentFullList.add(residentInfo); //=waiting list
        }
        sortWaitingListByArrivalTime();
    }
    public List<String[]> getResidentFullList(){
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
        StringBuilder waitingListContent = new StringBuilder();
        waitingListContent.append("Day ").append(currentDay).append("\n");
        waitingListContent.append("Waiting List\n");
        waitingListContent.append(
                "+----+-----------------------+-----+--------+---------------+-------------------------------------+-------------------------+---------+\\n");
        waitingListContent.append(
                "| No |         Name          | Age | Gender | Arrival Time  | Order                               |   Visited Restaurant    | Price $ |\n");
        waitingListContent.append(
                "+----+-----------------------+-----+--------+---------------+-------------------------------------+-------------------------+---------+\\n");

        int count = 1;
        for (String[] residentInfo : ResidentFullList) {
            String visitedRestaurant = residentInfo[11];
            String number = String.valueOf(count);
            String name = residentInfo[0];
            String age = residentInfo[1];
            String gender = residentInfo[2];
            String arrivalTime = residentInfo[14];
            String order = residentInfo[12];
            String price = residentInfo[13];

            waitingListContent.append(String.format("| %-3s| %-23s| %-4s| %-7s| %-14s| %-35s| %-20s| $%-7.2f |\n",
                    number, name, age, gender, arrivalTime, order, visitedRestaurant, price));

            count++;

            // Store the resident info in waitingList that will be used to filter
            String[] residentData = { number, name, age, gender, arrivalTime, order, visitedRestaurant, price };
            waitingListJoestar.add(residentData);

        }
        waitingListContent.append(
                "+----+-----------------------+-----+--------+---------------+-------------------------------------+---------------------+\n");
        String content = waitingListContent.toString();
        writeWaitingListToFile(content, "FullWaitingList.txt");
    }

    // for specific restaurant waiting list of each day
    public void WaitingList(String currentLocation) {
        StringBuilder waitingList = new StringBuilder();
        waitingList.append("Day ").append(currentDay).append("\n");
        waitingList.append("Waiting List ").append(currentLocation).append(currentDay).append("\n");
        waitingList.append(
                "+----+-----------------------+-----+--------+---------------+-------------------------------------+-------------------------+---------+\n");
        waitingList.append(
                "| No |         Name          | Age | Gender | Arrival Time  | Order                               |   Visited Restaurant    | Price $ |\n");
        waitingList.append(
                "+----+-----------------------+-----+--------+---------------+-------------------------------------+-------------------------+---------+\n");

        int count = 1;
        for (String[] residentInfo : ResidentFullList) {
            String visitedRestaurant = residentInfo[11];
            if (currentLocation.equals(visitedRestaurant)) {
                String number = String.valueOf(count);
                String name = residentInfo[0];
                String age = residentInfo[1];
                String gender = residentInfo[2];
                String arrivalTime = residentInfo[14];
                String order = residentInfo[12];
                String price = residentInfo[13];

                waitingList.append(String.format("| %-3s| %-23s| %-4s| %-7s| %-14s| %-35s| %-20s| $%-7.2f |\n",
                        number, name, age, gender, arrivalTime, order, visitedRestaurant, price));

                count++;

                // Store the resident info in waitingListOut
                String[] residentData = { number, name, age, gender, arrivalTime, order, visitedRestaurant };
                waitingListPearlJam.add(residentData);
            }
        }
        waitingList.append(
                "+----+-----------------------+-----+--------+---------------+-------------------------------------+---------------------+-------+\n");
        String content = waitingList.toString();
        writeWaitingListToFile(content, "waiting_list_" + currentLocation + ".txt");
    }

    public List<String[]> getWaitingList() {
        return this.waitingListPearlJam;
    }

    // to display and store the information of the resident
    public void ResidentInformation() {
        StringBuilder residentInformation = new StringBuilder();

        residentInformation.append("Resident Information in ").append(currentLocation).append("\n");
        residentInformation.append(
                "+----+-------------------------+-------+--------+------------------------------+-------------------+\n");
        residentInformation.append(
                "| No |          Name           |  Age  | Gender |         Parents              |       Stand       | \n");
        residentInformation.append(
                "+----+-------------------------+-------+--------+------------------------------+-------------------+ \n");

        int count = 1;
        for (String[] residentInfo : ResidentFullList) {
            String name = residentInfo[0];
            String age = residentInfo[1];
            String gender = residentInfo[2];
            List<String> parents = Arrays.asList(residentInfo[10].split(","));
            String standInfo = residentInfo[3];

            residentInformation.append("| ")
                    .append(String.format("%-3s", count))
                    .append(" | ")
                    .append(String.format("%-23s", name))
                    .append(" | ")
                    .append(String.format("%-4s", age))
                    .append(" | ")
                    .append(String.format("%-6s", gender))
                    .append(" | ")
                    .append(String.format("%-28s", String.join(", ", parents)))
                    .append(" | ")
                    .append(String.format("%-19s", standInfo))
                    .append(" |\n");

            count++;
        }
        residentInformation
                .append("+----+-------------------------+-------+--------+------------------------------+-------------------+\n");

        String content = residentInformation.toString();
        writeWaitingListToFile(content, "resident_information_" + currentLocation + ".txt");
    }

    // to save the profile of the person
    public void ResidentProfile(String ResidentName) {
        String nameSaved = "";
        StringBuilder profile = new StringBuilder();
        profile.append("\nProfiles:\n");

        for (String[] residentInfo : ResidentFullList) {
            String name = residentInfo[0];
            nameSaved = String.join("", name.split(" ")).toLowerCase();
            String age = residentInfo[1];
            String gender = residentInfo[2];
            List<String> parents = Arrays.asList(residentInfo[10].split(","));
            String standInfo = residentInfo[3];
            String destructivePower = residentInfo[4];
            String speed = residentInfo[5];
            String range = residentInfo[6];
            String stamina = residentInfo[7];
            String precision = residentInfo[8];
            String developmentPotential = residentInfo[9];
            String orderHistoryString = residentInfo[15]; // Extract the order history string

            profile.append("\n").append(name).append("'s Profile\n");
            profile.append("Name: ").append(name).append("\n");
            profile.append("Age: ").append(age).append("\n");
            profile.append("Gender: ").append(gender).append("\n");
            profile.append("Parents: ").append(String.join(", ", parents)).append("\n");
            profile.append("Stand: ").append(standInfo).append("\n");
            profile.append("Destructive Power: ").append(destructivePower).append("\n");
            profile.append("Speed: ").append(speed).append("\n");
            profile.append("Range: ").append(range).append("\n");
            profile.append("Stamina: ").append(stamina).append("\n");
            profile.append("Precision: ").append(precision).append("\n");
            profile.append("Development Potential: ").append(developmentPotential).append("\n");

            // Order History
            profile.append("Order History\n");
            profile.append("+-----+------------------------------------+------------+---------------------+\n");
            profile.append("| Day |              Order                 |   Price    |      Restaurant     |\n");
            profile.append("+-----+------------------------------------+------------+---------------------+\n");

            // Split the order history string into individual orders
            String[] orders = orderHistoryString.split(";");

            for (String order : orders) {
                String[] orderInfo = order.split(",");
                String day = orderInfo[0];
                String food = orderInfo[1];
                String price = orderInfo[2];
                String restaurant = orderInfo[3];

                profile.append("| ")
                        .append(String.format("%-3s", day))
                        .append(" | ")
                        .append(String.format("%-35s", food))
                        .append(" | ")
                        .append(String.format("%-10s", price))
                        .append(" | ")
                        .append(String.format("%-20s", restaurant))
                        .append(" |\n");
            }

            profile.append("+-----+------------------------------------+------------+---------------------+\n");
        }

        String content = profile.toString();
        writeProfileToFile(content, "profile_" + nameSaved + ".txt");
    }

    private void writeWaitingListToFile(String content, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(content);
            writer.newLine(); // Add a new line after appending the content
        } catch (IOException e) {
            e.printStackTrace();
        }
//   JS:   try {
//                 BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
//                 writer.write(content);
//                 writer.newLine();
//                 writer.close();
//             } catch (IOException e) {
//                 e.printStackTrace();
//             }
        // can write to the file; not sure yet
    }

    public void writeProfileToFile(String content, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName, false); // Open file in overwrite mode
            writer.write(content);
            writer.close();
            System.out.println("Content saved to file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

}
