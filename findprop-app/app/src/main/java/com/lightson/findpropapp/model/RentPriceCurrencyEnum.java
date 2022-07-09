package com.lightson.findpropapp.model;

import java.io.Serializable;

public enum RentPriceCurrencyEnum implements Serializable {
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
