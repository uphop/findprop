package com.lightson.findpropapp.model;

import java.io.Serializable;

public class RentPriceDetails implements Serializable {
    private Integer priceCount;
    private Integer priceMean;
    private Integer priceLow;
    private Integer priceMedian;
    private Integer priceHigh;
    private RentPriceCurrencyEnum currency;
    private RentPricePeriodEnum period;

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

    public RentPriceCurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(RentPriceCurrencyEnum currency) {
        this.currency = currency;
    }

    public RentPricePeriodEnum getPeriod() {
        return period;
    }

    public void setPeriod(RentPricePeriodEnum period) {
        this.period = period;
    }
}