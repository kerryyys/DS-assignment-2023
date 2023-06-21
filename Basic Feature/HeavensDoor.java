package JOJOLands.JOJO;

import java.io.*;
import java.util.*;

//view resident information
public class HeavensDoor {
    private List<String[]> residents;
    private List<String[]> stands;
    private String currentLocation;
    private ArrayList<String> residentInCurrentArea;
    private TheJoestarsChecker JoestarsChecker;
    private List<String[]> combinedResidents;

    public HeavensDoor(String currentLocation) {
        this.currentLocation = currentLocation;
        this.residentInCurrentArea = new ArrayList<>();
        residents = new ArrayList<>();
        stands = new ArrayList<>();
        readResidentsCSV("residentS.csv");
        readStandsCSV("stands.csv");
        this.combinedResidents = combineResidentsAndStands();
    }

    private void readResidentsCSV(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                residents.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readStandsCSV(String filename) {
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

    private List<String[]> combineResidentsAndStands() {
        List<String[]> combinedResidents = new ArrayList<>();

        for (int i = 0; i < residents.size(); i++) {
            String[] resident = residents.get(i);
            String name = resident[0];
            String age = resident[1];
            String gender = resident[2];
            String residentialArea = resident[3];
            String parents = resident[4];

            if (residentialArea.equalsIgnoreCase(currentLocation)) {
                residentInCurrentArea.add(resident[0].replaceAll(" ", "").toLowerCase()); // used in searching file name
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
                    standInfo = stand[0]; // Retrieve stand name from the stands list
                }

                if (standDetails != null) {
                    destructivePower = standDetails[0];
                    speed = standDetails[1];
                    range = standDetails[2];
                    stamina = standDetails[3];
                    precision = standDetails[4];
                    developmentPotential = standDetails[5];
                }

                String[] combinedInfo = new String[12];
                combinedInfo[0] = name;
                combinedInfo[1] = parseAge(age);
                combinedInfo[2] = gender;
                combinedInfo[3] = standInfo;
                combinedInfo[4] = destructivePower;
                combinedInfo[5] = speed;
                combinedInfo[6] = range;
                combinedInfo[7] = stamina;
                combinedInfo[8] = precision;
                combinedInfo[9] = developmentPotential;
                combinedInfo[10] = parents;

                combinedResidents.add(combinedInfo);
            }
        }

        return combinedResidents;
    }

    public void printResidents() {
        int no = 1; // To show NO.

        System.out.println("Resident Information in " + currentLocation);
        System.out.println(
                "+----+-----------------------+-----+--------+-----------------------+-------------------+-----------+-----------+-------------+---------------+-----------------------+");
        System.out.println(
                "| No | Name                  | Age | Gender | Stand                 | Destructive Power |   Speed   |   Range   |   Stamina   |   Precision   | Development Potential |");
        System.out.println(
                "+----+-----------------------+-----+--------+-----------------------+-------------------+-----------+-----------+-------------+---------------+-----------------------+");

        for (int i = 0; i < combinedResidents.size(); i++) {
            String[] resident = combinedResidents.get(i);
            System.out.printf("| %-2d | %-21s | %-3s | %-6s | %-21s | %-17s | %-9s | %-9s | %-11s | %-13s | %-21s |\n",
                    no, resident[0], resident[1], resident[2], resident[3], resident[4], resident[5], resident[6],
                    resident[7], resident[8], resident[9]);
            no++;
        }

        System.out.println(
                "+----+-----------------------+-----+--------+-----------------------+-------------------+-----------+-----------+-------------+---------------+-----------------------+");
    }

    // to change the null into N/A
    private String parseAge(String age) {
        try {
            return Integer.parseInt(age) + "";
        } catch (NumberFormatException e) {
            return "N/A";
        }
    }

    private String[] findStandByResidentName(String name) {
        for (String[] stand : stands) {
            if (stand.length > 1 && stand[1].trim().equalsIgnoreCase(name)) {
                return stand;
            }
        }
        return null;
    }

    // to check the name is stand holder or not
    private String[] findStandDetailsByResidentName(String name) {
        for (String[] standDetails : stands) {
            if (standDetails.length > 1 && standDetails[1].trim().equalsIgnoreCase(name)) {
                return Arrays.copyOfRange(standDetails, 2, standDetails.length);
            }
        }
        return null;
    }

    private void sortResidents(String sortingOrder) {

        String[] sortingCriteria = sortingOrder.split(";");

        for (int i = sortingCriteria.length - 1; i >= 0; i--) {
            String criteria = sortingCriteria[i];
            String[] parts = criteria.trim().split("\\(");
            String field = parts[0].trim();
            String sortOrder = parts[1].replaceAll("[()]", "").trim();

            switch (field.toLowerCase()) {
                case "name":
                    bubbleSort(combinedResidents, 0, sortOrder);
                    break;
                case "age":
                    bubbleSort(combinedResidents, 1, sortOrder);
                    break;
                case "gender":
                    bubbleSort(combinedResidents, 2, sortOrder);
                    break;
                case "stand":
                    bubbleSort(combinedResidents, 3, sortOrder);
                    break;
                case "destructive power":
                    bubbleSort(combinedResidents, 4, sortOrder);
                    break;
                case "speed":
                    bubbleSort(combinedResidents, 5, sortOrder);
                    break;
                case "range":
                    bubbleSort(combinedResidents, 6, sortOrder);
                    break;
                case "stamina":
                    bubbleSort(combinedResidents, 7, sortOrder);
                    break;
                case "precision":
                    bubbleSort(combinedResidents, 8, sortOrder);
                    break;
                case "development potential":
                    bubbleSort(combinedResidents, 9, sortOrder);
                    break;
                default:
                    System.out.println("Invalid sorting criteria: " + criteria);
                    return;
            }
        }

        int no = 1; // To show NO.

        System.out.println("Sorted Resident Information in " + currentLocation);
        System.out.println(
                "+----+-----------------------+-----+--------+-----------------------+-------------------+-----------+-----------+-------------+---------------+-----------------------+");
        System.out.println(
                "| No | Name                  | Age | Gender | Stand                 | Destructive Power |   Speed   |   Range   |   Stamina   |   Precision   | Development Potential |");
        System.out.println(
                "+----+-----------------------+-----+--------+-----------------------+-------------------+-----------+-----------+-------------+---------------+-----------------------+");

        for (int i = 0; i < combinedResidents.size(); i++) {
            String[] resident = combinedResidents.get(i);
            System.out.printf("| %-2d | %-21s | %-3s | %-6s | %-21s | %-17s | %-9s | %-9s | %-11s | %-13s | %-21s |\n",
                    no, resident[0], resident[1], resident[2], resident[3], resident[4], resident[5], resident[6],
                    resident[7], resident[8], resident[9]);
            no++;
        }
        System.out.println(
                "+----+-----------------------+-----+--------+-----------------------+-------------------+-----------+-----------+-------------+---------------+-----------------------+");
    }

    private void bubbleSort(List<String[]> residents, int fieldIndex, String sortOrder) {
        int n = residents.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                String[] resident1 = residents.get(j);
                String[] resident2 = residents.get(j + 1);

                if (resident1 != null && resident2 != null
                        && compareResidents(resident1, resident2, fieldIndex, sortOrder) > 0) {
                    // Swap residents
                    residents.set(j, resident2);
                    residents.set(j + 1, resident1);
                }
            }
        }
        // Move null data to the end of the list
        int nullIndex = -1;
        for (int i = n - 1; i >= 0; i--) {
            String[] resident = residents.get(i);
            if (resident == null) {
                if (nullIndex == -1) {
                    nullIndex = i;
                }
            } else if (nullIndex != -1) {
                residents.set(nullIndex, resident);
                residents.set(i, null);
                nullIndex--;
            }
        }
    }

    private int compareResidents(String[] resident1, String[] resident2, int fieldIndex, String sortOrder) {
        String value1 = resident1[fieldIndex] != null ? resident1[fieldIndex] : "";
        String value2 = resident2[fieldIndex] != null ? resident2[fieldIndex] : "";

        if (value1.isEmpty() && value2.isEmpty()) {
            return 0;
        }

        if (value1.isEmpty()) {
            return sortOrder.equalsIgnoreCase("asc") ? 1 : -1;
        }

        if (value2.isEmpty()) {
            return sortOrder.equalsIgnoreCase("asc") ? -1 : 1;
        }

        if (isNumeric(value1) && isNumeric(value2)) {
            int numericValue1 = Integer.parseInt(value1);
            int numericValue2 = Integer.parseInt(value2);

            if (sortOrder.equalsIgnoreCase("asc")) {
                return Integer.compare(numericValue1, numericValue2);
            } else {
                return Integer.compare(numericValue2, numericValue1);
            }
        } else {
            // Special sorting order for stand details (A, B, C, etc.)
            List<String> sortingOrder = Arrays.asList("Infinity", "A", "B", "C", "D", "E", "?", "Null", "N/A");

            int index1 = sortingOrder.indexOf(value1);
            int index2 = sortingOrder.indexOf(value2);

            if (sortOrder.equalsIgnoreCase("asc")) {
                return Integer.compare(index1, index2);
            } else {
                return Integer.compare(index2, index1);
            }
        }
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public void select() {
        Scanner sc = new Scanner(System.in);
        String choice;

        do {
            System.out.println("[1] View Resident's Profile");
            System.out.println("[2] Sort");
            System.out.println("[3] Exit");
            System.out.print("Select: ");
            choice = sc.nextLine();
            System.out.println("================================================================================");
            switch (choice) {
                case "1":
                    System.out.println("Enter resident's name: ");
                    String name = sc.nextLine();
                    String residentName = name.replaceAll(" ", "").toLowerCase();
                    if (!residentInCurrentArea.contains(residentName)) {
                        System.out.println("Please enter the resident's name in this area only.");
                        continue;
                    }
                    System.out.println(
                            "================================================================================");
                    JoestarsChecker = new TheJoestarsChecker();
                    System.out.println(JoestarsChecker.readResidentProfiles(residentName));
                    System.out.println(
                            "================================================================================");
                    break;
                case "2":
                    System.out.println(
                            "Enter the sorting order (comma-separated criteria) (e.g., Stamina(ASC);Precision(DESC);Stand(ASC);):");
                    String sortingOrder = sc.nextLine();
                    if (!sortingOrder.matches("^(\\s*[a-zA-Z]+\\s*\\((?i:ASC|DESC)\\)\\s*;\\s*)+$")) {
                        System.out.println("Please follow the correct format.");
                        continue;
                    }
                    System.out.println(
                            "================================================================================");
                    sortResidents(sortingOrder);
                    break;
                case "3":
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (true);
    }
}
