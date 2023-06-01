/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AssignmentDS;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Darwi
 */
public class TheWorld {
    
    Graph <String, Integer> MapB = new Graph<>();
    //Graph <String, Integer> MapC = new Graph<>();
    protected String [] location;
    protected String currentLocation;

    public Graph<String, Integer> getMapB() {
        return MapB;
    }


    public void setMapB(Graph<String, Integer> MapB) {
        this.MapB = MapB;
    }

    public TheWorld() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Select a Map: ");
        System.out.println("[1] Default map");
        System.out.println("[2] Parallel Map");
        System.out.println("[3] Alternate Map");
        
        int n = sc.nextInt();
        
        switch(n){
            case 1:
                break;
            case 2:
                ModifyParallelMap();
                break;
            case 3:
                ModifyAlternatelMap();
                break;
        }
    }
    
    public void ModifyParallelMap(){
        try{
        JOJOMaps MapA = new JOJOMaps();
        location = new String[] {"Town Hall", "Morioh Grand Hotel", "Jade Garden", "Cafe Deux Magots", "Trattoria Trussardi", "San Giorgio Maggiore", "Joestar Mansion", "Polnareff Land", "Savage Garden", "Green Dolphin Street Prison", "Libeccio", "Angelo Rock", "DIO’s Mansion", "Vineyard"};
        for(String i : location)
        MapB.addVertex(i);

        currentLocation = "Town Hall";
        
        MapB.addEdge("Town Hall", "Vineyard", 3);
        MapB.addEdge("Town Hall", "Libeccio", 2);
        MapB.addEdge("Town Hall", "Trattoria Trussardi", 6);
        MapB.addEdge("Town Hall", "Cafe Deux Magots", 4);
        
        MapB.addEdge("Vineyard", "Libeccio", 3);
        
        MapB.addEdge("Trattoria Trussardi", "Angelo Rock", 3);
        MapB.addEdge("Trattoria Trussardi", "DIO's Mansion", 4);
        MapB.addEdge("Trattoria Trussardi", "Joestar Mansion", 5);
        
        MapB.addEdge("Angelo Rock", "DIO's Mansion", 1);
        MapB.addEdge("Angelo Rock", "Green Dolphin Street Prison", 8);
        
        MapB.addEdge("DIO's Mansion", "Green Dolphin Street Prison", 6);
        
        MapB.addEdge("Joestar Mansion", "Morioh Grand Hotel", 4);
        MapB.addEdge("Joestar Mansion", "Jade Garden", 3);
        MapB.addEdge("Joestar Mansion", "San Giorgio Magggiore", 5);
        
        MapB.addEdge("Jade Garden", "Cafe Deux Magots", 3);
        MapB.addEdge("Jade Garden", "Savage Garden", 4);
        
        MapB.addEdge("Savage Garden", "San Giorgio Maggiore", 6);
        MapB.addEdge("Savage Garden", "Cafe Deux Magots", 5);
        
        MapB.addEdge("Cafe Deux Magots", "Morioh Grand Hotel", 6);
        MapB.addEdge("Cafe Deux Magots", "Polnareff Land", 2);
        
        
        }catch(Exception e){
            System.out.println("error");
        }
    }
    public void ModifyAlternatelMap(){
        try{
        JOJOMaps MapA = new JOJOMaps();
        location = new String[] {"Town Hall", "Morioh Grand Hotel", "Jade Garden", "Cafe Deux Magots", "Trattoria Trussardi", "San Giorgio Maggiore", "Joestar Mansion", "Polnareff Land", "Savage Garden", "Green Dolphin Street Prison", "Libeccio", "Angelo Rock", "DIO’s Mansion", "Vineyard"};
        for(String i : location)
        MapB.addVertex(i);

        currentLocation = "Town Hall";
        
        MapB.addEdge("Town Hall", "Morioh Grand Hotel",2);
        MapB.addEdge("Town Hall", "Green Dolphin Street Prison",3);
        MapB.addEdge("Town Hall", "Passione Restaurant",7);
        
        MapB.addEdge("Morioh Grand Hotel", "San Giorgio Maggiore",3);
        MapB.addEdge("Morioh Grand Hotel", "Joestar Mansion",4);
        MapB.addEdge("Morioh Grand Hotel", "Green Dolphin Street Prison",2);
        
        MapB.addEdge("San Giorgio Maggiore", "Savage Garden",6);
        
        MapB.addEdge("Savage Garden", "Vineyard", 4);
        
        MapB.addEdge("Vineyard", "Cafe Deux Magots", 4);
        
        MapB.addEdge("Cafe Deux Magots", "DIO's Mansion", 1);
        MapB.addEdge("Cafe Deux Magots", "Passione Restaurant", 4);
         
        MapB.addEdge("DIO's Mansion", "Polnareff Land",2);
        MapB.addEdge("DIO's Mansion", "Passione Restaurant",2);
        
        MapB.addEdge("Polnareff Land", "Jade Garden",2);
        MapB.addEdge("Polnareff Land", "Angelo Rock",2);
        
        MapB.addEdge("Angelo Rock", "Jade Garden",1);
        MapB.addEdge("Angelo Rock", "Passione Restaurant",6);
        
        MapB.addEdge("Trattoria Trussardi", "Passione Restaurant",1);
        MapB.addEdge("Trattoria Trussardi", "Green Dolphin Street Prison",4);
        MapB.addEdge("Trattoria Trussardi", "Joestar Mansion",5);
        
        
         }catch(Exception e){
            System.out.println("error");
        }
    }
}
