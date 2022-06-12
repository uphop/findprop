package com.lightson.findpropapi.model;

import java.io.Serializable;

import com.lightson.findpropapi.entity.PostcodeAreaRentPrice;

public class RentPricePostcodeAreaDetails implements Serializable {
    private String PostcodeArea;
    private RentPriceDetails price;

    public RentPricePostcodeAreaDetails(String PostcodeArea, RentPriceDetails price) {
        this.PostcodeArea = PostcodeArea;
        this.price = price;
    }

    public RentPricePostcodeAreaDetails(String PostcodeArea, PostcodeAreaRentPrice inputRentPrice) {
        this.PostcodeArea = PostcodeArea;
        this.setPrice(inputRentPrice);
    }

    public RentPricePostcodeAreaDetails() {
    }

    public String getPostcodeArea() {
        return PostcodeArea;
    }

    public void setPostcodeArea(String PostcodeArea) {
        this.PostcodeArea = PostcodeArea;
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