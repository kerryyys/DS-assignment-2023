package JOJOLands;

public class JOJOMaps{
    
    protected Graph <String, Integer> maps = new Graph<>();
    protected String [] location;
    
    public JOJOMaps(){
        location = new String[] {"Town Hall", "Morioh Grand Hotel", "Jade Garden", "Cafe Deux Magots", "Trattoria Trussardi", "San Giorgio Maggiore", "Joestar Mansion", "Polnareff Land", "Savage Garden", "Green Dolphin Street Prison", "Libeccio", "Angelo Rock", "DIO's Mansion", "Vineyard"};
        for(String i : location)
        maps.addVertex(i);

        //add edge to connect each location, in km
        maps.addEdge("Town Hall", "Morioh Grand Hotel",5);
        maps.addEdge("Town Hall", "Jade Garden", 5);
        maps.addEdge("Town Hall", "Cafe Deux Magots", 4);

        maps.addEdge("Morioh Grand Hotel", "Trattoria Trussardi", 6);
        maps.addEdge("Morioh Grand Hotel", "Jade Garden", 3);

        maps.addEdge("Jade Garden", "Cafe Deux Magots", 3);
        maps.addEdge("Jade Garden", "San Giorgio Maggiore", 2);
        maps.addEdge("Jade Garden", "Joestar Mansion", 2);

        maps.addEdge("Cafe Deux Magots", "Polnareff Land", 4);
        maps.addEdge("Cafe Deux Magots", "Savage Garden", 4);

        maps.addEdge("Trattoria Trussardi", "Green Dolphin Street Prison", 6);
        maps.addEdge("Trattoria Trussardi", "San Giorgio Maggiore", 3);

        maps.addEdge("Green Dolphin Street Prison", "Libeccio", 3);
        maps.addEdge("Green Dolphin Street Prison", "Angelo Rock", 2);

        maps.addEdge("Libeccio", "DIO's Mansion", 2);
        maps.addEdge("Libeccio", "Joestar Mansion", 6);
        maps.addEdge("Libeccio", "Vineyard", 6);
        maps.addEdge("Libeccio", "San Giorgio Maggiore", 4);

        maps.addEdge("Angelo Rock", "DIO's Mansion", 3);

        maps.addEdge("Vineyard", "Joestar Mansion", 3);
        maps.addEdge("Vineyard", "Savage Garden", 8);
        maps.addEdge("Vineyard", "DIO's Mansion", 3);

        maps.addEdge("Savage Garden", "Joestar Mansion", 4);
        maps.addEdge("Savage Garden", "Polnareff Land", 6);
    }
/* 
    public Graph<String, Integer> getMaps() {
        return maps;
    }
*/
}