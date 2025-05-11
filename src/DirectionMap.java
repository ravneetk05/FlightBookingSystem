import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class DirectionMap {
    private Map<String, List<Path>> adjList = new HashMap<>();

    public DirectionMap() {
        fetchDB();
    }

    // Register a new flight between two cities using this method
    public void addFlight(String from, String to, double distance, double price) {
        adjList.putIfAbsent(from, new ArrayList<>());
        adjList.putIfAbsent(to, new ArrayList<>());
        adjList.get(from).add(new Path(from, to, distance, price));
    }

    // Get all reachable cities from a source city
    public List<Path> getNeighbors(String city) {
        return adjList.getOrDefault(city, new ArrayList<>());
    }

    // Get list of all cities in the map
    public Set<String> getCities() {
        return adjList.keySet();
    }

    // Fetch all flights from the database and add them to the map
    public void fetchDB() {
        String sql = "SELECT * FROM flights";
        while (true) {
            adjList = new HashMap<>();
            try {
                Connection conn = DB.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String from = rs.getString("from");
                    String to = rs.getString("to");
                    int price = rs.getInt("price");
                    int distance = rs.getInt("distance");

                    this.addFlight(from, to, distance, price);
                }
                break;

            } catch (Exception e) { System.out.println("Error fetching data from database."+ e.getMessage()); }
        }
    }
}

