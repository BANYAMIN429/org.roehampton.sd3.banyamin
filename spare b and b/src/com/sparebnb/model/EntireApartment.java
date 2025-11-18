package com.sparebnb.model;

public class EntireApartment extends Property {

    private int numberOfBedrooms;

    public EntireApartment(String id, String address, String desc, double price, Host owner, int numberOfBedrooms) {
        super(id, address, desc, price, owner);
        this.numberOfBedrooms = numberOfBedrooms;
    }

    @Override
    public String getPropertyType() {
        return "Entire Apartment (" + numberOfBedrooms + " Beds)";
    }
}