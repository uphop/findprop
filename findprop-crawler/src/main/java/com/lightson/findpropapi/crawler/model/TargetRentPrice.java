package com.lightson.findpropapi.crawler.model;

import java.util.List;

public class TargetRentPrice {
    private PostcodeAreaRentPrice postcodeAreaRentPrice;
    private List<PostcodeRentPrice> postcodeRentPrices;

    public TargetRentPrice(PostcodeAreaRentPrice postcodeAreaRentPrice, List<PostcodeRentPrice> postcodeRentPrices) {
        this.postcodeAreaRentPrice = postcodeAreaRentPrice;
        this.postcodeRentPrices = postcodeRentPrices;
    }

    public TargetRentPrice() {
    }

    public PostcodeAreaRentPrice getPostcodeAreaRentPrice() {
        return postcodeAreaRentPrice;
    }

    public void setPostcodeAreaRentPrice(PostcodeAreaRentPrice postcodeAreaRentPrice) {
        this.postcodeAreaRentPrice = postcodeAreaRentPrice;
    }

    public List<PostcodeRentPrice> getPostcodeRentPrices() {
        return postcodeRentPrices;
    }

    public void setPostcodeRentPrices(List<PostcodeRentPrice> postcodeRentPrices) {
        this.postcodeRentPrices = postcodeRentPrices;
    }

    @Override
    public String toString() {
        return "TargetRentPrice [postcodeAreaRentPrice=" + postcodeAreaRentPrice + ", postcodeRentPrices="
                + postcodeRentPrices + "]";
    }

}
