package JOJOLands.JOJO;

public class JOJOMaps {

    protected Graph<String, Integer> defaultMap = new Graph<>();
    protected Graph<String, Integer> parallelMap = new Graph<>();
    protected Graph<String, Integer> alternateMap = new Graph<>();
    protected String[] location;

    // The World
    public JOJOMaps() {
        location = new String[] { "Town Hall", "Morioh Grand Hotel", "Jade Garden", "Cafe Deux Magots",
                "Trattoria Trussardi", "San Giorgio Maggiore", "Joestar Mansion", "Polnareff Land", "Savage Garden",
                "Green Dolphin Street Prison", "Libeccio", "Angelo Rock", "DIO's Mansion", "Vineyard" };

        defaultMap = initializeDefaultMap();

        parallelMap = initializeParallelMap();

        alternateMap = initializeAlternateMap();
    }

    private Graph<String, Integer> initializeDefaultMap() {
        Graph<String, Integer> maps = new Graph<>();
        for (String i : location)
            maps.addVertex(i);

        // add edge to connect each location, in km
        maps.addEdge("Town Hall", "Morioh Grand Hotel", 5);
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

        return maps;
    }

    private Graph<String, Integer> initializeParallelMap() {
        Graph<String, Integer> maps = new Graph<>();
        for (String i : location)
            maps.addVertex(i);

        maps.addEdge("Town Hall", "Vineyard", 3);
        maps.addEdge("Town Hall", "Libeccio", 2);
        maps.addEdge("Town Hall", "Cafe Deux Magots", 4);
        maps.addEdge("Town Hall", "Trattoria Trussardi", 6);

        maps.addEdge("Morioh Grand Hotel", "Cafe Deux Magots", 6);
        maps.addEdge("Morioh Grand Hotel", "Joestar Mansion", 4);

        maps.addEdge("Jade Garden", "Cafe Deux Magots", 3);
        maps.addEdge("Jade Garden", "Savage Garden", 4);
        maps.addEdge("Jade Garden", "Joestar Mansion", 3);

        maps.addEdge("Cafe Deux Magots", "Polnareff Land", 2);
        maps.addEdge("Cafe Deux Magots", "Savage Garden", 5);

        maps.addEdge("Trattoria Trussardi", "DIO's Mansion", 4);
        maps.addEdge("Trattoria Trussardi", "Angelo Rock", 3);
        maps.addEdge("Trattoria Trussardi", "Joestar Mansion", 5);

        maps.addEdge("Green Dolphin Street Prison", "DIO's Mansion", 6);
        maps.addEdge("Green Dolphin Street Prison", "Angelo Rock", 8);

        maps.addEdge("Libeccio", "Vineyard", 3);

        maps.addEdge("Angelo Rock", "DIO's Mansion", 1);

        maps.addEdge("Savage Garden", "San Giorgio Maggiore", 6);

        maps.addEdge("Joestar Mansion", "San Giorgio Maggiore", 5);

        return maps;
    }

    // Passione Restaurant -> Libeccio
    private Graph<String, Integer> initializeAlternateMap() {
        Graph<String, Integer> maps = new Graph<>();
        for (String i : location)
            maps.addVertex(i);

        maps.addEdge("Town Hall", "Morioh Grand Hotel", 2);
        maps.addEdge("Town Hall", "Green Dolphin Street Prison", 3);
        maps.addEdge("Town Hall", "Libeccio", 7);

        maps.addEdge("Morioh Grand Hotel", "Green Dolphin Street Prison", 2);
        maps.addEdge("Morioh Grand Hotel", "San Giorgio Maggiore", 3);
        maps.addEdge("Morioh Grand Hotel", "Joestar Mansion", 4);

        maps.addEdge("Jade Garden", "Angelo Rock", 1);
        maps.addEdge("Jade Garden", "Polnareff Land", 2);

        maps.addEdge("Cafe Deux Magots", "Libeccio", 4);
        maps.addEdge("Cafe Deux Magots", "DIO's Mansion", 1);
        maps.addEdge("Cafe Deux Magots", "Vineyard", 4);

        maps.addEdge("Trattoria Trussardi", "Joestar Mansion", 5);
        maps.addEdge("Trattoria Trussardi", "Green Dolphin Street Prison", 4);
        maps.addEdge("Trattoria Trussardi", "Libeccio", 1);

        maps.addEdge("Angelo Rock", "Libeccio", 6);
        maps.addEdge("Angelo Rock", "Polnareff Land", 2);

        maps.addEdge("Savage Garden", "San Giorgio Maggiore", 6);
        maps.addEdge("Savage Garden", "Vineyard", 4);

        maps.addEdge("DIO's Mansion", "Libeccio", 2);
        maps.addEdge("DIO's Mansion", "Polnareff Land", 2);

        return maps;
    }

    public Graph<String, Integer> getMapByName(String mapType) {
        if (mapType.equals("Default Map")) {
            return defaultMap;
        } else if (mapType.equals("Parallel Map")) {
            return parallelMap;
        } else {
            return alternateMap;
        }
    }
}