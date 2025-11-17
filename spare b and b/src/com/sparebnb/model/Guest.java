package com.sparebnb.model;

import java.util.ArrayList;

/**
 * Represents a Guest in the SpareB&B system.
 * A Guest is a user who can search for and book properties.
 */
public class Guest {

    // --- Attributes ---
    private String guestId;
    private String name;
    private String email;

    /**
     * A list of bookings made by this guest.
     * We can add this in a later step when Booking.java is created.
     */
    // private ArrayList<Booking> bookingsMade;

    // --- Constructor ---

    /**
     * Constructs a new Guest object.
     *
     * @param guestId The unique identifier for the guest (e.g., "G1").
     * @param name    The guest's full name.
     * @param email   The guest's contact email address.
     */
    public Guest(String guestId, String name, String email) {
        this.guestId = guestId;
        this.name = name;
        this.email = email;
        // When a new Guest is created, their list of bookings is empty.
        // this.bookingsMade = new ArrayList<>();
    }

    // --- Getters and Setters ---

    public String getGuestId() {
        return guestId;
    }

    // guestId is a final identifier, so we don't provide a setGuestId()

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // --- toString() Method ---

    /**
     * Returns a string representation of the Guest.
     *
     * @return A formatted string with the guest's details.
     */
    @Override
    public String toString() {
        return "Guest [ID: " + guestId + ", Name: " + name + ", Email: " + email + "]";
    }
}