This file contain 3 features which is interconnect and Feature 3 is PeralJam class, The JoeStars and MondayBlue.<br>
The WaitingList Generator class is used to randomly generate the list and store the list into file <br>
The Resident class is used to read the residents.csv and stands.csv file<br>
TheJoestars has method to apply filter in Feature 5<br>
Monday Blue used to count the sales<br>
PearlJam will sort the list according to the restaurant<br>

all need to run the waiting list is to get the overall waiting list of that day in WaitingListGenerator, then use TheJoestars, followed by other method in specific location to call their method.<br>

CAN BE USED TO TEST RUN TO SEE THE CONTENT IN THE FILE
public static void main(String[] args) {
    String filename = "myfile.txt";
    String newContent = "This is new content.";

    // Append new content to the existing file
    appendToFile(newContent, filename);
}
