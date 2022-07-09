package com.lightson.findpropapp.model;

import java.io.Serializable;

public class RentPriceLocalAuthorityDetails implements Serializable {
    private String localAuthority;
    private RentPriceDetails price;

    public RentPriceLocalAuthorityDetails(String localAuthority, RentPriceDetails price) {
        this.localAuthority = localAuthority;
        this.price = price;
    }

    public RentPriceLocalAuthorityDetails() {
    }

    public String getLocalAuthority() {
        return localAuthority;
    }

    public void setLocalAuthority(String localAuthority) {
        this.localAuthority = localAuthority;
    }

    public RentPriceDetails getPrice() {
        return price;
    }

    public void setPrice(RentPriceDetails price) {
        this.price = price;
    }
}
