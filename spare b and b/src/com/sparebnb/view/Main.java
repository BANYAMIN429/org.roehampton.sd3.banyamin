package com.sparebnb.view;

import com.sparebnb.controller.SystemManager;
import com.sparebnb.model.Booking;
import com.sparebnb.model.Guest;
import com.sparebnb.model.Host;
import com.sparebnb.model.Property;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    // Get the SINGLETON instance
    private static SystemManager manager = SystemManager.getInstance();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to SpareB&B (Advanced Version)");

        boolean running = true;
        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Add Host");
            System.out.println("2. View Hosts");
            System.out.println("3. Add Property (Factory Mode)");
            System.out.println("4. View Properties");
            System.out.println("5. Add Guest");
            System.out.println("6. View Guests");
            System.out.println("7. Create Booking");
            System.out.println("8. View Bookings");
            System.out.println("9. Cancel Booking");
            System.out.println("10. Search by Price");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": addHost(); break;
                case "2": viewHosts(); break;
                case "3": addProperty(); break;
                case "4": viewProperties(); break;
                case "5": addGuest(); break;
                case "6": viewGuests(); break;
                case "7": createBooking(); break;
                case "8": viewBookings(); break;
                case "9": cancelBooking(); break;
                case "10": searchProperties(); break;
                case "0": running = false; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void addHost() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.println(manager.addHost(name, email));
    }

    private static void viewHosts() {
        for (Host h : manager.getAllHosts()) System.out.println(h);
    }

    private static void addProperty() {
        if (manager.getAllHosts().isEmpty()) {
            System.out.println("Add a host first.");
            return;
        }
        System.out.println("--- Select Property Type ---");
        System.out.println("1. Entire Apartment");
        System.out.println("2. Shared Room");
        System.out.print("Enter type (1 or 2): ");
        int type = Integer.parseInt(scanner.nextLine());

        viewHosts();
        System.out.print("Host ID: ");
        String ownerId = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Description: ");
        String desc = scanner.nextLine();
        System.out.print("Price per night: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.println(manager.addProperty(type, address, desc, price, ownerId));
    }

    private static void viewProperties() {
        for (Property p : manager.getAllProperties()) System.out.println(p);
    }

    private static void addGuest() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.println(manager.addGuest(name, email));
    }

    private static void viewGuests() {
        for (Guest g : manager.getAllGuests()) System.out.println(g);
    }

    private static void createBooking() {
        System.out.print("Guest ID: ");
        String gid = scanner.nextLine();
        System.out.print("Property ID: ");
        String pid = scanner.nextLine();
        System.out.print("Start (YYYY-MM-DD): ");
        LocalDate start = LocalDate.parse(scanner.nextLine());
        System.out.print("End (YYYY-MM-DD): ");
        LocalDate end = LocalDate.parse(scanner.nextLine());
        System.out.println(manager.createBooking(gid, pid, start, end));
    }

    private static void viewBookings() {
        for (Booking b : manager.getAllBookings()) System.out.println(b);
    }

    private static void cancelBooking() {
        System.out.print("Booking ID to cancel: ");
        String bid = scanner.nextLine();
        System.out.println(manager.cancelBooking(bid));
    }

    private static void searchProperties() {
        System.out.print("Max Price: ");
        double max = Double.parseDouble(scanner.nextLine());
        for (Property p : manager.searchPropertiesByPrice(max)) System.out.println(p);
    }
}1