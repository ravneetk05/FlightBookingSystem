import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class DirectionMap {
    private Map<String, List<Path>> adjList = new HashMap<>();

    public DirectionMap() { // Constructor for map
        fetchDB(); // Fetch known flights from database and add them to the map
        System.out.println("Loaded " + adjList.size() + " flights.");
        System.out.println("Cities: " + adjList.keySet());
        System.out.println();
        System.out.println("Flights between cities:");
        for (String city : adjList.keySet()) {
            for (Path flight : adjList.get(city)) {
                System.out.println(flight);
            }
        }
    }

    // Register a new flight between two cities using this method
    public void addFlight(String from, String to, double distance, double price) {
        adjList.putIfAbsent(from, new ArrayList<>());
        adjList.putIfAbsent(to, new ArrayList<>());
        adjList.get(from).add(new Path(from, to, distance, price));
    }

    // Get all reachable cities from a source city
    public List<Path> getNeighbors(String city) {
        return adjList.getOrDefault(city, new ArrayList<>()); // return city's neighbors or return empty list if not found
    }

    // Get a set of all cities in the map
    public Set<String> getCities() {
        return adjList.keySet(); // return keys of the map as a set of cities
    }

    // Fetch all flights from the database and add them to the map
    public void fetchDB() {
        String sql = "SELECT * FROM flights"; // Query to fetch all flights from the database
        while (true) {
            adjList = new HashMap<>(); // Reset the old map to avoid duplicates
            try {
                Connection conn = DB.getConnection(); // Fetch DB connection
                PreparedStatement pstmt = conn.prepareStatement(sql); // Prepare statement to fetch data from database
                ResultSet rs = pstmt.executeQuery(); // Execute query and store result in ResultSet
                while (rs.next()) { // Iterate through all results and add them to the map
                    String from = rs.getString("from");
                    String to = rs.getString("to");
                    int price = rs.getInt("price");
                    int distance = rs.getInt("distance");

                    this.addFlight(from, to, distance, price);
                }
                break;

            } catch (Exception e) { System.out.println("Error fetching data from database."+ e.getMessage()); } // Print DB exceptions
        }
    }
}

