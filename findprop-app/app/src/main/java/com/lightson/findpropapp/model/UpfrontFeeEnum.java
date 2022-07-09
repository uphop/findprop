package com.lightson.findpropapp.model;

import java.io.Serializable;

public enum UpfrontFeeEnum implements Serializable {
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
