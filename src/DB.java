import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DB {
    private static final String URL = "jdbc:mysql://localhost:3306/fbs"; // Database connection URI
    private static final String USER = "fbs"; // Database username
    private static final String PASSWORD = "SimplePassword@1234"; // Database password

    // Method to get a connection
    public static Connection getConnection() throws SQLException { // Fetch database connection when called
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the MySQL JDBC driver class at runtime
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }

        // Return the connection
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
