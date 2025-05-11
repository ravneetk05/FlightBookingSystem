import java.util.HashMap;

public class SeatAllocator {

    private boolean[][] seatsEmptyData;
    private HashMap<Integer, Integer> columnMetaData;

    private static final int ROWS = 9;  // 9 Rows (1-9)
    private static final int COLUMNS = 6; // 6 Columns (A to F)

    // Represents all possible seats with their index based on column for a faster availability check
    public SeatAllocator() {
        seatsEmptyData = new boolean[COLUMNS][ROWS];
        columnMetaData = new HashMap<>();

        // Initialize all seats to true for available
        for (int i = 0; i < COLUMNS; i++) {
            columnMetaData.put(i, ROWS);
            for (int j = 0; j < ROWS; j++) {
                seatsEmptyData[i][j] = true;
            }
        }
    }

    // Book a seat, returns true if the seat was successfully booked
    public boolean bookSeat(char column, int row) {
        if (isSeatValid(column, row)) {
            if (isSeatAvailable(column, row)) {
                seatsEmptyData[column - 'A'][row] = false;
                return true;
            } else {
                System.out.println("Seat " + column+row  + " is already booked.");
                return false;
            }
        } else {
            System.out.println("Invalid seat position.");
            return false;
        }
    }

    // Cancel a booking, returns true if the seat was successfully canceled
    public void cancelBooking(char column, int row) {
        if (isSeatValid(column, row)) {
            if (!isSeatAvailable(column, row)) {
                seatsEmptyData[column - 'A'][row] = true;
            } else {
                System.out.println("Seat " + row + column + " is not booked.");
            }
        } else {
            System.out.println("Invalid seat position.");
        }
    }

    // Check if a seat is available
    public boolean isSeatValid(char column, int row) {
        return row >= 0 && row <= ROWS-1 && column >= 'A' && column <= 'F';
    }

    // Check if a seat is available
    public boolean isSeatAvailable(char column, int row) {
        return isSeatValid(column, row) && seatsEmptyData[column - 'A'][row];
    }

    // Check if the place column is free
    public boolean isColumnNotFull(char column) {
        return columnMetaData.getOrDefault(column - 'A', 0) > 0;
    }

    // Print the current seat map
    public void printSeatMap() {
        System.out.println("Seat Map (* means booked seats):");
        System.out.print("   ");
        for (int i = 0; i < COLUMNS; i++) { System.out.print(" "+ (char)('A'+ i)); }
        System.out.println();
        for (int i = 0; i < ROWS; i++) {
            System.out.print((i+1) + " | ");
            for (int j = 0; j < COLUMNS; j++) {
                if (seatsEmptyData[j][i]) {
                    System.out.print(" ");
                } else {
                    System.out.print("*");
                }
                System.out.print(" ");
            }
            System.out.println("|");
        }
        System.out.println();
    }
}
