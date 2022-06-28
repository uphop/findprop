package com.lightson.findpropapi.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lightson.findpropapi.entity.LocalAuthorityRentPrice;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RentPriceLocalAuthorityDetails implements Serializable {
    private String localAuthority;
    private RentPriceDetails price;

    public RentPriceLocalAuthorityDetails(String localAuthority, RentPriceDetails price) {
        this.localAuthority = localAuthority;
        this.price = price;
    }

    public RentPriceLocalAuthorityDetails(String localAuthority, LocalAuthorityRentPrice inputPrice) {
        this.localAuthority = localAuthority;
        this.setPrice(inputPrice);
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

    public void setPrice(LocalAuthorityRentPrice inputPrice) {
        if(inputPrice != null) {
            this.price = RentPriceDetails.fromLocalAuthorityRentPrice(inputPrice);
        }
    }

}
