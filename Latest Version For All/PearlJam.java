package JOJOLands.JOJO;

import java.util.*;

//maybe can use bubble sort but no time
public class PearlJam {
    private LinkedList<String[]> waitingList;
    private LinkedList<String[]> processedList;
    private int processedDay;
    private String currentLocation;

    public PearlJam(String currentLocation, int day) {
        this.waitingList = new LinkedList<>(WaitingListGenerator.getWaitingListPearlJam(currentLocation));
        this.processedDay = day;
        this.currentLocation = currentLocation;
        // Process orders based on the restaurant's rule
        this.processedList = new LinkedList<>();
        
        processOrdersByRestaurant(currentLocation);
        
    }

    public void processOrdersByRestaurant(String restaurant) {
        if (waitingList.isEmpty()) {
            System.out.println("No customers visited " + restaurant + ".");
            return;
        }

        switch (restaurant) {
            case "Jade Garden":
                processOrdersInJadeGarden(waitingList);
                break;
            case "Cafe Deux Magots":
                processOrdersInCafeDeuxMagots(waitingList);
                break;
            case "Trattoria Trussardi":
                processOrdersInTrattoriaTrussardi(waitingList);
                break;
            case "Libeccio":
                processOrdersInLibeccio(waitingList);
                break;
            case "Savage Garden":
                processOrdersInSavageGarden(waitingList);
                break;
            default:
                System.out.println("Unknown restaurant: " + restaurant);
                return;
        }
    }

    // first and last customers to arrive are served first, followed by thesecond
    // and second-last, and so on.
    private void processOrdersInJadeGarden(LinkedList<String[]> waitingList) {
        int size = waitingList.size();

        while (!waitingList.isEmpty()) {
            String[] firstCustomer = waitingList.removeFirst(); // Remove the first customer

            // Check if there is only one customer left in the list
            if (waitingList.isEmpty()) {
                processedList.addLast(firstCustomer); // Add the last customer to the processed list
                break;
            }

            String[] lastCustomer = waitingList.removeLast(); // Remove the last customer

            processedList.addLast(firstCustomer); // Add the first customer to the processed list
            processedList.addLast(lastCustomer); // Add the last customer to the processed list

            size -= 2; // Update the remaining size

            if (size == 1) {
                processedList.addLast(waitingList.removeFirst()); // Add the remaining customer to the processed list
                break; // Exit the loop if all customers have been processed
            }
        }
    }

    // queue list follow age
    private void processOrdersInCafeDeuxMagots(LinkedList<String[]> waitingList) {
        LinkedList<String[]> knownAgesList = new LinkedList<>();
        LinkedList<String[]> unknownAgesList = new LinkedList<>();

        // Separate customers with unknown ages from others
        for (String[] customer : waitingList) {
            if (customer[2].equals("N/A")) {
                unknownAgesList.add(customer);
            } else {
                knownAgesList.add(customer);
            }
        }

        knownAgesList.sort(Comparator.comparing(c -> parseAge(c[2])));

        // Process orders based on age criteria
        while (knownAgesList.size() >= 2) {
            String[] youngest = knownAgesList.removeFirst(); // Remove the youngest
            String[] oldest = knownAgesList.removeLast(); // Remove the oldest

            processedList.add(oldest);
            processedList.add(youngest);
        }

        // Add remaining customers with unknown ages to the end of the processed list
        processedList.addAll(unknownAgesList);
    }

    private int parseAge(String ageString) {
        if (ageString.equals("N/A")) {
            return Integer.MIN_VALUE; // Return a specific value for "N/A"
        } else {
            try {
                return Integer.parseInt(ageString);
            } catch (NumberFormatException e) {
                // Handle any non-integer age values
                return 0; // Or any other appropriate value for invalid ages
            }
        }
    }

