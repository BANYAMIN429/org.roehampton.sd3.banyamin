package com.sparebnb.controller;

import com.sparebnb.model.Booking;
import com.sparebnb.model.Guest;
import com.sparebnb.model.Host;
import com.sparebnb.model.Property;
import com.sparebnb.model.Facility; // Import the new Enum

import java.time.LocalDate;
import java.util.ArrayList;

public class SystemManager {

    // --- SINGLETON PATTERN ---
    private static SystemManager instance;

    private ArrayList<Host> hosts;
    private ArrayList<Property> properties;
    private ArrayList<Guest> guests;
    private ArrayList<Booking> bookings;

    // Private constructor prevents direct instantiation (new SystemManager())
    private SystemManager() {
        this.hosts = new ArrayList<>();
        this.properties = new ArrayList<>();
        this.guests = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    // Global access point
    public static SystemManager getInstance() {
        if (instance == null) {
            instance = new SystemManager();
        }
        return instance;
    }
    // -------------------------

    // --- Host Methods ---
    public String addHost(String name, String email) {
        String hostId = "H" + (hosts.size() + 1);
        Host newHost = new Host(hostId, name, email);
        this.hosts.add(newHost);
        return "Host added: " + name + " (ID: " + hostId + ")";
    }

    public ArrayList<Host> getAllHosts() { return this.hosts; }

    public Host findHostById(String hostId) {
        for (Host h : hosts) {
            if (h.getHostId().equalsIgnoreCase(hostId)) return h;
        }
        return null;
    }

    // --- Property Methods ---
    // Updated to use the Factory and handle Facilities
    public String addProperty(int typeChoice, String address, String description, double price, String ownerId) {
        Host owner = findHostById(ownerId);
        if (owner == null) return "Error: Host not found.";

        String propertyId = "P" + (properties.size() + 1);

        try {
            // Use FACTORY to create the specific type
            Property newProp = PropertyFactory.createProperty(typeChoice, propertyId, address, description, price, owner);

            // Add some default facilities for demo purposes
            newProp.addFacility(Facility.WIFI);
            if (typeChoice == 1) newProp.addFacility(Facility.KITCHEN);

            this.properties.add(newProp);
            owner.addProperty(newProp);
            return "Property added successfully! ID: " + propertyId;
        } catch (IllegalArgumentException e) {
            return "Error: Invalid property type selected.";
        }
    }

    public ArrayList<Property> getAllProperties() { return this.properties; }

    public Property findPropertyById(String id) {
        for (Property p : properties) {
            if (p.getPropertyId().equalsIgnoreCase(id)) return p;
        }
        return null;
    }

    public ArrayList<Property> searchPropertiesByPrice(double maxPrice) {
        ArrayList<Property> result = new ArrayList<>();
        for (Property p : properties) {
            if (p.getPricePerNight() <= maxPrice) {
                result.add(p);
            }
        }
        return result;
    }

    // --- Guest Methods ---
    public String addGuest(String name, String email) {
        String id = "G" + (guests.size() + 1);
        Guest g = new Guest(id, name, email);
        this.guests.add(g);
        return "Guest added: " + name + " (ID: " + id + ")";
    }

    public ArrayList<Guest> getAllGuests() { return this.guests; }

    public Guest findGuestById(String id) {
        for (Guest g : guests) {
            if (g.getGuestId().equalsIgnoreCase(id)) return g;
        }
        return null;
    }

    // --- Booking Methods ---
    public String createBooking(String guestId, String propertyId, LocalDate start, LocalDate end) {
        Guest guest = findGuestById(guestId);
        Property property = findPropertyById(propertyId);

        if (guest == null) return "Guest not found.";
        if (property == null) return "Property not found.";
        if (!end.isAfter(start)) return "End date must be after start date.";

        // Overlap check
        for (Booking b : bookings) {
            if (b.getProperty().equals(property)) {
                if (start.isBefore(b.getEndDate()) && end.isAfter(b.getStartDate())) {
                    return "Property is unavailable for these dates.";
                }
            }
        }

        String bookingId = "B" + (bookings.size() + 1);
        Booking newBooking = new Booking(bookingId, guest, property, start, end);
        this.bookings.add(newBooking);
        return "Booking confirmed! ID: " + bookingId + " Total: $" + String.format("%.2f", newBooking.getTotalPrice());
    }

    public ArrayList<Booking> getAllBookings() { return this.bookings; }

    public String cancelBooking(String bookingId) {
        Booking toRemove = null;
        for (Booking b : bookings) {
            if (b.getBookingId().equalsIgnoreCase(bookingId)) {
                toRemove = b;
                break;
            }
        }
        if (toRemove != null) {
            bookings.remove(toRemove);
            return "Booking cancelled.";
        }
        return "Booking ID not found.";
    }
}