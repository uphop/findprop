package com.example.findpropapp.ui.main;

public enum RentPriceEntryType {
    postcode("This postcode"),
    postcode_area("This postcode area"),
    local_authority("This borough"),
    related_local_authority("Near-by borough"),
    similar_local_authority("Similar borough"),
    region("This city");

    private String displayName;

    RentPriceEntryType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
