package com.example.findpropapp.model;

import java.io.Serializable;

public enum RentPricePropertyTypeEnum implements Serializable {
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
