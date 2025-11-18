package com.sparebnb.controller;

import com.sparebnb.model.EntireApartment;
import com.sparebnb.model.Host;
import com.sparebnb.model.Property;
import com.sparebnb.model.SharedRoom;

/**
 * Factory Pattern Implementation.
 * Responsible for creating the correct subclass of Property.
 */
public class PropertyFactory {

    public static Property createProperty(int typeChoice, String id, String address, String desc, double price, Host owner) {
        switch (typeChoice) {
            case 1:
                // Defaulting bedrooms to 1 for simplicity in this menu version
                return new EntireApartment(id, address, desc, price, owner, 1);
            case 2:
                // Defaulting private bath to false for simplicity
                return new SharedRoom(id, address, desc, price, owner, false);
            default:
                throw new IllegalArgumentException("Unknown property type");
        }
    }
}