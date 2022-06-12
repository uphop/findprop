package com.lightson.findpropapi.model;

import java.io.Serializable;

import com.lightson.findpropapi.entity.LocalAuthorityRentPrice;
import com.lightson.findpropapi.entity.PostcodeAreaRentPrice;
import com.lightson.findpropapi.entity.RegionRentPrice;

public class RentPriceDetails implements Serializable {
    private Integer priceCount;
    private Integer priceMean;
    private Integer priceLow;
    private Integer priceMedian;
    private Integer priceHigh;
    private String currency;
    private String period;
    
    public Integer getPriceCount() {
        return priceCount;
    }
    public void setPriceCount(Integer priceCount) {
        this.priceCount = priceCount;
    }
    public Integer getPriceMean() {
        return priceMean;
    }
    public void setPriceMean(Integer priceMean) {
        this.priceMean = priceMean;
    }
    public Integer getPriceLow() {
        return priceLow;
    }
    public void setPriceLow(Integer priceLow) {
        this.priceLow = priceLow;
    }
    public Integer getPriceMedian() {
        return priceMedian;
    }
    public void setPriceMedian(Integer priceMedian) {
        this.priceMedian = priceMedian;
    }
    public Integer getPriceHigh() {
        return priceHigh;
    }
    public void setPriceHigh(Integer priceHigh) {
        this.priceHigh = priceHigh;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getPeriod() {
        return period;
    }
    public void setPeriod(String period) {
        this.period = period;
    }

    public static RentPriceDetails fromRegionRentPrice(RegionRentPrice inputRentPrice) {
        RentPriceDetails outputRentPrice = new RentPriceDetails();
        outputRentPrice.setCurrency(inputRentPrice.getCurrency());
        outputRentPrice.setPeriod(inputRentPrice.getPeriod());
        outputRentPrice.setPriceCount(inputRentPrice.getPriceCount());
        outputRentPrice.setPriceHigh(inputRentPrice.getPriceHigh());
        outputRentPrice.setPriceLow(inputRentPrice.getPriceLow());
        outputRentPrice.setPriceMean(inputRentPrice.getPriceMean());
        outputRentPrice.setPriceMedian(inputRentPrice.getPriceMedian());
        return outputRentPrice;
    }

    public static RentPriceDetails fromLocalAuthorityRentPrice(LocalAuthorityRentPrice inputRentPrice) {
        RentPriceDetails outputRentPrice = new RentPriceDetails();
        outputRentPrice.setCurrency(inputRentPrice.getCurrency());
        outputRentPrice.setPeriod(inputRentPrice.getPeriod());
        outputRentPrice.setPriceCount(inputRentPrice.getPriceCount());
        outputRentPrice.setPriceHigh(inputRentPrice.getPriceHigh());
        outputRentPrice.setPriceLow(inputRentPrice.getPriceLow());
        outputRentPrice.setPriceMean(inputRentPrice.getPriceMean());
        outputRentPrice.setPriceMedian(inputRentPrice.getPriceMedian());
        return outputRentPrice;
    }

    public static RentPriceDetails fromPostcodeAreaRentPrice(PostcodeAreaRentPrice inputRentPrice) {
        RentPriceDetails outputRentPrice = new RentPriceDetails();
        outputRentPrice.setCurrency(inputRentPrice.getCurrency());
        outputRentPrice.setPeriod(inputRentPrice.getPeriod());
        outputRentPrice.setPriceCount(inputRentPrice.getPriceCount());
        outputRentPrice.setPriceHigh(inputRentPrice.getPriceHigh());
        outputRentPrice.setPriceLow(inputRentPrice.getPriceLow());
        outputRentPrice.setPriceMean(inputRentPrice.getPriceMean());
        outputRentPrice.setPriceMedian(inputRentPrice.getPriceMedian());
        return outputRentPrice;
    }
}