package com.lightson.findpropapi.model;

public enum RentPriceCurrencyEnum {
    gbp("£"),
    usd("$"),
    eur("€");

    private String displayName;

    RentPriceCurrencyEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
