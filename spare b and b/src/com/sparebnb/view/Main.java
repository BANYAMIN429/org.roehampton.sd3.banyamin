package com.sparebnb.view;

import com.sparebnb.controller.SystemManager;
import com.sparebnb.model.Host; // Import the Host class
import com.sparebnb.model.Property;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The main entry point for the SpareB&B console application.
 */
public class Main {

    private static SystemManager manager = new SystemManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to SpareB&B");

        boolean isRunning = true;
        while (isRunning) {
            // 1. Show the UPDATED menu
            System.out.println("\n--- Main Menu ---");
            System.out.println("(1) Add a new Host");
            System.out.println("(2) View all Hosts");
            System.out.println("(3) Add a new Property");
            System.out.println("(4) View all Properties");
            System.out.println("(5) Exit");
            System.out.print("Enter your choice: ");

            // 2. Get user choice
            String choice = scanner.nextLine();

            // 3. Handle choice
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
                    isRunning = false;
                    System.out.println("Thank you for using SpareB&B. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // --- NEW Helper Method for Hosts ---
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
            // This uses the .toString() method in your Host class
            System.out.println(host);
        }
    }

    // --- UPDATED Helper Methods for Properties ---
    private static void addPropertyMenu() {
        System.out.println("\n--- Add New Property ---");

        // You must have a host to add a property
        if (manager.getAllHosts().isEmpty()) {
            System.out.println("Error: You must add a Host first before adding a property.");
            return;
        }

        // Show available hosts
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

        // Pass all data to the manager (THE FIX)
        String message = manager.addProperty(address, desc, price, ownerId);
        System.out.println(message); // This will print success or the error
    }

    private static void viewAllPropertiesMenu() {
        System.out.println("\n--- All Properties ---");
        ArrayList<Property> properties = manager.getAllProperties();

        if (properties.isEmpty()) {
            System.out.println("No properties found.");
            return;
        }

        for (Property prop : properties) {
            // This is much cleaner! It uses the .toString() method
            // in your Property class automatically.
            System.out.println(prop);
        }
    }
}