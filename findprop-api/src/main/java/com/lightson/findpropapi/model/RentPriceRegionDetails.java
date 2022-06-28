package com.lightson.findpropapi.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lightson.findpropapi.entity.RegionRentPrice;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RentPriceRegionDetails implements Serializable {
    private String region;
    private RentPriceDetails price;

    public RentPriceRegionDetails(String region, RentPriceDetails price) {
        this.region = region;
        this.price = price;
    }

    public RentPriceRegionDetails(String region, RegionRentPrice inputRentPrice) {
        this.region = region;
        this.setPrice(inputRentPrice);
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

    public void setPrice(RegionRentPrice inputRentPrice) {
        if(inputRentPrice != null) {
            this.price = RentPriceDetails.fromRegionRentPrice(inputRentPrice);
        }
    }

}
