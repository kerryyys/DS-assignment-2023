package JOJOLands.JOJO;

import java.io.*;
import java.util.*;

public class TheJoestarsChecker {
    private String currentLocation;
    private List<String[]> residentProfiles;
    private List<String[]> residentInformation;
    private List<String[]> PersonalOrder;

    public TheJoestarsChecker(String currentLocation) {
        this.currentLocation = currentLocation;
        this.residentInformation =new ArrayList<>();
        this.residentProfiles = new ArrayList<>();
        this.PersonalOrder = new ArrayList<>();
    }

    public void readResidentInformation(String filename){
        filename = "resident_information_" + currentLocation + ".txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line;
            boolean headerSkipped = false;

            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                if (line.startsWith("+----")) {
                    continue;
                }

                String[] residentInfo = line.split("\\s*\\|\\s*");
                residentInformation.add(residentInfo);
                
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readResidentProfiles(String nameSaved) {
            String filename = "profile_" + nameSaved + ".txt";

            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                boolean headerSkipped = false;

                while ((line = br.readLine()) != null) {
                    if (!headerSkipped) {
                        headerSkipped = true;
                        continue;
                    }
        
                    if (line.startsWith("+----")) {
                        continue;
                    }
        
                    String[] residentDetails = line.split("\\|");
                    residentProfiles.add(residentDetails);

                    // Store personal order information in PersonalOrder array
                    //used to apply filter in TheJoestars class
                    String orderHistory = residentDetails[15].trim();
                String[] orders = orderHistory.split(";");
                for (String order : orders) {
                    String[] orderInfo = order.split(",");
                    PersonalOrder.add(orderInfo);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> readOrderHistory(String name){
        String filename = name + "_order_history" + ".txt";

        List<String[]> matchingLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
    
            while ((line = br.readLine()) != null) {
                if (line.matches("\\d+,.*?,\\d+\\.\\d+,.*?;")) {
                    String[] orderDetails = line.split(",");
                    matchingLines.add(orderDetails);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return matchingLines;
    }
    
    // to display the list of resident information in the current location
    public void displayResidentInformation() {
        System.out.println("Resident Information in " + currentLocation);
        System.out.println(
                "+----+-------------------------+-------+--------+--------------------------------------+-------------------+");
        System.out.println(
                "| No |          Name           |  Age  | Gender |             Parents                  |       Stand       |");
        System.out.println(
                "+----+-------------------------+-------+--------+--------------------------------------+-------------------+");

        int count = 1;
        for (String[] residentInfo : residentInformation) {
            String name = residentInfo[1].trim();
            String age = residentInfo[2].trim();
            String gender = residentInfo[3].trim();
            List<String> parents = Arrays.asList(residentInfo[11].split(","));
            String standInfo = residentInfo[4].trim();

            System.out.printf("| %-3s | %-23s | %-5s | %-6s | %-36s | %-19s |%n",
                    count, name, age, gender, String.join(", ", parents), standInfo);

            count++;
        }
        System.out.println(
                "+----+-------------------------+-------+--------+--------------------------------------+-------------------+");
    }

    //to display the Specific resident profile in details in The Joestars
    public void displayResidentProfiles(String ResidentName) {
        for (String[] residentInfo : residentProfiles) {
            String name = residentInfo[1].trim();
            String age = residentInfo[2].trim();
            String gender = residentInfo[3].trim();
            List<String> parents = Arrays.asList(residentInfo[11].split(","));
            String standInfo = residentInfo[4].trim();
            String destructivePower = residentInfo[5].trim();
            String speed = residentInfo[6].trim();
            String range = residentInfo[7].trim();
            String stamina = residentInfo[8].trim();
            String precision = residentInfo[9].trim();
            String developmentPotential = residentInfo[10].trim();
            String orderHistory = residentInfo[16].trim();

            System.out.println();
            System.out.println(name + "'s Profile");
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("Gender: " + gender);
            System.out.println("Parents: " + String.join(", ", parents));
            System.out.println("Stand: " + standInfo);
            System.out.println("Destructive Power: " + destructivePower);
            System.out.println("Speed: " + speed);
            System.out.println("Range: " + range);
            System.out.println("Stamina: " + stamina);
            System.out.println("Precision: " + precision);
            System.out.println("Development Potential: " + developmentPotential);

            // Order History
            System.out.println("Order History");
            System.out.println("+-----+------------------------------------+---------------------+");
            System.out.println("| Day |              Order                 |      Restaurant     |");
            System.out.println("+-----+------------------------------------+---------------------+");

            String[] orders = orderHistory.split(";");
            for (String order : orders) {
                String[] orderInfo = order.split(",");
                String day = orderInfo[0].trim();
                String food = orderInfo[1].trim();
                String restaurant = orderInfo[2].trim();
                String price = orderInfo[3].trim(); //dont need print out
                System.out.printf("| %-3s | %-35s | %-20s |%n", day, food, restaurant);

            }

            System.out.println("+-----+------------------------------------+---------------------+");
        }
    }
    public List<String[]> getPersonalOrder(){
        return PersonalOrder;
    }
}
