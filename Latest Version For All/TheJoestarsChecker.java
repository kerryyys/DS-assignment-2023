package JOJOLands.JOJO;

import java.io.*;
import java.util.*;

public class TheJoestarsChecker {

    private String directory = HermitPurple.directoryPath;

    public String readResidentProfiles(String nameSaved) {
        String filename = directory + "/profile_" + nameSaved + ".txt";

        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    public List<String[]> readOrderHistory(String name) {
        String filename = directory + "/" + name + "_order_history" + ".txt";
        List<String[]> matchingLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] orders = line.split(";");
                for (String order : orders) {
                    String[] orderDetails = order.split(",");
                    matchingLines.add(orderDetails);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matchingLines;
    }
}
