package com.sparebnb.controller;

import com.sparebnb.model.Host;
import com.sparebnb.model.Property;
import com.sparebnb.model.Guest;
import com.sparebnb.model.Booking;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Manages the core business logic and data for the SpareB&B system.
 * This class acts as the "Controller" in the MVC pattern.
 * It holds the main lists of all hosts, properties, guests, and bookings.
 */
public class SystemManager {

    // --- Member Variables (The system's "memory") ---
    private ArrayList<Host> hosts;
    private ArrayList<Property> properties;
    private ArrayList<Guest> guests;
    private ArrayList<Booking> bookings;

    /**
     * Constructor: Initializes all the lists.
     */
    public SystemManager() {
        this.hosts = new ArrayList<>();
        this.properties = new ArrayList<>();
        this.guests = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    // --- Host Methods ---

    /**
     * Creates a new Host and adds them to the system.
     */
    public String addHost(String name, String email) {
        String hostId = "H" + (hosts.size() + 1);
        Host newHost = new Host(hostId, name, email);
        this.hosts.add(newHost);
        return "Host " + name + " added successfully with ID: " + hostId;
    }

    /**
     * Retrieves the list of all hosts in the system.
     */
    public ArrayList<Host> getAllHosts() {
        return this.hosts;
    }

    /**
     * Helper method to find a Host by their ID.
     */
    public Host findHostById(String hostId) {
        for (Host host : this.hosts) {
            if (host.getHostId().equals(hostId)) {
                return host; // Found it!
            }
        }
        return null; // Not found
    }

    // --- Property Methods ---

    /**
     * Creates a new Property and adds it to the system, linking it to an owner.
     */
    public String addProperty(String address, String description, double pricePerNight, String ownerId) {
        Host propertyOwner = findHostById(ownerId);
        if (propertyOwner == null) {
            return "Error: Host with ID " + ownerId + " not found. Cannot add property.";
        }

        String propertyId = "P" + (properties.size() + 1);
        Property newProperty = new Property(propertyId, address, description, pricePerNight, propertyOwner);
        this.properties.add(newProperty);
        propertyOwner.addProperty(newProperty); // Link it to the host

        return "Property at " + address + " added successfully with ID: " + propertyId;
    }

    /**
     * Retrieves the list of all properties in the system.
     */
    public ArrayList<Property> getAllProperties() {
        return this.properties;
    }

    /**
     * Helper method to find a Property by its ID.
     * (This is needed for createBooking)
     */
    public Property findPropertyById(String propertyId) {
        for (Property property : this.properties) {
            if (property.getPropertyId().equals(propertyId)) {
                return property;
            }
        }
        return null;
    }

    /**
     * NEW (Advanced Feature): Searches properties by max price.
     *
     * @param maxPrice The maximum price per night.
     * @return A *new* list containing only matching properties.
     */
    public ArrayList<Property> searchPropertiesByPrice(double maxPrice) {
        ArrayList<Property> filteredProperties = new ArrayList<>();
        for (Property property : this.properties) {
            if (property.getPricePerNight() <= maxPrice) {
                filteredProperties.add(property);
            }
        }
        return filteredProperties;
    }

    // --- Guest Methods ---

    /**
     * Creates a new Guest and adds them to the system.
     */
    public String addGuest(String name, String email) {
        String guestId = "G" + (guests.size() + 1);
        Guest newGuest = new Guest(guestId, name, email);
        this.guests.add(newGuest);
        return "Guest " + name + " added successfully with ID: " + guestId;
    }

    /**
     * Retrieves the list of all guests in the system.
     */
    public ArrayList<Guest> getAllGuests() {
        return this.guests;
    }

    /**
     * Helper method to find a Guest by their ID.
     * (This is needed for createBooking)
     */
    public Guest findGuestById(String guestId) {
        for (Guest guest : this.guests) {
            if (guest.getGuestId().equals(guestId)) {
                return guest; // Found it!
            }
        }
        return null; // Not found
    }

    // --- Booking Methods ---

    /**
     * Retrieves the list of all bookings in the system.
     */
    public ArrayList<Booking> getAllBookings() {
        return this.bookings;
    }

    /**
     * NEW (Advanced Feature): Helper method to find a Booking by its ID.
     *
     * @param bookingId The ID of the booking to find.
     * @return The Booking object if found, or null.
     */
    public Booking findBookingById(String bookingId) {
        for (Booking booking : this.bookings) {
            if (booking.getBookingId().equals(bookingId)) {
                return booking;
            }
        }
        return null;
    }

    /**
     * Checks if a property is available for a given date range.
     * This is the core business logic for bookings.
     */
    private boolean isPropertyAvailable(Property property, LocalDate newStart, LocalDate newEnd) {
        for (Booking existingBooking : this.bookings) {
            if (existingBooking.getProperty().equals(property)) {
                boolean conflict = newStart.isBefore(existingBooking.getEndDate()) &&
                        newEnd.isAfter(existingBooking.getStartDate());

                if (conflict) {
                    return false; // Found an overlap
                }
            }
        }
        return true; // No conflicts found
    }

    /**
     * Creates a new Booking and adds it to the system.
     */
    public String createBooking(String guestId, String propertyId, LocalDate startDate, LocalDate endDate) {

        Guest guest = findGuestById(guestId);
        Property property = findPropertyById(propertyId);

        if (guest == null) {
            return "Error: Guest with ID " + guestId + " not found.";
        }
        if (property == null) {
            return "Error: Property with ID " + propertyId + " not found.";
        }
        if (endDate.isBefore(startDate) || endDate.isEqual(startDate)) {
            return "Error: End date must be at least one day after the start date.";
        }

        if (!isPropertyAvailable(property, startDate, endDate)) {
            return "Error: Property is not available for those dates. Please try again.";
        }

        String bookingId = "B" + (bookings.size() + 1);
        Booking newBooking = new Booking(bookingId, guest, property, startDate, endDate);
        this.bookings.add(newBooking);

        return "Booking successful! ID: " + bookingId + ". Total Price: $" + String.format("%.2f", newBooking.getTotalPrice());
    }

    /**
     * NEW (Advanced Feature): Cancels (deletes) a booking from the system.
     *
     * @param bookingId The ID of the booking to cancel.
     * @return A confirmation or error message.
     */
    public String cancelBooking(String bookingId) {
        Booking bookingToCancel = findBookingById(bookingId);

        if (bookingToCancel == null) {
            return "Error: Booking with ID " + bookingId + " not found.";
        }

        this.bookings.remove(bookingToCancel);
        return "Booking " + bookingId + " has been successfully cancelled.";
    }
}