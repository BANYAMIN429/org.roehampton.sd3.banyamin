package com.sparebnb.model;

public class SharedRoom extends Property {

    private boolean hasPrivateBath;

    public SharedRoom(String id, String address, String desc, double price, Host owner, boolean hasPrivateBath) {
        super(id, address, desc, price, owner);
        this.hasPrivateBath = hasPrivateBath;
    }

    @Override
    public String getPropertyType() {
        return hasPrivateBath ? "Shared Room (Ensuite)" : "Shared Room (Shared Bath)";
    }
}