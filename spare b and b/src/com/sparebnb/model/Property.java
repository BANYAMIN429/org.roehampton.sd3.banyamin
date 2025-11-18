package com.sparebnb.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all property types.
 * Demonstrates ABSTRACTION and ENCAPSULATION.
 */
public abstract class Property {

    private String propertyId;
    private String address;
    private String description;
    private double pricePerNight;
    private Host owner;

    // Advanced Feature: Composition (Property HAS-A list of Facilities)
    private List<Facility> facilities;

    public Property(String propertyId, String address, String description, double pricePerNight, Host owner) {
        this.propertyId = propertyId;
        this.address = address;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.owner = owner;
        this.facilities = new ArrayList<>();
    }

    // --- Abstract Method (Polymorphism) ---
    // Every subclass MUST implement this method differently.
    public abstract String getPropertyType();

    // --- Concrete Methods ---

    public void addFacility(Facility facility) {
        if (!facilities.contains(facility)) {
            facilities.add(facility);
        }
    }

    public List<Facility> getFacilities() {
        return facilities;
    }

    public String getPropertyId() { return propertyId; }
    public String getAddress() { return address; }
    public String getDescription() { return description; }
    public double getPricePerNight() { return pricePerNight; }
    public Host getOwner() { return owner; }

    @Override
    public String toString() {
        String ownerName = (owner != null) ? owner.getName() : "Unknown";
        return String.format("[%s] ID: %s | %s | %s | $%.2f/night | Owner: %s | Facilities: %s",
                getPropertyType(), // Polymorphic call
                propertyId,
                address,
                description,
                pricePerNight,
                ownerName,
                facilities.toString());
    }
}