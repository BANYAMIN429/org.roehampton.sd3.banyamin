package com.sparebnb.view;

import com.sparebnb.controller.SystemManager;
import com.sparebnb.model.Host;
import com.sparebnb.model.Property;
import com.sparebnb.model.Guest;
import com.sparebnb.model.Booking;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static SystemManager manager = new SystemManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to SpareB&B");

        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("(1) Add a new Host");
            System.out.println("(2) View all Hosts");
            System.out.println("(3) Add a new Property");
            System.out.println("(4) View all Properties");
            System.out.println("(5) Register new Guest");
            System.out.println("(6) View all Guests");
            System.out.println("(7) Create new Booking");
            System.out.println("(8) View all Bookings");
            System.out.println("(9) Search Properties by Price"); // <-- NEW
            System.out.println("(10) Cancel a Booking"); // <-- NEW
            System.out.println("(11) Exit"); // <-- NEW (was 9)
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addHostMenu();
                    break;
                case "2":
                    viewAllHostsMenu();
                    break;
                case "3":
                    addPropertyMenu();
                    break;
                case "4":
                    viewAllPropertiesMenu();
                    break;
                case "5":
                    addGuestMenu();
                    break;
                case "6":
                    viewAllGuestsMenu();
                    break;
                case "7":
                    addBookingMenu();
                    break;
                case "8":
                    viewAllBookingsMenu();
                    break;
                case "9": // <-- NEW
                    searchPropertiesMenu();
                    break;
                case "10": // <-- NEW
                    cancelBookingMenu();
                    break;
                case "11": // <-- NEW (was 9)
                    isRunning = false;
                    System.out.println("Thank you for using SpareB&B. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // --- Host Helper Methods ---
    private static void addHostMenu() {
        System.out.println("\n--- Add New Host ---");
        System.out.print("Enter host name: ");
        String name = scanner.nextLine();
        System.out.print("Enter host email: ");
        String email = scanner.nextLine();
        String message = manager.addHost(name, email);
        System.out.println(message);
    }

    private static void viewAllHostsMenu() {
        System.out.println("\n--- All Hosts ---");
        ArrayList<Host> hosts = manager.getAllHosts();
        if (hosts.isEmpty()) {
            System.out.println("No hosts found.");
            return;
        }
        for (Host host : hosts) {
            System.out.println(host);
        }
    }

    // --- Property Helper Methods ---
    private static void addPropertyMenu() {
        System.out.println("\n--- Add New Property ---");
        if (manager.getAllHosts().isEmpty()) {
            System.out.println("Error: You must add a Host first before adding a property.");
            return;
        }
        System.out.println("Available Hosts:");
        viewAllHostsMenu();
        System.out.print("Enter the ID of the Host who owns this property (e.g., H1): ");
        String ownerId = scanner.nextLine();
        System.out.print("Enter property address: ");
        String address = scanner.nextLine();
        System.out.print("Enter description: ");
        String desc = scanner.nextLine();
        double price = 0;
        try {
            System.out.print("Enter price per night: ");
            price = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid price. Setting to 0.0.");
        }
        String message = manager.addProperty(address, desc, price, ownerId);
        System.out.println(message);
    }

    private static void viewAllPropertiesMenu() {
        System.out.println("\n--- All Properties ---");
        ArrayList<Property> properties = manager.getAllProperties();
        if (properties.isEmpty()) {
            System.out.println("No properties found.");
            return;
        }
        for (Property prop : properties) {
            System.out.println(prop);
        }
    }

    /**
     * NEW: Handles the menu logic for searching properties by price.
     */
    private static void searchPropertiesMenu() {
        System.out.println("\n--- Search Properties by Price ---");
        double maxPrice = 0;
        try {
            System.out.print("Enter maximum price per night: ");
            maxPrice = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid price. Please enter a number.");
            return;
        }

        ArrayList<Property> properties = manager.searchPropertiesByPrice(maxPrice);

        if (properties.isEmpty()) {
            System.out.println("No properties found matching your criteria.");
            return;
        }

        System.out.println("Matching Properties:");
        for (Property prop : properties) {
            System.out.println(prop);
        }
    }

    // --- Guest Helper Methods ---
    private static void addGuestMenu() {
        System.out.println("\n--- Register New Guest ---");
        System.out.print("Enter guest name: ");
        String name = scanner.nextLine();
        System.out.print("Enter guest email: ");
        String email = scanner.nextLine();
        String message = manager.addGuest(name, email);
        System.out.println(message);
    }

    private static void viewAllGuestsMenu() {
        System.out.println("\n--- All Guests ---");
        ArrayList<Guest> guests = manager.getAllGuests();
        if (guests.isEmpty()) {
            System.out.println("No guests found.");
            return;
        }
        for (Guest guest : guests) {
            System.out.println(guest);
        }
    }

    // --- Booking Helper Methods ---

    /**
     * Handles the menu logic for creating a new booking.
     */
    private static void addBookingMenu() {
        System.out.println("\n--- Create New Booking ---");

        if (manager.getAllGuests().isEmpty()) {
            System.out.println("Error: A Guest must be registered before booking.");
            return;
        }
        if (manager.getAllProperties().isEmpty()) {
            System.out.println("Error: No properties are available to book.");
            return;
        }

        System.out.println("Available Guests:");
        viewAllGuestsMenu();
        System.out.print("Enter your Guest ID (e.g., G1): ");
        String guestId = scanner.nextLine();

        System.out.println("\nAvailable Properties:");
        viewAllPropertiesMenu();
        System.out.print("Enter the Property ID you wish to book (e.g., P1): ");
        String propertyId = scanner.nextLine();

        LocalDate startDate = null;
        LocalDate endDate = null;

        try {
            System.out.print("Enter Start Date (YYYY-MM-DD): ");
            startDate = LocalDate.parse(scanner.nextLine());

            System.out.print("Enter End Date (YYYY-MM-DD): ");
            endDate = LocalDate.parse(scanner.nextLine());
        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        String message = manager.createBooking(guestId, propertyId, startDate, endDate);
        System.out.println(message);
    }

    /**
     * Handles the menu logic for viewing all bookings.
     */
    private static void viewAllBookingsMenu() {
        System.out.println("\n--- All Bookings ---");
        ArrayList<Booking> bookings = manager.getAllBookings();

        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Booking booking : bookings) {
            System.out.println(booking);
        }
    }

    /**
     * NEW: Handles the menu logic for cancelling a booking.
     */
    private static void cancelBookingMenu() {
        System.out.println("\n--- Cancel a Booking ---");

        if (manager.getAllBookings().isEmpty()) {
            System.out.println("There are no bookings to cancel.");
            return;
        }

        // Show all bookings so the user knows which ID to pick
        viewAllBookingsMenu();

        System.out.print("\nEnter the ID of the booking you wish to cancel (e.g., B1): ");
        String bookingId = scanner.nextLine();

        String message = manager.cancelBooking(bookingId);
        System.out.println(message);
    }
}