<title>Basic Feature<title>
There are 8 basic features in this file.<br>
Each class is created according to their feature names and there are some helper classes to assist the functionalities of the classes.
Player as the Main class to run the whole program<br>
<b>Hermit Purple</b>
as the main menu to diplay whole output.<br>
Requirement:<br>
● Create a virtual game environment of JOJOLands based on the map given.
● A player can move to adjacent locations connected by roads from his current location.
● A player can travel back to the most recent location visited.
● A player can move forward in history if he chooses the back option.
● The system clears a player’s forward history when he decides to move to a new location.
● A player can choose to travel back to the Town Hall directly.
● The system displays the available missions in each location.
● The system recognises the first day as Day 1, which represents Sunday.
● A player starts at the Town Hall at the start of each day.
● A player can advance to the next day by selecting the corresponding option at the Town Hall.

<b>HeavensDoor<br>
function to display the information of the resident in each location, and sorting can be selected<br>
Requirement:<br>
● The system retrieves the information about the residents and Stands from the two provided files.
● A player can view all the information about the residents in each residential area.
● A player can view the information on a resident’s Stand if the resident is a Stand user.
● A player can sort the residents in a self-defined order by multiple fields, including each of their orders.
● Built-in sorting methods like Arrays.sort are not allowed.
● The Stand parameters follow an ascending order of Infinity, A, B, C, D, E, ?, and Null.

<b>PearlJam</b><br>
Requirement: <br>
● A player can view the waiting list and order processing list of a restaurant of the current day.
● A player can view the name, age, and gender of each customer and the food they ordered in the waiting list and order processing list.
● The system sorts the waiting list by the time the customers arrive in ascending order.
● The system generates the order processing list based on the rule specific to every restaurant, as stated above.
● No resident is allowed to dine more than once in one day.

<b>TheJoestars</b><br>
Requirement: <br>
● A player can select a resident in a given residential area to view his detailed information.
● A player can view a resident’s recent food and restaurant history, which should match the restaurant’s daily sales records.
● The system randomly selects a food and restaurant for each resident.
● The random food selection algorithm should satisfy the Joestars’ food preferences.

<b>MondayBlues</b><br>
Requirement: <br>
● A player can view the number of each food sold with its total price on a specific day.
● A player can specify a range of days to view the aggregated information on the sales.
● The aggregated information comprises the minimum, maximum, and top k highest sales, as well as the total and average sales.
● The system should display the information in a table format.

<b>MilagroMan</b><br>
Requirement: <br>
● A player can create new food in a restaurant.
● A player can modify existing food information in a restaurant.
● A player can remove existing food in a restaurant.
● Modification of food information should only affect the sales records starting from the next day without affecting the previous sales records.
● Create a new option Milagro Man in each restaurant that allows a player to enter the experimental mode.
● In Milagro Man:
  ○ A player can modify any food price within a given range of days.
  ○ A player can perform the queries as in 5. Moody Blues with the updated prices.
● The system restores sales records to their original state once a player exits the Milagro Man.

<b>SuperFly</b><br>
Requirement: <br>
● Create a new option Red Hot Chili Pepper in Angelo Rock.
● In Red Hot Chili Pepper,
  ○ A player can view the necessary power cables to be upgraded with the total length.
  ○ The total length of the upgraded connections should be minimised.
  ○ There should always be an upgraded electrical path between any two locations.
● Create a new option The Hand in Morioh Grand Hotel.
● In The Hand,
  ○ A player can view the unnecessary water connections to be removed with the total length.
  ○ The total length of the removed connections should be minimised.
  ○ Every location should only have one connection with the water supply from the Town Hall.

<b>TheWorld</b><br>
Requirement: <br>
● A player can select from multiple maps available at the start of the game.
● The maps should include the Default Map, Parallel Map, and Alternate Map.
● A player can save the current game state.
● A player can load a game state from a save file.
● When loading a save file, the game state should be identical to the state in which it was saved.


