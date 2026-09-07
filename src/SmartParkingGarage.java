import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class SmartParkingGarage {

    // Collections
    static Queue<String> waitingQueue = new LinkedList<>();
    static Stack<String> parkingStack = new Stack<>();

    // Constants
    static final int MAX_CAPACITY = 10;

    // Statistics
    static int totalParkedToday = 0;
     static int totalDepartedToday = 0;

    // Scanner
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            displayMenu();

            choice = readMenuChoice();

            switch (choice) {

                case 1:
                    addVehicle();
                    break;

                case 2:
                    parkVehicle();
                    break;

                case 3:
                    removeVehicle();
                    break;

                case 4:
                    viewNextWaitingVehicle();
                    break;

                case 5:
                    viewLastParkedVehicle();
                    break;

                case 6:
                    displayWaitingQueue();
                    break;

                case 7:
                    displayParkedVehicles();
                    break;

                case 8:
                    searchVehicle();
                    break;

                case 9:
                    displayStatistics();
                    break;

                case 10:
                    clearWaitingQueue();
                    break;

                case 11:
                    clearParkingGarage();
                    break;

                case 12:
                    resetSystem();
                    break;

                case 13:
                    System.out.println("\nThank you for using Smart Parking Garage.");
                    break;

                default:
                    System.out.println("Invalid menu choice!");
            }

        } while (choice != 13);

        input.close();
    }

    // =====================================
    // Display Menu
    // =====================================

    public static void displayMenu() {

        System.out.println("\n========= Smart Parking Garage =========");
        System.out.println("1. Add Vehicle to Waiting Queue");
        System.out.println("2. Park Next Vehicle");
        System.out.println("3. Remove Parked Vehicle");
        System.out.println("4. View Next Waiting Vehicle");
        System.out.println("5. View Last Parked Vehicle");
        System.out.println("6. Display Waiting Queue");
        System.out.println("7. Display Parked Vehicles");
        System.out.println("8. Search Vehicle");
        System.out.println("9. Display Garage Statistics");
        System.out.println("10. Clear Waiting Queue");
        System.out.println("11. Clear Parking Garage");
        System.out.println("12. Reset Entire System");
        System.out.println("13. Exit");
        System.out.print("Enter your choice: ");
    }

    // =====================================
    // Add Vehicle
    // =====================================

    public static void addVehicle() {

        System.out.print("Enter vehicle number: ");
        String vehicle = input.nextLine().trim();

        if (vehicle.isEmpty()) {
            System.out.println("Vehicle number cannot be blank.");
            return;
        }

        if (isDuplicateVehicle(vehicle)) {
            System.out.println("Error: Vehicle already exists.");
            return;
        }

        waitingQueue.offer(vehicle);
        System.out.println("Vehicle added to waiting queue.");
    }

    // =====================================
    // Park Vehicle
    // =====================================

    public static void parkVehicle() {

        if (waitingQueue.isEmpty()) {
            System.out.println("No vehicles are waiting.");
            return;
        }

        if (parkingStack.size() >= MAX_CAPACITY) {
            System.out.println("Garage is full.");
            return;
        }

        String vehicle = waitingQueue.poll();
        parkingStack.push(vehicle);
        totalParkedToday++;

        System.out.println(vehicle + " parked successfully.");
    }

    // =====================================
    // Remove Vehicle
    // =====================================

    public static void removeVehicle() {

        if (parkingStack.isEmpty()) {
            System.out.println("Parking garage is empty.");
            return;
        }

        String removed = parkingStack.pop();
        totalDepartedToday++;

        System.out.println("Vehicle removed: " + removed);

        // Automatically park next waiting vehicle
        if (!waitingQueue.isEmpty() && parkingStack.size() < MAX_CAPACITY) {

            String next = waitingQueue.poll();
            parkingStack.push(next);
            totalParkedToday++;

            System.out.println("Automatically parked: " + next);
        }
    }

    // =====================================
    // View Next Waiting Vehicle
    // =====================================

    public static void viewNextWaitingVehicle() {

        if (waitingQueue.isEmpty()) {
            System.out.println("No vehicles are waiting.");
        } else {
            System.out.println("Next waiting vehicle: " + waitingQueue.peek());
        }
    }

    // =====================================
    // View Last Parked Vehicle
    // =====================================

    public static void viewLastParkedVehicle() {

        if (parkingStack.isEmpty()) {
            System.out.println("Parking garage is empty.");
        } else {
            System.out.println("Last parked vehicle: " + parkingStack.peek());
        }
    }

    // =====================================
    // Display Waiting Queue
    // =====================================

    public static void displayWaitingQueue() {

        if (waitingQueue.isEmpty()) {
            System.out.println("No vehicles are waiting.");
            return;
        }

        System.out.println("\nWaiting Vehicles:");

        for (String vehicle : waitingQueue) {
            System.out.println(vehicle);
        }

        System.out.println("Total Waiting Vehicles: " + waitingQueue.size());
    }

    // =====================================
    // Display Parked Vehicles
    // =====================================

    public static void displayParkedVehicles() {

        if (parkingStack.isEmpty()) {
            System.out.println("Parking garage is empty.");
        } else {

            System.out.println("\nParked Vehicles (Newest -> Oldest)");

            for (int i = parkingStack.size() - 1; i >= 0; i--) {
                System.out.println(parkingStack.get(i));
            }
        }

        System.out.println("\nGarage Capacity : " + MAX_CAPACITY);
        System.out.println("Occupied Spaces : " + parkingStack.size());
        System.out.println("Available Spaces : " + (MAX_CAPACITY - parkingStack.size()));
    }

    // =====================================
    // Search Vehicle
    // =====================================

    public static void searchVehicle() {

        if (waitingQueue.isEmpty() && parkingStack.isEmpty()) {
            System.out.println("System is empty.");
            return;
        }

        System.out.print("Enter vehicle number: ");
        String vehicle = input.nextLine().trim();

        if (waitingQueue.contains(vehicle)) {
            System.out.println(vehicle + " is waiting in queue.");
        } else if (parkingStack.contains(vehicle)) {
            System.out.println(vehicle + " is parked.");
        } else {
            System.out.println("Vehicle not found.");
        }
    }

    // =====================================
    // Statistics
    // =====================================

    public static void displayStatistics() {

        int parked = parkingStack.size();
        int waiting = waitingQueue.size();
        int available = MAX_CAPACITY - parked;
        int processed = totalParkedToday + totalDepartedToday;

        double occupancy = ((double) parked / MAX_CAPACITY) * 100;

        System.out.println("\n========== Garage Statistics ==========");
        System.out.println("Maximum Capacity : " + MAX_CAPACITY);
        System.out.println("Currently Parked : " + parked);
        System.out.println("Waiting Vehicles : " + waiting);
        System.out.println("Available Spaces : " + available);
        System.out.println("Vehicles Parked Today : " + totalParkedToday);
        System.out.println("Vehicles Departed Today : " + totalDepartedToday);
        System.out.println("Total Vehicles Processed : " + processed);
        System.out.printf("Garage Occupancy : %.0f%%\n", occupancy);
    }

    // =====================================
    // Clear Waiting Queue
    // =====================================

    public static void clearWaitingQueue() {

        if (waitingQueue.isEmpty()) {
            System.out.println("Waiting queue is already empty.");
            return;
        }

        System.out.print("Are you sure? (Y/N): ");
        String answer = input.nextLine();

        if (answer.equalsIgnoreCase("Y")) {
            waitingQueue.clear();
            System.out.println("Waiting queue cleared.");
        } else {
            System.out.println("Operation cancelled.");
        }
    }

    // =====================================
    // Clear Parking Garage
    // =====================================

    public static void clearParkingGarage() {

        if (parkingStack.isEmpty()) {
            System.out.println("Parking garage is already empty.");
            return;
        }

        parkingStack.clear();
        System.out.println("Parking garage cleared.");
    }

    // =====================================
    // Reset System
    // =====================================

    public static void resetSystem() {

        waitingQueue.clear();
        parkingStack.clear();

        totalParkedToday = 0;
        totalDepartedToday = 0;

        System.out.println("System successfully reset.");
    }

    // =====================================
    // Duplicate Check
    // =====================================

    public static boolean isDuplicateVehicle(String vehicle) {

        return waitingQueue.contains(vehicle) || parkingStack.contains(vehicle);
    }
}