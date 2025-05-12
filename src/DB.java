import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DB {
    private static final String URL = "jdbc:mysql://localhost:3306/fbs";
    private static final String USER = "fbs";
    private static final String PASSWORD = "SimplePassword@1234";

    // Method to get a connection
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }

        // Return the connection
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
