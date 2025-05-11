import java.util.ArrayList;

public class User {
    private final String email;
    private final String password;
    public RecentActions recentActions;
    public ArrayList<Path> bookingPath;
    public ArrayList<ArrayList<Integer>> bookingSeat;


    // Stores basic and history data for users to retain in memory even after logout
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.recentActions = new RecentActions(3);
        this.bookingPath = new ArrayList<>();
        this.bookingSeat = new ArrayList<>();
    }

    // Check if entered password matches the actual password
    public boolean checkPassword(String inputPassword) {
        return inputPassword.equals(this.password);
    }
}
