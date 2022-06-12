package com.example.findpropapp.model;

import java.io.Serializable;

public class RentPricePostcodeAreaDetails implements Serializable {
    private String PostcodeArea;
    private RentPriceDetails price;

    public RentPricePostcodeAreaDetails(String PostcodeArea, RentPriceDetails price) {
        this.PostcodeArea = PostcodeArea;
        this.price = price;
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

    @Override
    public String toString() {
        return "RentPricePostcodeAreaDetails{" +
                "PostcodeArea='" + PostcodeArea + '\'' +
                ", price=" + price +
                '}';
    }
}