package com.sparebnb.model;

/**
 * Represents the facilities available at a property.
 * Using an Enum is cleaner and safer than using raw strings.
 */
public enum Facility {
    WIFI,
    KITCHEN,
    PARKING,
    AIR_CONDITIONING,
    POOL,
    BALCONY
}