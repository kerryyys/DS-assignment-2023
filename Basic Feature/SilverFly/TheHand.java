
import java.util.ArrayList;
import java.util.List;


class Edges {
    String vertex1;
    String vertex2;
    int weight;

    public Edges(String vertex1, String vertex2, int weight) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
    }
}

public class TheHand extends Player{
    public static List<Edges> assignGraph(String graphStr) {
        List<Edges> edges = new ArrayList<>();
        String[] edgeList = graphStr.split(",");
        for (String edgeStr : edgeList) {
            String[] parts = edgeStr.split(":"); //split into vertices and edge
            String[] vertex = parts[0].split("-"); //split vertices
            String vertex1 = vertex[0];
            String vertex2 = vertex[1];
            int weight = Integer.parseInt(parts[1]); //parts 1=edge as weight
            edges.add(new Edges(vertex1, vertex2, weight));
        }
        return edges;
    }

    public static List<Edges> prim(String graphStr, String rootVertex) {
        List<Edges> edges = assignGraph(graphStr);
        List<Edges> prims = new ArrayList<>();
        List<String> visited = new ArrayList<>();
        visited.add(rootVertex);

        while (visited.size() < getTotalVertices(edges)) {
            int minWeight = Integer.MAX_VALUE;
            Edges minEdge = null;

            for (Edges edge : edges) {
                boolean isConnectedToVisited = visited.contains(edge.vertex1) ^ visited.contains(edge.vertex2);//xor to see connected to either vertex
                if (isConnectedToVisited && edge.weight < minWeight) {
                    minWeight = edge.weight;
                    minEdge = edge;
                }
            }

            if (minEdge != null) {   
                visited.add(visited.contains(minEdge.vertex1) ? minEdge.vertex2 : minEdge.vertex1);//checks which vertex is already visisted or not
                prims.add(minEdge);
            }
        }

        return prims;
    }

    private static int getTotalVertices(List<Edges> edges) {
        ArrayList<String> vertices = new ArrayList<>();
        for (Edges edge : edges) {
            if (!vertices.contains(edge.vertex1)) {
                vertices.add(edge.vertex1);
            }
            if (!vertices.contains(edge.vertex2)) {
                vertices.add(edge.vertex2);
            }
        }
        return vertices.size();
    }

