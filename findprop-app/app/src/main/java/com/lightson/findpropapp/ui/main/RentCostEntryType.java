package com.lightson.findpropapp.ui.main;

public enum RentCostEntryType {
    utility("Utilities"),
    rent("Rent"),
    upfront("Upfront costs");

    private String displayName;

    RentCostEntryType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
