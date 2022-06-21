package com.example.findpropapp.ui.main;

public enum RentPriceEntryType {
    postcode_area("Postcode area"),
    local_authority("This borough"),
    related_local_authority("Near-by borough"),
    similar_local_authority("Similar borough"),
    region("City");

    private String displayName;

    RentPriceEntryType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
