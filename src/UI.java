import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UI {
    private static final UserData userData = new UserData();
    private static final Scanner scanner = new Scanner(System.in);
    private static final DirectionMap flightDirectionMap = new DirectionMap();
    private static final RouteFinder routeFinder = new RouteFinder(flightDirectionMap);
    static String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    static String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";

    // Allows and returns only a valid number from the user's input
    private static int inputNumber(Scanner scanner) {
        int choice;
        while (true) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                System.out.println();
                return choice;
            } else {
                System.out.println("That's not a valid number. Try again.");
                scanner.next();
            }
        }
    }


    // Method to pretty print a specific path
    public static void printPaths(List<List<Path>> allPaths) {
        int routeIndex = 1;
        for (List<Path> path : allPaths) {
            System.out.print("["+routeIndex+"] ");
            routeIndex++;
            for (int i = 0; i < path.size(); i++) {
                if (i==0) System.out.print(path.get(i).from);
                System.out.print(" -> ");
                System.out.print(path.get(i).to);
            }
            System.out.println();
            System.out.print("Total distance: " + path.stream().mapToDouble(e -> e.distance).sum() + " km");
            System.out.print(" | ");
            System.out.println("Total fare: $" + path.stream().mapToDouble(e -> e.price).sum());
            System.out.println();
        }
    }

    // All UI elements to use before login
    public static void preLogin() {
        System.out.println("\n--- Flight Management System ---");
        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = inputNumber(scanner);

            if (choice == 3) {
                scanner.close();
                return;
            } else {
                if (choice == 1) {
                    String email, password;
                    while (true) {
                        System.out.print("Enter email: ");
                        email = scanner.nextLine();
                        System.out.println();
                        if (!Pattern.matches(emailRegex, email)) {
                            System.out.println("Invalid email format. Please enter a valid email.");
                        } else if(userData.getUser(email) != null) {
                            System.out.println("Email already exists");
                        } else break;
                    }
                    while (true) {
                        System.out.print("Create password: ");
                        password = scanner.nextLine();
                        System.out.println();
                        if (!Pattern.matches(passwordRegex, password)) {
                            System.out.println("Password too easy.");
                        } else break;
                    }
                    userData.register(email, password);
                    postLogin(email, scanner);
                } else if (choice == 2) {
                    String email, password;
                    while (true) {
                        System.out.print("Enter email: ");
                        email = scanner.nextLine();
                        System.out.println();
                        if (!Pattern.matches(emailRegex, email)) {
                            System.out.println("Invalid email format. Please enter a valid email.");
                        } else if(userData.getUser(email) == null) {
                            System.out.println("Email doesnt exist");
                        } else break;
                    }
                    System.out.print("Enter password: ");
                    password = scanner.nextLine();
                    System.out.println();
                    if (userData.login(email, password)) {
                        postLogin(email, scanner);
                    } else {
                        System.out.println("Invalid credentials. Try again.");
                    }
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }


    // All UI elements to use before login
    public static void postLogin(String email, Scanner scanner) {
        System.out.println("Welcome !");

        boolean loggedIn = true;
        while (loggedIn) {
            User currentUser = userData.getUser(email);
            System.out.println("1. Search and Book Best Route");
            System.out.println("2. View Booking History");
            System.out.println("3. Cancel booking");
            System.out.println("4. View Last 3 Actions");
            System.out.println("5. Logout");

            System.out.print("Enter your choice: ");
            int choice = inputNumber(scanner);

            switch (choice) {
                case 1:
                    System.out.println("Available Cities: " + flightDirectionMap.getCities());
                    String source, destination;
                    do {
                        System.out.print("Enter source city: ");
                        source = scanner.nextLine();
                        System.out.println();
                    } while (!flightDirectionMap.getCities().contains(source));
                    do {
                        System.out.print("Enter destination city: ");
                        destination = scanner.nextLine();
                        System.out.println();
                    } while (!flightDirectionMap.getCities().contains(destination));

                    List<List<Path>> allPaths = routeFinder.findAllPaths(source, destination);
                    if (allPaths.isEmpty()) {
                        System.out.println("No route found!");
                    } else {
                        System.out.println("Possible routes: ");
                        printPaths(allPaths);
                        boolean continueWithBooking = allPaths.size()==1;
                        while (!continueWithBooking) {
                            System.out.println("1. Sort the choices based on distances");
                            System.out.println("2. Sort the choices based on prices");
                            System.out.println("3. Continue with booking");
                            System.out.print("Enter your choice: ");
                            int routeChoice = inputNumber(scanner);
                            switch (routeChoice) {
                                case 1:
                                    allPaths = Merge.distanceMergeSort(allPaths);
                                    printPaths(allPaths);
                                    break;
                                case 2:
                                    allPaths = Merge.priceMergeSort(allPaths);
                                    printPaths(allPaths);
                                    break;
                                case 3:
                                    continueWithBooking = true;
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                                    break;
                            }
                        }
                        while (true) {
                            int routeChoice = 0;
                            if (allPaths.size()>1) {
                                System.out.print("Insert route number between 1 and " + allPaths.size() + ": ");
                                routeChoice = inputNumber(scanner) - 1;
                            }

                            if (routeChoice >= 0 && routeChoice < allPaths.size()) {
                                List<Path> route = allPaths.get(routeChoice);
                                for (int i=0; i<route.size(); i++) {
                                    System.out.println("["+(i+1)+"] ");
                                    Path currentMiniPath = route.get(i);
                                    System.out.println("SeatMap for " + currentMiniPath);
                                    currentMiniPath.seatMap.printSeatMap();
                                    char bookCol;
                                    int bookRow;
                                    while (true) {
                                        System.out.print("Enter seat column (A-F): ");
                                        bookCol = Character.toUpperCase(scanner.next().charAt(0));
                                        System.out.println();
                                        if (currentMiniPath.seatMap.isColumnNotFull(bookCol)) break;
                                        else System.out.println("Column full or not valid.");
                                    }
                                    while (true) {
                                        System.out.print("Enter seat row (1-9): ");
                                        bookRow = inputNumber(scanner)-1;
                                        if (currentMiniPath.seatMap.isSeatAvailable(bookCol, bookRow)) break;
                                        else System.out.println("Seat not available.");
                                    }
                                    if (currentMiniPath.seatMap.bookSeat(bookCol, bookRow)) {
                                        currentUser.recentActions.addAction("Booked seat " + bookRow + bookCol + " for " + currentMiniPath);
                                        currentUser.bookingPath.add(currentMiniPath);
                                        ArrayList<Integer> seat = new ArrayList<>();
                                        seat.add(bookCol-'A');
                                        seat.add(bookRow);
                                        currentUser.bookingSeat.add(seat);
                                    }
                                }
                                break;
                            } else {
                                System.out.println("Invalid choice");
                            }
                        }
                    }
                    break;

                case 2:
                    if (currentUser.bookingPath.isEmpty()) {
                        System.out.println("No booking history.");
                    } else {
                        for (Path path : currentUser.bookingPath) {
                            System.out.println(path);
                        }
                    }
                    break;

                case 3:
                    if (currentUser.bookingPath.isEmpty()) {
                        System.out.println("No booking history.");
                    } else {
                        int count = 1;
                        for (Path path : currentUser.bookingPath) {
                            System.out.println(count + ": " + path);
                            count++;
                        }
                        while (true) {
                            System.out.println("Enter a value between 1 and " + (currentUser.bookingPath.size()));
                            int cancelChoice = inputNumber(scanner) - 1;
                            if (cancelChoice>=0 && cancelChoice<=currentUser.bookingPath.size()-1) {
                                Path path = currentUser.bookingPath.get(cancelChoice);
                                ArrayList<Integer> seat = currentUser.bookingSeat.get(cancelChoice);
                                currentUser.recentActions.addAction("Cancelled seat " + seat.getLast() + seat.getFirst() + " for " + path);
                                currentUser.recentActions.addAction("Cancelled trip " + path);
                                path.seatMap.cancelBooking((char) ('A'+seat.getFirst()), seat.getLast());
                                currentUser.bookingPath.remove(cancelChoice);
                                currentUser.bookingSeat.remove(cancelChoice);
                                break;
                            } else {
                                System.out.println("Invalid index");
                            }
                        }
                    }
                    break;

                case 4:
                    currentUser.recentActions.displayRecentActions();
                    break;

                case 5:
                    System.out.println("Logged out");
                    loggedIn = false;
                    break;


                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
