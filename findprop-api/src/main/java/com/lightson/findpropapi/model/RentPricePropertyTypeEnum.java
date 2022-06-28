package com.lightson.findpropapi.model;

public enum RentPricePropertyTypeEnum {
    room("Room"),
    studio("Studio"),
    flat("Flat");

    private String displayName;

    RentPricePropertyTypeEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
