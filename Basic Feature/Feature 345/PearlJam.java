package JOJOLands;

import java.util.*;

//maybe can use bubble sort but no time
public class PearlJam extends WaitingListGenerator {
    private LinkedList<String[]> waitingList;
    private LinkedList<String[]> processedList;
    private int processedDay;

    public PearlJam(String currentLocation, int day) {
        super(currentLocation, day);
        super.addCustomerToWaitingList();
        super.WaitingList(currentLocation);
        this.waitingList = new LinkedList<>(super.getWaitingList());
        this.processedDay = day;
        // Process orders based on the restaurant's rule
        this.processedList = new LinkedList<>();
        processOrdersByRestaurant(currentLocation);
        displayList();

    }

    public void processOrdersByRestaurant(String restaurant) {
        if (waitingList.size() <= 2) {
            System.out.println("No customers in the waiting list.");
            return;
        }

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
        displayList();
    }

    //first and last customers to arrive are served first, followed by thesecond and second-last, and so on.
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

            size -= 2;  // Update the remaining size

            if (size <= 0) {
                break;    // Exit the loop if all customers have been processed
            }
        }
    }

    //queue list follow age
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

        knownAgesList.sort(Comparator.comparing(c -> Integer.parseInt(c[2])));

        // Process orders based on age criteria
        while (knownAgesList.size() >= 2) {
            String[] youngest = knownAgesList.removeFirst();  // Remove the youngest
            String[] oldest = knownAgesList.removeLast();   // Remove the oldest

            processedList.add(oldest);
            processedList.add(youngest);
        }

        // Add remaining customers with unknown ages to the end of the processed list
        processedList.addAll(unknownAgesList);
    }

    //serves the youngest man first, followed by the oldest woman. In the next turn, it’s the oldest man and then the youngest woman.
    private void processOrdersInTrattoriaTrussardi(LinkedList<String[]> waitingList) {
        // Split the waiting list into male and female lists
        LinkedList<String[]> maleList = new LinkedList<>();
        LinkedList<String[]> femaleList = new LinkedList<>();
        LinkedList<String[]> unknownGenderList = new LinkedList<>();

        for (String[] person : waitingList) {
            if (person[3].equals("Male")) {
                maleList.add(person);
            } else if (person[3].equals("Female")) {
                femaleList.add(person);
            } else if (person[3].equals("N/A")) {
                unknownGenderList.add(person);
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

        //pollFirst  ensures that null is returned when the list is empty, allowing the loop to terminate gracefully.
        // Add remaining persons from male list if not empty
        while (!maleList.isEmpty()) {
            processedList.add(maleList.pollFirst());
        }

        // Add remaining persons from female list if not empty
        while (!femaleList.isEmpty()) {
            processedList.add(femaleList.pollFirst());
        }
        
        //Add the unknown gender person into list
        while(!unknownGenderList.isEmpty()){
        processedList.addAll(unknownGenderList);
        }
    }

    private void processOrdersInLibeccio(LinkedList<String[]> waitingList) {
        int currentDay = processedDay;  //// Get the current day number

        int count = 1;  // Initialize the count to 1
        int index = 0; // Initialize the index of the current person in the waiting list

        while (!waitingList.isEmpty()) {
            if (count % currentDay == 0) {
                String[] removedPerson = waitingList.remove(index);  // Remove the person at the current index
                processedList.addLast(removedPerson); // Add the removed person to the end of the processed list
                index--; // Adjust the index after removing a person
            }

            count++;
            index++;

            if (index >= waitingList.size()) {
                // Reached the end of the waiting list, start over from the beginning
                index = 0;
            }
        }

        // Reverse the processed list to maintain the order of serving last
        Collections.reverse(processedList);
    }

    //number matches the day number, served first.
    private void processOrdersInSavageGarden(LinkedList<String[]> waitingList) {
        int currentDay = processedDay;

        int count = 1;
        int index = 0;

        while (!waitingList.isEmpty()) {
            if (count == currentDay) {
                String[] removedPerson = waitingList.remove(index); // Add the removed person to the end of the processed list
                processedList.addLast(removedPerson);
                count = 1;

                if (index >= waitingList.size()) {
                    // Reached the end of the waiting list, start over from the last person in reverse order
                    index = waitingList.size() - 1;
                } else {
                    index--;
                }
            } else {
                count++;
                index++;

                if (index >= waitingList.size()) {
                    // Reached the end of the waiting list, start over from the first person
                    index = 0;
                }
            }

            if (index < 0) {
                // Reached the beginning of the waiting list, start over from the last person in reverse order
                index = waitingList.size() - 1;
            }
        }
    }

    public void displayList() {
        System.out.println("Order Processing List:");
        System.out.println(
                "+----+-------------------------+-----+--------+-------------------------------------+");
        System.out.println(
                "| No |          Name           | Age | Gender |                Order                |");
        System.out.println(
                "+----+-------------------------+-----+--------+-------------------------------------+");

        int index = 1;
        for (int i = 1; i < processedList.size(); i++) {
            String[] customer = processedList.get(i);
            String name = customer[1];
            String age = customer[2];
            String gender = customer[3];
            String order = customer[5];

            System.out.printf("| %-2d | %-23s | %-3s | %-6s | %-35s |\n", index, name, age, gender, order);
            index++;
        }
        System.out.println(
                "+----+--------------------+-------------------------+-----+--------+");
        System.out
                .println("======================================================================");
        System.out.println();
    }
}
