package com.lightson.findpropapi.crawler.model;

public class SourceRentPrice {
    private String postcodeArea;
    private String bedroomCategory;
    public SourceRentPrice(String postcodeArea, String bedroomCategory) {
        this.postcodeArea = postcodeArea;
        this.bedroomCategory = bedroomCategory;
    }
    public SourceRentPrice() {
    }
    public String getPostcodeArea() {
        return postcodeArea;
    }
    public void setPostcodeArea(String postcodeArea) {
        this.postcodeArea = postcodeArea;
    }
    public String getBedroomCategory() {
        return bedroomCategory;
    }
    public void setBedroomCategory(String bedroomCategory) {
        this.bedroomCategory = bedroomCategory;
    }
    @Override
    public String toString() {
        return "SourcePostcodeAreaRentPrices [bedroomCategory=" + bedroomCategory + ", postcodeArea=" + postcodeArea
                + "]";
    }
}
