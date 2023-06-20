# Extra Feature
### Extra Feature 1 -- Another One Bite of Dust<br>
Requirements<br>
● Create a new option Another One Bites the Dust in Angelo Rock.<br>
● A player can enter the path of locations Yoshikage Kira remembered he went through.<br>
● The system should display whether the Bites the Dust ability has been activated.<br>
● If so, the system should display the longest non-overlapping path of consecutive locations that are repeated at least twice in the original path, as Bites the Dust was most likely to be activated when Kira passed through them.<br>
● The path should consist of more than one location.<br>
● If there are multiple paths with the same longest length, the system would display any of the paths. <br>

### Extra Feature 4 -- Dirty Deeds Done Dirt Cheap <br>
Requirements<br>
● Create a new option Dirty Deeds Done Dirt Cheap in Green Dolphin Street Prison.<br>
● A player can enter a source and a destination as inputs.<br>
● The system displays the top three shortest paths with their respective total distances between the source and destination.<br>
● If two paths have the same total distance, the one with fewer locations is given priority.<br>
● The paths should not visit any location more than once.<br>

### Extra Feature 7 -- The Golden Spirit <br>
Requirements<br>
● Create a new option The Golden Spirit in Joestar Mansion.<br>
● A player can enter the names of any two Joestars to search for their lowest common ancestors.<br>
● The system should display all lowest common ancestors of the two Joestars entered.<br>
● Note that there might be more than one lowest common ancestor.<br>

### Extra Feature 8 -- Thus Spoke Rohan Kishibe <br>
Requirements<br>
● Create a new option Thus Spoke Rohan Kishibe in Morioh Grand Hotel.<br>
● A player can enter the locations that Rohan would like to visit.<br>
● The system should display the shortest path, which starts from Rohan’s home (Morioh Grand Hotel) and includes all the locations specified, with the total distance.<br>
● The sequence in which Rohan visits the locations doesn’t matter.<br>
● Rohan doesn’t mind passing through other places or visiting a location more than once.<br>
**Solution** <br>
*findShortestPath(List<String> locations):*

This method takes a list of locations as input and returns the shortest path that connects those locations.
It initializes an empty list called shortestPath to store the final result.
The startVertex is set to "Morioh Grand Hotel", which represents the starting point of the path.
It iterates over the list of locations:
For each location, it calls the findPath method to retrieve the path from the startVertex to the current endVertex.
If it's not the first location in the list (i > 0), it adds only the portion of the path from the second vertex to the last vertex (path.subList(1, path.size())) to the shortestPath list.
Otherwise, for the first location, it adds the entire path to the shortestPath list.
It updates the startVertex to be the current endVertex for the next iteration.
Finally, it returns the shortestPath, which contains the complete shortest path connecting all the locations.<br>
*findPath(String startVertex, String endVertex):*

This method finds the shortest path from a startVertex to an endVertex using Dijkstra's algorithm.
It uses a PriorityQueue<VertexEntry> to store vertices based on their distance from the startVertex.
It creates two maps: distance to store the minimum distance from the startVertex to each vertex, and previousVertex to store the previous vertex in the shortest path.
Initially, it sets the distance of all vertices to infinity except for the startVertex, which is set to 0.
It adds the startVertex to the priority queue with a distance of 0.
While the priority queue is not empty:
It retrieves the vertex with the minimum distance (currentEntry) from the priority queue.
If the current vertex is the endVertex, it means the shortest path has been found, so the loop is exited.
It retrieves the current distance from the distance map.
For each neighbor of the current vertex:
It calculates the total distance from the startVertex to the neighbor through the current vertex.
If the total distance is less than the recorded distance for the neighbor, it updates the distance and previous vertex in the respective maps.
It adds the neighbor and its total distance as a new VertexEntry to the priority queue.
After finding the shortest path, it reconstructs the path by starting from the endVertex and following the previousVertex map until it reaches the startVertex.
The path is stored in the path list by adding each vertex at the beginning (path.add(0, currentVertex)).
Finally, it adds the startVertex to the beginning of the path list and returns it.
These methods work together to find the shortest path between multiple locations by repeatedly finding the shortest path between consecutive locations using Dijkstra's algorithm.
