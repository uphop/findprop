package com.example.findpropapp.ui.main;

public enum RentPriceEntryType {
    postcode_area("Postcode area"),
    local_authority("Borough"),
    related_local_authority("Near-by borough"),
    region("City");

    private String displayName;

    RentPriceEntryType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
