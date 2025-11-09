package com.sparebnb.controller;

import com.sparebnb.model.Host;
import com.sparebnb.model.Property;
import java.util.ArrayList;

/**
 * Manages the core business logic and data for the SpareB&B system.
 * This class acts as the "Controller" in the MVC pattern.
 * It holds the main lists of all hosts and properties.
 */
public class SystemManager {

    private ArrayList<Host> hosts;
    private ArrayList<Property> properties;

    public SystemManager() {
        this.hosts = new ArrayList<>();
        this.properties = new ArrayList<>();
    }

    // --- Host Methods ---

    /**
     * Creates a new Host and adds them to the system.
     *
     * @param name  The host's full name.
     * @param email The host's contact email.
     * @return A message confirming the host was added.
     */
    public String addHost(String name, String email) {
        String hostId = "H" + (hosts.size() + 1);
        Host newHost = new Host(hostId, name, email);
        this.hosts.add(newHost);
        return "Host " + name + " added successfully with ID: " + hostId;
    }

    /**
     * Retrieves the list of all hosts in the system.
     *
     * @return An ArrayList containing all Host objects.
     */
    public ArrayList<Host> getAllHosts() {
        return this.hosts;
    }

    /**
     * Helper method to find a Host by their ID.
     *
     * @param hostId The ID to search for.
     * @return The Host object if found, or null otherwise.
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
     *
     * @param address       The property's address.
     * @param description   A description of the property.
     * @param pricePerNight The nightly rental price.
     * @param ownerId       The ID of the host who owns this property.
     * @return A message confirming the property was added or an error.
     */
    public String addProperty(String address, String description, double pricePerNight, String ownerId) {

        // 1. Find the Host object using the ID
        Host propertyOwner = findHostById(ownerId);

        if (propertyOwner == null) {
            return "Error: Host with ID " + ownerId + " not found. Cannot add property.";
        }

        // 2. Create the new Property ID
        String propertyId = "P" + (properties.size() + 1);

        // 3. Create the Property object, passing the full Host object (THE FIX)
        Property newProperty = new Property(propertyId, address, description, pricePerNight, propertyOwner);

        // 4. Add the property to the main list
        this.properties.add(newProperty);

        // 5. (Good OOP) Also add the property to the Host's personal list
        propertyOwner.addProperty(newProperty);

        return "Property at " + address + " added successfully with ID: " + propertyId;
    }

    /**
     * Retrieves the list of all properties in the system.
     *
     * @return An ArrayList containing all Property objects.
     */
    public ArrayList<Property> getAllProperties() {
        return this.properties;
    }
}