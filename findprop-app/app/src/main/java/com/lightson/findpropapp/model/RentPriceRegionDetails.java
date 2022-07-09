package com.lightson.findpropapp.model;

import java.io.Serializable;

public class RentPriceRegionDetails implements Serializable {
    private String region;
    private RentPriceDetails price;

    public RentPriceRegionDetails(String region, RentPriceDetails price) {
        this.region = region;
        this.price = price;
    }

    public RentPriceRegionDetails() {
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public RentPriceDetails getPrice() {
        return price;
    }

    public void setPrice(RentPriceDetails price) {
        this.price = price;
    }

}
