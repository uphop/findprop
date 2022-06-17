package com.example.findpropapp.ui.main;

public enum RentCostEntryType {
    utility("Utilities"),
    council_tax("Council tax"),
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
