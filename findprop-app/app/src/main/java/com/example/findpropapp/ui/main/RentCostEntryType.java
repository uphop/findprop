package com.example.findpropapp.ui.main;

public enum RentCostEntryType {
    utility("Utility cost"),
    upfront("Upfront cost");

    private String displayName;

    RentCostEntryType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
