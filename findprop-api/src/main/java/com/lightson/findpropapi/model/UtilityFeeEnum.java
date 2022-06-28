package com.lightson.findpropapi.model;

public enum UtilityFeeEnum {
    tv_license("TV license"),
    council_tax("Council tax"),
    internet("Internet"),
    energy("Energy"),
    water("Water");

    private String displayName;

    UtilityFeeEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
