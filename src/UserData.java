import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class UserData {
    private final HashMap<String, User> users;

    // Stores all users and their data, also used for registration and login
    public UserData() {
        users = new HashMap<>();
        String sql = "SELECT * FROM users"; // Query to fetch all users from the database
        while (true) {
            try (Connection conn = DB.getConnection(); // Fetch DB connection
                 PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) { // Execute query and store result in ResultSet

                while (rs.next()) {
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    users.put(email, new User(email, password)); // Add user to the map with their data
                }
                break;

            } catch (Exception e) { System.out.println("Error fetching data from database."+ e.getMessage()); }
        }
    }

    // Register a new user
    public void register(String email, String password) {
        if (users.containsKey(email)) {
            return; // email already exists
        }
        String sql = "INSERT INTO users (email, password) VALUES (?, ?)"; // Query to insert a new user into the database
        try {
            Connection conn = DB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             pstmt.setString(1, email); // Set email for the first parameter in the query
             pstmt.setString(2, password); // Set password for the first parameter in the query
             pstmt.execute(); // Execute the query

        } catch (Exception e) { System.out.println("Error fetching data from database."+ e.getMessage()); }
        users.put(email, new User(email, password)); // Add user to the map with their data
    }


    // Check if user credentials are correct
    public boolean checkLogin(String email, String password) {
        if (!users.containsKey(email)) {
            return false;
        }
        return users.get(email).checkPassword(password); // Check if the entered password matches the actual password in the database
    }


    // Get any user's data
    public User getUser(String email) {
        return users.get(email);
    }
}
