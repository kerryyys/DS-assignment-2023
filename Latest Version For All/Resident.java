package JOJOLands.JOJO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

public class Resident {
    
    private Random rand = new Random();
    private List<String[]> residents;
    private List<String[]> stands;
    private String name;
    private String age;
    private String gender;
    private String residentialArea;
    private List<String> parents;
    private String standInfo;
    private String destructivePower;
    private String speed;
    private String range;
    private String stamina;
    private String precision;
    private String developmentPotential;
    private String visitedRestaurant;
    private String order;
    private String priceOfFood;
    private String arrivalTime;
    private List<Resident> combinedResidents;
    private List<String> orderHistory;

    public Resident(){
        this.residents = new ArrayList<>();
        this.stands = new ArrayList<>();
    }
    
    public Resident(String name, String age, String gender, String residentialArea, List<String> parents, String standInfo, String destructivePower, String speed, String range, String stamina, String precision, String developmentPotential, String visitedRestaurant, String order, String priceOfFood, String arrivalTime) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.residentialArea = residentialArea;
        this.parents = parents;
        this.standInfo = standInfo;
        this.destructivePower = destructivePower;
        this.speed = speed;
        this.range = range;
        this.stamina = stamina;
        this.precision = precision;
        this.developmentPotential = developmentPotential;
        this.visitedRestaurant = visitedRestaurant;
        this.order = order;
        this.priceOfFood = priceOfFood;
        this.arrivalTime = arrivalTime;
    }

    public void readResidentsCSV(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                residents.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readStandsCSV(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header line
                }
                String[] data = line.split(",");
                if (data.length > 1) {
                    String[] standDetails = new String[data.length];
                    for (int i = 0; i < data.length; i++) {
                        standDetails[i] = data[i].trim();
                    }
                    stands.add(standDetails);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void combineResidentsAndStands() {
        List<Resident> combinedResidents = new ArrayList<>();
    
        for (int i = 0; i < residents.size(); i++) {
            String[] resident = residents.get(i);
            String name = resident[0];
            String age = resident[1];
            String gender = resident[2];
            String residentialArea = resident[3];
            List<String> parents = new ArrayList<>();
            parents.add(resident[4]);
            if (resident.length > 5) {
            parents.add(resident[5]);
            }
    
            String[] stand = findStandByResidentName(name);
            String[] standDetails = findStandDetailsByResidentName(name);
    
            String standInfo = null;
            String destructivePower = null;
            String speed = null;
            String range = null;
            String stamina = null;
            String precision = null;
            String developmentPotential = null;
    
            if (stand != null) {
                standInfo = stand[0];  // Retrieve stand name from the stands list
            }
    
            if (standDetails != null) {
                destructivePower = standDetails[0];
                speed = standDetails[1];
                range = standDetails[2];
                stamina = standDetails[3];
                precision = standDetails[4];
                developmentPotential = standDetails[5];
            }
            
            Resident residentList = new Resident(name, age, gender, residentialArea, parents, standInfo, destructivePower, speed, range, stamina, precision, developmentPotential, "", "", "", "");
           
            combinedResidents.add(residentList);
        }
                // Store the combinedResidents list in an instance variable
                this.combinedResidents = combinedResidents;
    }

    private String[] findStandByResidentName(String name) {
        for (String[] stand : stands) {
            if (stand.length > 1 && stand[1].trim().equalsIgnoreCase(name)) {
                return stand;
            }
        }
        return null;
    }

    //to check the name is stand holder or not
    private String[] findStandDetailsByResidentName(String name) {
        for (String[] standDetails : stands) {
            if (standDetails.length > 1 && standDetails[1].trim().equalsIgnoreCase(name)) {
                return Arrays.copyOfRange(standDetails, 2, standDetails.length);
            }
        }
        return null;
    }
    
    public List<Resident> getCombinedResidents() {
        return combinedResidents;
    }

    public String getName() {
        return name;
    }
    
    public String getAge() {
        return age;
    }
    
    public String getGender() {
        return gender;
    }
    
    public String getResidentialArea() {
        return residentialArea;
    }
    
    public List<String> getParents() {
        return parents;
    }
    
    public String getStandInfo() {
        return standInfo;
    }
    
    public String getDestructivePower() {
        return destructivePower;
    }
    
    public String getSpeed() {
        return speed;
    }
    
    public String getRange() {
        return range;
    }
    
    public String getStamina() {
        return stamina;
    }
    
    public String getPrecision() {
        return precision;
    }
    
    public String getDevelopmentPotential() {
        return developmentPotential;
    }
    
    public String getVisitedRestaurant() {
        return visitedRestaurant;
    }
    
    public String getOrder() {
        return order;
    }
    
    public String getPriceOfFood() {
        return priceOfFood;
    }
    
    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setVisitedRestaurant(){
        String[] locations = { "Jade Garden", "Cafe Deux Magots", "Trattoria Trussardi", "Libeccio", "Savage Garden" };
        this.visitedRestaurant = locations[rand.nextInt(locations.length)];
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setPriceOfFood(String priceOfFood) {
        this.priceOfFood = priceOfFood;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setOrderToHistory(int currentDay, String order, String price, String restaurant) {
        this.order = order;
        this.visitedRestaurant = restaurant;
        this.priceOfFood = price;

        this.orderHistory = new ArrayList<>();
        this.orderHistory.add(Integer.toString(currentDay));
        this.orderHistory.add(order);
        this.orderHistory.add(price);
        this.orderHistory.add(restaurant);

        //currentDay get in WaitingListGenerator class
    }

    public List<String> getOrderHistory() {
        return orderHistory;
    }
}
