import java.util.LinkedList;
import java.util.Queue;

public class RecentActions {
    private Queue<String> actionQueue;
    private int maxSize;

    // Constructor with a specified maximum size for recent searches
    public RecentActions(int maxSize) {
        this.maxSize = maxSize;
        this.actionQueue = new LinkedList<>();
    }

    // Add a new action to the queue
    public void addAction(String term) {
        if (actionQueue.size() >= maxSize) {
            actionQueue.poll(); // Remove the oldest search if the queue is full
        }
        actionQueue.offer(term);
    }

    // Display all recent actions
    public void displayRecentActions() {
        if (actionQueue.isEmpty()) {
            System.out.println("No recent actions.");
        } else {
            System.out.println("Recent Actions:");
            for (String search : actionQueue) {
                System.out.println(search);
            }
        }
    }
}
