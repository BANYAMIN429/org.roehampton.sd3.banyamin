package com.sparebnb.model;

import java.util.ArrayList;

/**
 * Represents a Host in the SpareB&B system.
 * A Host is a user who can own and list properties.
 */
public class Host {

    // --- Attributes ---
    private String hostId;
    private String name;
    private String email;

    /**
     * A list of properties owned by this host.
     * This demonstrates the "Composition" OOP principle (a Host "has" Properties).
     */
    private ArrayList<Property> propertiesOwned;

    // --- Constructor ---

    /**
     * Constructs a new Host object.
     *
     * @param hostId The unique identifier for the host.
     * @param name   The host's full name.
     * @param email  The host's contact email address.
     */
    public Host(String hostId, String name, String email) {
        this.hostId = hostId;
        this.name = name;
        this.email = email;
        // When a new Host is created, their list of properties is empty.
        this.propertiesOwned = new ArrayList<>();
    }

    // --- Getters and Setters ---
    // Tip: In IntelliJ, use Alt+Insert (or Cmd+N on Mac) -> "Getter and Setter"

    public String getHostId() {
        return hostId;
    }

    // hostId is typically a final identifier, so we don't provide a setHostId()

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
        // You could add email validation logic here
        this.email = email;
    }

    public ArrayList<Property> getPropertiesOwned() {
        return propertiesOwned;
    }

    // We don't provide a setPropertiesOwned() because you shouldn't
    // replace the list. Instead, you add or remove from it.

    // --- Business Logic Methods ---

    /**
     * Adds a property to this host's list of owned properties.
     *
     * @param property The Property object to add.
     */
    public void addProperty(Property property) {
        if (property != null && !this.propertiesOwned.contains(property)) {
            this.propertiesOwned.add(property);

            // This creates a two-way link, which is great OOP.
            // We'll need to add this method to the Property class.
            // property.setOwner(this);
        }
    }

    // --- toString() Method ---

    /**
     * Returns a string representation of the Host, useful for
     * printing to the console and debugging.
     *
     * @return A formatted string with the host's details.
     */
    @Override
    public String toString() {
        return "Host [ID: " + hostId + ", Name: " + name + ", Email: " + email + "]";
    }
}