    // serves the youngest man first, followed by the oldest woman. In the next
    // turn, it’s the oldest man and then the youngest woman.
    private void processOrdersInTrattoriaTrussardi(LinkedList<String[]> waitingList) {
        // Split the waiting list into male and female lists
        LinkedList<String[]> maleList = new LinkedList<>();
        LinkedList<String[]> femaleList = new LinkedList<>();
        LinkedList<String[]> unknownAgeList = new LinkedList<>();

        for (String[] person : waitingList) {
            if (person[2].equals("N/A")) {
                unknownAgeList.add(person);
            }
            else if (person[3].equals("Male")) {
                maleList.add(person);
            } else if (person[3].equals("Female")) {
                femaleList.add(person);
            } 
        }

        // Sort male list from youngest to oldest
        maleList.sort(Comparator.comparing(c -> Integer.parseInt(c[2])));

        // Sort female list from oldest to youngest
        femaleList.sort(Comparator.comparing(c -> Integer.parseInt(c[2])));

        while (!maleList.isEmpty() && !femaleList.isEmpty()) {
            // Serve the youngest man
            String[] youngestMan = maleList.removeFirst();
            processedList.add(youngestMan);

            // Serve the oldest woman
            String[] oldestWoman = femaleList.removeLast();
            processedList.add(oldestWoman);

            // Serve the oldest man
            if (!maleList.isEmpty()) {
                String[] oldestMan = maleList.removeLast();
                processedList.add(oldestMan);
            }

            // Serve the youngest woman
            if (!femaleList.isEmpty()) {
                String[] youngestWoman = femaleList.removeFirst();
                processedList.add(youngestWoman);
            }
        }

        // pollFirst ensures that null is returned when the list is empty, allowing the
        // loop to terminate.
        // Add remaining persons from male list if not empty by 1 young 1 old
        while (!maleList.isEmpty()) {
            processedList.add(maleList.removeFirst());
            while (!maleList.isEmpty()) {
            processedList.add(maleList.removeLast());
            }
        }

        // Add remaining persons from female list if not empty
        while (!femaleList.isEmpty()) {
            processedList.add(femaleList.removeLast());
            while (!femaleList.isEmpty()) {
            processedList.add(femaleList.removeFirst());
            }
        }

        // Add the unknown afe person into list
        processedList.addAll(unknownAgeList);
    }

    private void processOrdersInLibeccio(LinkedList<String[]> waitingList) {
        int currentDay = processedDay; //// Get the current day number

        int count = 1; // Initialize the count to 1
        int index = 1; // Initialize the index of the current person in the waiting list

        while (!waitingList.isEmpty()) {
            if (count % currentDay == 0) {
                String[] removedPerson = waitingList.remove(index-1); // Remove the person at the current index
                processedList.addLast(removedPerson); // Add the removed person to the end of the processed list
                if (index > waitingList.size()) {
                    // Reached the end of the waiting list, start over from the first person
                    index = 1;
                }
                count++;
            }else{
            count++;
            index++;
            if (index > waitingList.size()) {
                index = 1;
            }
        }
        }

        // Reverse the processed list to maintain the order of serving last
        Collections.reverse(processedList);
    }

    // number matches the day number, served first.
    private void processOrdersInSavageGarden(LinkedList<String[]> waitingList) {
        int currentDay = processedDay;
        int count = 1;
        int index = 1;
    
        while (!waitingList.isEmpty()) {
            if (count == currentDay) {
                // Add the removed person to the end of the processed list
                processedList.addLast(waitingList.remove(index - 1));
                count = 1;
                
                if (index >= waitingList.size()) {
                    // Reached the end of the waiting list, start over from the last person in reverse
                    Collections.reverse(waitingList);
                    index = 1;
                }
            } 
            else {
                count++;
                index++;
                
                if (index >= waitingList.size()) {
                    // Reached the end of the waiting list, start over from the first person
                    Collections.reverse(waitingList);
                    index = 1;
                }
            }
        }
    }

    public void displayWaitingList(){
        List<String[]> waitinglist = WaitingListGenerator.getWaitingListPearlJam(currentLocation);

        System.out.println("Waiting List of " + currentLocation);
        System.out.println(
                "+----+-------------------------+-----+--------+---------------+-------------------------------------+");
        System.out.println(
                "| No |          Name           | Age | Gender | Arrival Time  |                Order                |");
        System.out.println(
                "+----+-------------------------+-----+--------+---------------+-------------------------------------+");

        for (int i = 0; i < waitinglist.size(); i++) { 
            String[] customer = waitinglist.get(i);
            String index = customer[0];
            String name = customer[1];
            String age = customer[2];
            String gender = customer[3];
            String arrivalTime = customer[4];
            String order = customer[5];  

            System.out.printf("| %-2s | %-23s | %-3s | %-6s | %-14s| %-35s |\n", index, name, age, gender,arrivalTime, order);
        }
        System.out.println(
                "+----+-------------------------+-----+--------+---------------+-------------------------------------+");
        System.out
                .println("============================================================================");
        System.out.println();

    }

    public void displayList() {
        System.out.println("Order Processing List of "+ currentLocation);
        System.out.println(
                "+----+-------------------------+-----+--------+-------------------------------------+");
        System.out.println(
                "| No |          Name           | Age | Gender |                Order                |");
        System.out.println(
                "+----+-------------------------+-----+--------+-------------------------------------+");

        int index = 1;
        for (int i = 0; i < processedList.size(); i++) { 
            String[] customer = processedList.get(i);
            String name = customer[1];
            String age = customer[2];
            String gender = customer[3];
            String order = customer[5];  

            System.out.printf("| %-2d | %-23s | %-3s | %-6s | %-35s |\n", index, name, age, gender, order);
            index++;
        }
        System.out.println(
                "+----+-------------------------+-----+--------+-------------------------------------+");
        System.out
                .println("============================================================================");
        System.out.println();
    }
}
