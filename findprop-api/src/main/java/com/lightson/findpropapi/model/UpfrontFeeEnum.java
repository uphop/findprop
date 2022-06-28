package com.lightson.findpropapi.model;

public enum UpfrontFeeEnum {
    holding_deposit("Holding deposit"),
    tenancy_deposit("Tenancy deposit");

    private String displayName;

    UpfrontFeeEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
