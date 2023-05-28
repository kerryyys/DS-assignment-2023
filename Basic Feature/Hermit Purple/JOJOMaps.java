package JOJOLands;

public class JOJOMaps{
    
    Graph <String, Integer> maps = new Graph<>();
    //Graph <String, Integer> Parallel_maps = new Graph<>();
    //Graph <String, Integer> Alternate_maps = new Graph<>();
    protected String [] location;
    protected String currentLocation;

    public Graph<String, Integer> getMaps() {
        return maps;
    }

    
    public JOJOMaps(int maptype) {
        location = new String[] {"Town Hall", "Morioh Grand Hotel", "Jade Garden", "Cafe Deux Magots", "Trattoria Trussardi", "San Giorgio Maggiore", "Joestar Mansion", "Polnareff Land", "Savage Garden", "Green Dolphin Street Prison", "Libeccio", "Angelo Rock", "DIO’s Mansion", "Vineyard"};
        for(String i : location)
        maps.addVertex(i);

        currentLocation = "Town Hall";
        
        if(maptype== 1){
            
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

        maps.addEdge("Libeccio", "DIO’s Mansion", 2);
        maps.addEdge("Libeccio", "Joestar Mansion", 6);
        maps.addEdge("Libeccio", "Vineyard", 6);
        maps.addEdge("Libeccio", "San Giorgio Maggiore", 4);

        maps.addEdge("Angelo Rock", "DIO’s Mansion", 3);

        maps.addEdge("Vineyard", "Joestar Mansion", 3);
        maps.addEdge("Vineyard", "Savage Garden", 8);
        maps.addEdge("Vineyard", "DIO’s Mansion", 3);

        maps.addEdge("Savage Garden", "Joestar Mansion", 4);
        maps.addEdge("Savage Garden", "Polnareff Land", 6);
        }
        
        else if(maptype == 2){
        maps.addEdge("Town Hall", "Vineyard", 3);
        maps.addEdge("Town Hall", "Libeccio", 2);
        maps.addEdge("Town Hall", "Trattoria Trussardi", 6);
        maps.addEdge("Town Hall", "Cafe Deux Magots", 4);
        
        maps.addEdge("Vineyard", "Libeccio", 3);
        
        maps.addEdge("Trattoria Trussardi", "Angelo Rock", 3);
        maps.addEdge("Trattoria Trussardi", "DIO's Mansion", 4);
        maps.addEdge("Trattoria Trussardi", "Joestar Mansion", 5);
        
        maps.addEdge("Angelo Rock", "DIO's Mansion", 1);
        maps.addEdge("Angelo Rock", "Green Dolphin Street Prison", 8);
        
        maps.addEdge("DIO's Mansion", "Green Dolphin Street Prison", 6);
        
        maps.addEdge("Joestar Mansion", "Morioh Grand Hotel", 4);
        maps.addEdge("Joestar Mansion", "Jade Garden", 3);
        maps.addEdge("Joestar Mansion", "San Giorgio Magggiore", 5);
        
        maps.addEdge("Jade Garden", "Cafe Deux Magots", 3);
        maps.addEdge("Jade Garden", "Savage Garden", 4);
        
        maps.addEdge("Savage Garden", "San Giorgio Maggiore", 6);
        maps.addEdge("Savage Garden", "Cafe Deux Magots", 5);
        
        maps.addEdge("Cafe Deux Magots", "Morioh Grand Hotel", 6);
        maps.addEdge("Cafe Deux Magots", "Polnareff Land", 2);
        }
        else if(maptype == 3){
        maps.addEdge("Town Hall", "Morioh Grand Hotel",2);
        maps.addEdge("Town Hall", "Green Dolphin Street Prison",3);
        maps.addEdge("Town Hall", "Passione Restaurant",7);
        
        maps.addEdge("Morioh Grand Hotel", "San Giorgio Maggiore",3);
        maps.addEdge("Morioh Grand Hotel", "Joestar Mansion",4);
        maps.addEdge("Morioh Grand Hotel", "Green Dolphin Street Prison",2);
        
        maps.addEdge("San Giorgio Maggiore", "Savage Garden",6);
        
        maps.addEdge("Savage Garden", "Vineyard", 4);
        
        maps.addEdge("Vineyard", "Cafe Deux Magots", 4);
        
        maps.addEdge("Cafe Deux Magots", "DIO's Mansion", 1);
        maps.addEdge("Cafe Deux Magots", "Passione Restaurant", 4);
         
        maps.addEdge("DIO's Mansion", "Polnareff Land",2);
        maps.addEdge("DIO's Mansion", "Passione Restaurant",2);
        
        maps.addEdge("Polnareff Land", "Jade Garden",2);
        maps.addEdge("Polnareff Land", "Angelo Rock",2);
        
        maps.addEdge("Angelo Rock", "Jade Garden",1);
        maps.addEdge("Angelo Rock", "Passione Restaurant",6);
        
        maps.addEdge("Trattoria Trussardi", "Passione Restaurant",1);
        maps.addEdge("Trattoria Trussardi", "Green Dolphin Street Prison",4);
        maps.addEdge("Trattoria Trussardi", "Joestar Mansion",5);
                }
        
        else{
            System.out.println("Out of Bound");
        }
    }
    /*
    public JOJOMaps(){
        location = new String[] {"Town Hall", "Morioh Grand Hotel", "Jade Garden", "Cafe Deux Magots", "Trattoria Trussardi", "San Giorgio Maggiore", "Joestar Mansion", "Polnareff Land", "Savage Garden", "Green Dolphin Street Prison", "Libeccio", "Angelo Rock", "DIO’s Mansion", "Vineyard"};
        for(String i : location)
        maps.addVertex(i);

        currentLocation = "Town Hall";

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

        maps.addEdge("Libeccio", "DIO’s Mansion", 2);
        maps.addEdge("Libeccio", "Joestar Mansion", 6);
        maps.addEdge("Libeccio", "Vineyard", 6);
        maps.addEdge("Libeccio", "San Giorgio Maggiore", 4);

        maps.addEdge("Angelo Rock", "DIO’s Mansion", 3);

        maps.addEdge("Vineyard", "Joestar Mansion", 3);
        maps.addEdge("Vineyard", "Savage Garden", 8);
        maps.addEdge("Vineyard", "DIO’s Mansion", 3);

        maps.addEdge("Savage Garden", "Joestar Mansion", 4);
        maps.addEdge("Savage Garden", "Polnareff Land", 6);
        
        Parallel_maps.addEdge("Town Hall", "Vineyard", 3);
        Parallel_maps.addEdge("Town Hall", "Libeccio", 2);
        Parallel_maps.addEdge("Town Hall", "Trattoria Trussardi", 6);
        Parallel_maps.addEdge("Town Hall", "Cafe Deux Magots", 4);
        
        Parallel_maps.addEdge("Vineyard", "Libeccio", 3);
        
        Parallel_maps.addEdge("Trattoria Trussardi", "Angelo Rock", 3);
        Parallel_maps.addEdge("Trattoria Trussardi", "DIO's Mansion", 4);
        Parallel_maps.addEdge("Trattoria Trussardi", "Joestar Mansion", 5);
        
        Parallel_maps.addEdge("Angelo Rock", "DIO's Mansion", 1);
        Parallel_maps.addEdge("Angelo Rock", "Green Dolphin Street Prison", 8);
        
        Parallel_maps.addEdge("DIO's Mansion", "Green Dolphin Street Prison", 6);
        
        Parallel_maps.addEdge("Joestar Mansion", "Morioh Grand Hotel", 4);
        Parallel_maps.addEdge("Joestar Mansion", "Jade Garden", 3);
        Parallel_maps.addEdge("Joestar Mansion", "San Giorgio Magggiore", 5);
        
        Parallel_maps.addEdge("Jade Garden", "Cafe Deux Magots", 3);
        Parallel_maps.addEdge("Jade Garden", "Savage Garden", 4);
        
        Parallel_maps.addEdge("Savage Garden", "San Giorgio Maggiore", 6);
        Parallel_maps.addEdge("Savage Garden", "Cafe Deux Magots", 5);
        
        Parallel_maps.addEdge("Cafe Deux Magots", "Morioh Grand Hotel", 6);
        Parallel_maps.addEdge("Cafe Deux Magots", "Polnareff Land", 2);
        
        Alternate_maps.addEdge("Town Hall", "Morioh Grand Hotel",2);
        Alternate_maps.addEdge("Town Hall", "Green Dolphin Street Prison",3);
        Alternate_maps.addEdge("Town Hall", "Passione Restaurant",7);
        
        Alternate_maps.addEdge("Morioh Grand Hotel", "San Giorgio Maggiore",3);
        Alternate_maps.addEdge("Morioh Grand Hotel", "Joestar Mansion",4);
        Alternate_maps.addEdge("Morioh Grand Hotel", "Green Dolphin Street Prison",2);
        
        Alternate_maps.addEdge("San Giorgio Maggiore", "Savage Garden",6);
        
        Alternate_maps.addEdge("Savage Garden", "Vineyard", 4);
        
        Alternate_maps.addEdge("Vineyard", "Cafe Deux Magots", 4);
        
        Alternate_maps.addEdge("Cafe Deux Magots", "DIO's Mansion", 1);
        Alternate_maps.addEdge("Cafe Deux Magots", "Passione Restaurant", 4);
         
        Alternate_maps.addEdge("DIO's Mansion", "Polnareff Land",2);
        Alternate_maps.addEdge("DIO's Mansion", "Passione Restaurant",2);
        
        Alternate_maps.addEdge("Polnareff Land", "Jade Garden",2);
        Alternate_maps.addEdge("Polnareff Land", "Angelo Rock",2);
        
        Alternate_maps.addEdge("Angelo Rock", "Jade Garden",1);
        Alternate_maps.addEdge("Angelo Rock", "Passione Restaurant",6);
        
        Alternate_maps.addEdge("Trattoria Trussardi", "Passione Restaurant",1);
        Alternate_maps.addEdge("Trattoria Trussardi", "Green Dolphin Street Prison",4);
        Alternate_maps.addEdge("Trattoria Trussardi", "Joestar Mansion",5);
    }
    */

}
/* 
    public Graph<String, Integer> getMaps() {
        return maps;
    }
*/
}
