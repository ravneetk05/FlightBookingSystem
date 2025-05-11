import java.util.*;

public class RouteFinder {
    private final DirectionMap directionMap;

    public RouteFinder(DirectionMap directionMap) {
        this.directionMap = directionMap;
    }

    // Return a list of all the paths possible from source to destination
    public List<List<Path>> findAllPaths(String source, String destination) {
        List<List<Path>> possibleRoutes = new ArrayList<>();
        LinkedList<Path> currentPath = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        dfs(source, destination, visited, currentPath, possibleRoutes);
        return possibleRoutes;
    }

    // Recursive function to check every node if it leads to destination
    private void dfs(String current, String destination, Set<String> visited, LinkedList<Path> currentPath, List<List<Path>> possibleRoutes) {
        if (current.equals(destination)) { // Reached destination, add to possible paths
            possibleRoutes.add(new ArrayList<>(currentPath));
            return;
        }
        visited.add(current); // Add the current location to check and prevent loops
        for (Path path : directionMap.getNeighbors(current)) {
            if (!visited.contains(path.to)) {
                currentPath.add(path);
                dfs(path.to, destination, visited, currentPath, possibleRoutes);
                currentPath.removeLast();
            }
        }
        visited.remove(current);
    }
}
