package com.sparebnb.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit; // We'll use this to calculate the number of nights

/**
 * Represents a single booking in the SpareB&B system.
 * This class links a Guest to a Property for a specific date range.
 */
public class Booking {

    private String bookingId;
    private Guest guest; // The Guest object who booked
    private Property property; // The Property object that was booked
    private LocalDate startDate; // The check-in date
    private LocalDate endDate; // The check-out date
    private double totalPrice; // This will be calculated automatically

    /**
     * Constructs a new Booking object.
     *
     * @param bookingId  The unique ID for the booking (e.g., "B1").
     * @param guest      The Guest object.
     * @param property   The Property object.
     * @param startDate  The check-in date.
     * @param endDate    The check-out date.
     */
    public Booking(String bookingId, Guest guest, Property property, LocalDate startDate, LocalDate endDate) {
        this.bookingId = bookingId;
        this.guest = guest;
        this.property = property;
        this.startDate = startDate;
        this.endDate = endDate;

        // Call the private helper method to calculate the price
        this.totalPrice = calculateTotalPrice();
    }

    /**
     * A private helper method to calculate the total price.
     * It finds the number of nights and multiplies by the property's nightly rate.
     *
     * @return The calculated total price.
     */
    private double calculateTotalPrice() {
        // ChronoUnit.DAYS.between calculates the number of full days between the two dates.
        // For a booking, this is the number of nights.
        long numberOfNights = ChronoUnit.DAYS.between(startDate, endDate);

        // If the start and end date are the same, it's 0 nights.
        // We should ensure at least 1 night is charged.
        if (numberOfNights <= 0) {
            numberOfNights = 1;
        }

        // Get the price from the property and multiply
        return property.getPricePerNight() * numberOfNights;
    }

    // --- Getters ---
    // We don't provide setters, because a booking's core details
    // (like who, where, and when) shouldn't be changed.
    // To "change" a booking, you would cancel it and make a new one.

    public String getBookingId() {
        return bookingId;
    }

    public Guest getGuest() {
        return guest;
    }

    public Property getProperty() {
        return property;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Returns a well-formatted string representation of the Booking.
     * This is called automatically when you print a Booking object.
     *
     * @return A formatted string with the booking's details.
     */
    @Override
    public String toString() {
        // String.format is a clean way to build a string
        // "%.2f" formats the price to have two decimal places (e.g., $150.00)
        return String.format("Booking [ID: %s, Guest: %s, Property: %s, From: %s, To: %s, Total: $%.2f]",
                bookingId,
                guest.getName(), // Get the guest's name to be more readable
                property.getAddress(), // Get the property's address
                startDate,
                endDate,
                totalPrice
        );
    }
}