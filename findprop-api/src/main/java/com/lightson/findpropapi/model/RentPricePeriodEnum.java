package com.lightson.findpropapi.model;

public enum RentPricePeriodEnum {
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
