package com.example.findpropapp.model;

import java.io.Serializable;

public class RentPricePostcodeAreaDetails implements Serializable {
    private String postcodeArea;
    private RentPriceDetails price;

    public RentPricePostcodeAreaDetails(String postcodeArea, RentPriceDetails price) {
        this.postcodeArea = postcodeArea;
        this.price = price;
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
}