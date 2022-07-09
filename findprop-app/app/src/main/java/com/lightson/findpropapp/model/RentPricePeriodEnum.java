package com.lightson.findpropapp.model;

import java.io.Serializable;

public enum RentPricePeriodEnum implements Serializable {
    one_off("One-off"),
    year("Year"),
    month("Month"),
    week("Week"),
    day("Day");

    private String displayName;

    RentPricePeriodEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
