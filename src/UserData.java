import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class UserData {
    private HashMap<String, User> users;

    // Stores all users and their data, also used for registration and login
    public UserData() {
        users = new HashMap<>();
        String sql = "SELECT * FROM users";
        while (true) {
            try (Connection conn = DB.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    users.put(email, new User(email, password));
                }
                break;

            } catch (Exception e) { System.out.println("Error fetching data from database."+ e.getMessage()); }
        }
    }

    // Register a new user
    public boolean register(String email, String password) {
        if (users.containsKey(email)) {
            return false; // email already exists
        }
        String sql = "INSERT INTO users (email, password) VALUES (?, ?)";
        try {
            Connection conn = DB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             pstmt.setString(1, email);
             pstmt.setString(2, password);
             pstmt.execute();

        } catch (Exception e) { System.out.println("Error fetching data from database."+ e.getMessage()); }
        users.put(email, new User(email, password));
        return true;
    }


    // Login user
    public boolean login(String email, String password) {
        if (!users.containsKey(email)) {
            return false;
        }
        return users.get(email).checkPassword(password);
    }


    // Get any user's data
    public User getUser(String email) {
        return users.get(email);
    }
}
