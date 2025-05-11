import java.util.ArrayList;
import java.util.List;

public class Merge {
    private static double getTotalDistance(List<Path> path) {
        return path.stream().mapToDouble(e -> e.distance).sum();
    }


    // Merge sort algorithm for sorting paths based on distance
    public static List<List<Path>> distanceMergeSort(List<List<Path>> paths) {
        if (paths.size() <= 1) {
            return paths;
        }
        int mid = paths.size() / 2;
        List<List<Path>> left = distanceMergeSort(new ArrayList<>(paths.subList(0, mid)));
        List<List<Path>> right = distanceMergeSort(new ArrayList<>(paths.subList(mid, paths.size())));

        return mergeDistance(left, right);
    }


    // Merge two sorted lists of paths based on distance and return the merged list
    private static List<List<Path>> mergeDistance(List<List<Path>> left, List<List<Path>> right) {
        List<List<Path>> merged = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (getTotalDistance(left.get(i)) <= getTotalDistance(right.get(j))) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }

        while (i < left.size()) merged.add(left.get(i++));
        while (j < right.size()) merged.add(right.get(j++));

        return merged;
    }


    // Get the total price of a list of paths
    private static double getTotalPrice(List<Path> path) {
        return path.stream().mapToDouble(e -> e.price).sum();
    }


    // Merge sort algorithm for sorting paths based on price
    public static List<List<Path>> priceMergeSort(List<List<Path>> paths) {
        if (paths.size() <= 1) {
            return paths;
        }
        int mid = paths.size() / 2;
        List<List<Path>> left = priceMergeSort(new ArrayList<>(paths.subList(0, mid)));
        List<List<Path>> right = priceMergeSort(new ArrayList<>(paths.subList(mid, paths.size())));

        return mergePrice(left, right);
    }


    // Merge two sorted lists of paths based on price and return the merged list
    private static List<List<Path>> mergePrice(List<List<Path>> left, List<List<Path>> right) {
        List<List<Path>> merged = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (getTotalPrice(left.get(i)) <= getTotalPrice(right.get(j))) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }

        while (i < left.size()) merged.add(left.get(i++));
        while (j < right.size()) merged.add(right.get(j++));

        return merged;
    }

}