    public void display(Graph<String,Integer> map) {
        Graph<String, Integer> a = Player.mainMap1;
        Graph<String, Integer> b = Player.mainMap2;
        Graph<String, Integer> c = Player.mainMap3;
        
        if(map.equals(mainMap1)){
            
        String graphStr = "Town Hall-Morioh Grand Hotel:5,Town Hall-Jade Garden:5,Town Hall-Cafe Deux Magots:4,"
                + "Morioh Grand Hotel-Trattoria Trussardi:6,Morioh Grand Hotel-Jade Garden:3,"
                + "Jade Garden-San Giorgio Maggiore:2,Jade Garden-Joestar Mansion:2,"
                + "Cafe Deux Magots-Jade Garden:3,Cafe Deux Magots-Savage Garden:4,Cafe Deux Magots-Polnareff Land:4,"
                + "Savage Garden-Polnareff Land:6,Savage Garden-Joestar Mansion:4,Savage Garden-Vineyard:8,"
                + "Vineyard-Joestar Mansion:3,Vineyard-Libeccio:6,Vineyard-DIO's Mansion:3,"
                + "Libeccio-Joestar Mansion:6,Libeccio-DIO's Mansion:2,Libeccio-Green Dolphin Street Prison:3,Libeccio-San Giorgio Maggiore:4,"
                + "Trattoria Trussardi-San Giorgio Maggiore:3,Trattoria Trussardi-Green Dolphin Street Prison:6,"
                + "Angelo Rock-DIO's Mansion:3,Angelo Rock-Green Dolphin Street Prison:2";

        String rootVertex = "Town Hall"; // Set the desired root vertex
        List<Edges> minimumSpanningTree = prim(graphStr, rootVertex);
        System.out.println("Unnecessary water connections to be removed :");
        
int count = 1;
        int totalLength = 0;
        for (Edges edge : minimumSpanningTree) {
            System.out.println(count + ". " + edge.vertex1 + "--" + edge.vertex2 + "(" + edge.weight +"km)");
            totalLength += edge.weight;
            count++;
        }

        System.out.println("Total Length: " + totalLength +"km");
    }
        else if(map.equals(mainMap2)){
            
            String graphStr = "Town Hall-Vineyard:3,Town Hall-Libeccio:2,Town Hall-Cafe Deux Magots:4,Town Hall-Trattoria Trussardi:6,"
                              +"Morioh Grand Hotel-Cafe Deux Magots:6,Morioh Grand Hotel-Joestar Mansion:4,"
                              +"Jade Garden-Cafe Deux Magots:3,Jade Garden-Savage Garden:4,Jade Garden-Joestar Mansion:3,"
                              +"Cafe Deux Magots-Polnareff Land:2,Cafe Deux Magots-Savage Garden:5,"
                              +"Trattoria Trussardi-DIO's Mansion:4,Trattoria Trussardi-Angelo Rock:3,Trattoria Trussardi-Joestar Mansion:5,"
                              +"Green Dolphin Street Prison-DIO's Mansion:6,Green Dolphin Street Prison-Angelo Rock:8,"
                              +"Libeccio-Vineyard:3,"
                              +"Angelo Rock-DIO's Mansion:1,"
                              +"Savage Garden-San Giorgio Maggiore:6,"
                              +"Joestar Mansion-San Giorgio Maggiore:5";
            
            String rootVertex = "Town Hall"; // Set the desired root vertex
        List<Edges> minimumSpanningTree = prim(graphStr, rootVertex);
        System.out.println("Unnecessary water connections to be removed :");
        
int count = 1;
        int totalLength = 0;
        for (Edges edge : minimumSpanningTree) {
            System.out.println(count + ". " + edge.vertex1 + "--" + edge.vertex2 + "(" + edge.weight +"km)");
            totalLength += edge.weight;
            count++;
        }

        System.out.println("Total Length: " + totalLength +"km");
    }
        else{
            String graphStr = "Town Hall-Morioh Grand Hotel:2,Town Hall-Green Dolphin Street Prison:3,Town Hall-Libeccio:7,"
                              +"Morioh Grand Hotel-Green Dolphin Street Prison:2,Morioh Grand Hotel-San Giorgio Maggiore:3,Morioh Grand Hotel-Joestar Mansion:4,"
                              +"Jade Garden-Angelo Rock:1,Jade Garden-Polnareff Land:2,"
                              +"Cafe Deux Magots-Libeccio:4,Cafe Deux Magots-DIO's Mansion:1,Cafe Deux Magots-Vineyard:4,"
                              +"Trattoria Trussardi-Joestar Mansion:5,Trattoria Trussardi-Green Dolphin Street Prison:4,Trattoria Trussardi-Libeccio:1,"
                              +"Angelo Rock-Libeccio:6,Angelo Rock-Polnareff Land:2,"
                              +"Savage Garden-San Giorgio Maggiore:6,Savage Garden-Vineyard:4,"
                              +"DIO's Mansion-Libeccio:2,DIO's Mansion-Polnareff Land:2,";
            
            String rootVertex = "Town Hall"; // Set the desired root vertex
        List<Edges> minimumSpanningTree = prim(graphStr, rootVertex);
        System.out.println("Unnecessary water connections to be removed :");
        
int count = 1;
        int totalLength = 0;
        for (Edges edge : minimumSpanningTree) {
            System.out.println(count + ". " + edge.vertex1 + "--" + edge.vertex2 + "(" + edge.weight +"km)");
            totalLength += edge.weight;
            count++;
        }

        System.out.println("Total Length: " + totalLength +"km");
        }
   }
 }


    
