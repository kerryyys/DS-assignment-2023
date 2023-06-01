<!DOCTYPE html>
<html>
<head>
<title> Basic Feature </title>
</head>
  <body>
There are 8 basic features in this file.<br>
Each class is created according to their feature names and there are some helper classes to assist the functionalities of the classes.<br>
Player as the Main class to run the whole program<br>

<b>Hermit Purple</b><br>
as the main menu to diplay whole output.<br>
Requirement:<br>
● Create a virtual game environment of JOJOLands based on the map given.<br>
● A player can move to adjacent locations connected by roads from his current location.<br>
● A player can travel back to the most recent location visited.<br>
● A player can move forward in history if he chooses the back option.<br>
● The system clears a player’s forward history when he decides to move to a new location.<br>
● A player can choose to travel back to the Town Hall directly.<br>
● The system displays the available missions in each location.<br>
● The system recognises the first day as Day 1, which represents Sunday.<br>
● A player starts at the Town Hall at the start of each day.<br>
● A player can advance to the next day by selecting the corresponding option at the Town Hall.<br>

<b>HeavensDoor</b><br>
function to display the information of the resident in each location, and sorting can be selected<br>
Requirement:<br>
● The system retrieves the information about the residents and Stands from the two provided files.<br>
● A player can view all the information about the residents in each residential area.<br>
● A player can view the information on a resident’s Stand if the resident is a Stand user.<br>
● A player can sort the residents in a self-defined order by multiple fields, including each of their orders.<br>
● Built-in sorting methods like Arrays.sort are not allowed.<br>
● The Stand parameters follow an ascending order of Infinity, A, B, C, D, E, ?, and Null.<br>

<b>PearlJam</b><br>
Requirement: <br>
● A player can view the waiting list and order processing list of a restaurant of the current day.<br>
● A player can view the name, age, and gender of each customer and the food they ordered in the waiting list and order processing list.<br>
● The system sorts the waiting list by the time the customers arrive in ascending order.<br>
● The system generates the order processing list based on the rule specific to every restaurant, as stated above.<br>
● No resident is allowed to dine more than once in one day.<br>

<b>TheJoestars</b><br>
Requirement: <br>
● A player can select a resident in a given residential area to view his detailed information.<br>
● A player can view a resident’s recent food and restaurant history, which should match the restaurant’s daily sales records.<br>
● The system randomly selects a food and restaurant for each resident. <br>
● The random food selection algorithm should satisfy the Joestars’ food preferences. <br>

<b>MondayBlues</b><br>
Requirement: <br>
● A player can view the number of each food sold with its total price on a specific day.<br>
● A player can specify a range of days to view the aggregated information on the sales.<br>
● The aggregated information comprises the minimum, maximum, and top k highest sales, as well as the total and average sales.<br>
● The system should display the information in a table format.<br>

<b>MilagroMan</b><br>
Requirement: <br>
● A player can create new food in a restaurant.<br>
● A player can modify existing food information in a restaurant.<br>
● A player can remove existing food in a restaurant.<br>
● Modification of food information should only affect the sales records starting from the next day without affecting the previous sales records.<br>
● Create a new option Milagro Man in each restaurant that allows a player to enter the experimental mode.<br>
● In Milagro Man:<br>
  ○ A player can modify any food price within a given range of days.<br>
  ○ A player can perform the queries as in 5. Moody Blues with the updated prices.<br>
● The system restores sales records to their original state once a player exits the Milagro Man.<br>

<b>SuperFly</b><br>
Requirement: <br>
● Create a new option Red Hot Chili Pepper in Angelo Rock.<br>
● In Red Hot Chili Pepper,<br>
  ○ A player can view the necessary power cables to be upgraded with the total length.<br>
  ○ The total length of the upgraded connections should be minimised.<br>
  ○ There should always be an upgraded electrical path between any two locations.<br>
● Create a new option The Hand in Morioh Grand Hotel.<br>
● In The Hand,<br>
  ○ A player can view the unnecessary water connections to be removed with the total length.<br>
  ○ The total length of the removed connections should be minimised.<br>
  ○ Every location should only have one connection with the water supply from the Town Hall.<br>

<b>TheWorld</b><br>
Requirement: <br>
● A player can select from multiple maps available at the start of the game.<br>
● The maps should include the Default Map, Parallel Map, and Alternate Map.<br>
● A player can save the current game state.<br>
● A player can load a game state from a save file.<br>
● When loading a save file, the game state should be identical to the state in which it was saved.<br>


