public class Path {
    String from;
    String to;
    double distance;
    double price;
    SeatAllocator seatMap;

    // Represents a path between every 2 places possible to travel
    public Path(String from, String to, double distance, double price) {
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.price = price;
        this.seatMap = new SeatAllocator();
    }

    // Overridden function to pretty-print path data
    public String toString() {
        return from + " -> " + to + " (" + distance + "km $" + price +")";
    }
}