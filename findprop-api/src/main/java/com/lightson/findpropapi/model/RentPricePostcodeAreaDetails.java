package com.lightson.findpropapi.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lightson.findpropapi.entity.PostcodeAreaRentPrice;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RentPricePostcodeAreaDetails implements Serializable {
    private String postcodeArea;
    private RentPriceDetails price;

    public RentPricePostcodeAreaDetails(String postcodeArea, RentPriceDetails price) {
        this.postcodeArea = postcodeArea;
        this.price = price;
    }

    public RentPricePostcodeAreaDetails(String postcodeArea, PostcodeAreaRentPrice inputRentPrice) {
        this.postcodeArea = postcodeArea;
        this.setPrice(inputRentPrice);
    }

    public RentPricePostcodeAreaDetails() {
    }

    public String getPostcodeArea() {
        return postcodeArea;
    }

    public void setPostcodeArea(String postcodeArea) {
        this.postcodeArea = postcodeArea;
    }

    public RentPriceDetails getPrice() {
        return price;
    }

    public void setPrice(RentPriceDetails price) {
        this.price = price;
    }

    public void setPrice(PostcodeAreaRentPrice inputRentPrice) {
        if(inputRentPrice != null) {
            this.price = RentPriceDetails.fromPostcodeAreaRentPrice(inputRentPrice);
        }
    }
}