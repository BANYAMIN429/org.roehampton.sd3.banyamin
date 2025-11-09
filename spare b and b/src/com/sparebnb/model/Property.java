package com.sparebnb.model;

/**
 * Represents a single rentable property in the SpareB&B system.
 * This class holds all data related to a property, such as its
 * address, price, and owner.
 */
public class Property {

    // --- Attributes ---
    private String propertyId;
    private String address;
    private String description;
    private double pricePerNight;

    // Advanced Feature: This links the Property back to its owner.
    // This is much better than just storing a 'hostId' string.
    private Host owner;

    // --- Constructor ---

    /**
     * Constructs a new Property object.
     *
     * @param propertyId    The unique ID for the property.
     * @param address       The physical address of the property.
     * @param description   A text description.
     * @param pricePerNight The cost for one night's stay.
     * @param owner         The Host object who owns this property.
     */
    public Property(String propertyId, String address, String description, double pricePerNight, Host owner) {
        this.propertyId = propertyId;
        this.address = address;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.owner = owner;
    }

    // --- Getters and Setters ---
    // (Generated in IntelliJ with Alt+Insert -> Getter and Setter)

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This is the method that was missing and causing the error
     * in your Main.java file.
     *
     * @return The price per night as a double.
     */
    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Host getOwner() {
        return owner;
    }

    public void setOwner(Host owner) {
        this.owner = owner;
    }

    // --- toString() Method ---

    /**
     * Returns a well-formatted string representation of the Property.
     * This is automatically called when you print a Property object,
     * making your menu look clean.
     *
     * @return A formatted string with the property's details.
     */
    @Override
    public String toString() {
        // We can even get the owner's name, since we have the full Host object!
        String ownerName = (this.owner != null) ? this.owner.getName() : "No Owner";

        return String.format("Property [ID: %s, Address: %s, Price: $%.2f, Owner: %s]",
                propertyId,
                address,
                pricePerNight,
                ownerName
        );
        // String.format("%.2f", price) prints the price with 2 decimal places, e.g., "$120.00"
    }
}