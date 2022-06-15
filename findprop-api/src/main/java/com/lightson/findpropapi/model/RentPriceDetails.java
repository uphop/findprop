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
    private RentPriceCurrencyEnum currency;
    private RentPricePeriodEnum period;

    public final static int DAYS_IN_WEEK = 7;
    public final static int DAYS_IN_MONTH = 31;
    public final static int MONTHS_IN_YEAR = 12;
    public final static int WEEKS_IN_YEAR = 52;

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

    public static RentPriceDetails fromRegionRentPrice(RegionRentPrice inputRentPrice) {
        RentPriceDetails outputRentPrice = new RentPriceDetails();
        outputRentPrice.setCurrency(RentPriceCurrencyEnum.valueOf(inputRentPrice.getCurrency()));
        outputRentPrice.setPeriod(RentPricePeriodEnum.valueOf(inputRentPrice.getPeriod()));
        outputRentPrice.setPriceCount(inputRentPrice.getPriceCount());
        outputRentPrice.setPriceHigh(inputRentPrice.getPriceHigh());
        outputRentPrice.setPriceLow(inputRentPrice.getPriceLow());
        outputRentPrice.setPriceMean(inputRentPrice.getPriceMean());
        outputRentPrice.setPriceMedian(inputRentPrice.getPriceMedian());
        return outputRentPrice;
    }

    public static RentPriceDetails fromLocalAuthorityRentPrice(LocalAuthorityRentPrice inputRentPrice) {
        RentPriceDetails outputRentPrice = new RentPriceDetails();
        outputRentPrice.setCurrency(RentPriceCurrencyEnum.valueOf(inputRentPrice.getCurrency()));
        outputRentPrice.setPeriod(RentPricePeriodEnum.valueOf(inputRentPrice.getPeriod()));
        outputRentPrice.setPriceCount(inputRentPrice.getPriceCount());
        outputRentPrice.setPriceHigh(inputRentPrice.getPriceHigh());
        outputRentPrice.setPriceLow(inputRentPrice.getPriceLow());
        outputRentPrice.setPriceMean(inputRentPrice.getPriceMean());
        outputRentPrice.setPriceMedian(inputRentPrice.getPriceMedian());
        return outputRentPrice;
    }

    public static RentPriceDetails fromPostcodeAreaRentPrice(PostcodeAreaRentPrice inputRentPrice) {
        RentPriceDetails outputRentPrice = new RentPriceDetails();
        outputRentPrice.setCurrency(RentPriceCurrencyEnum.valueOf(inputRentPrice.getCurrency()));
        outputRentPrice.setPeriod(RentPricePeriodEnum.valueOf(inputRentPrice.getPeriod()));
        outputRentPrice.setPriceCount(inputRentPrice.getPriceCount());
        outputRentPrice.setPriceHigh(inputRentPrice.getPriceHigh());
        outputRentPrice.setPriceLow(inputRentPrice.getPriceLow());
        outputRentPrice.setPriceMean(inputRentPrice.getPriceMean());
        outputRentPrice.setPriceMedian(inputRentPrice.getPriceMedian());
        return outputRentPrice;
    }

    public int getPricePCW() {
        switch (this.period) {
            case day:
                return this.priceMean * DAYS_IN_WEEK;
            case week:
                return this.priceMean;
            case month:
                return this.priceMean * MONTHS_IN_YEAR / WEEKS_IN_YEAR;
            case year:
                return this.priceMean / WEEKS_IN_YEAR;
            case one_off:
                return -1;
            default:
                return -1;
        }
    }

    public int getPricePCM() {
        switch (this.period) {
            case day:
                return this.priceMean * DAYS_IN_MONTH;
            case week:
                return this.priceMean * WEEKS_IN_YEAR / MONTHS_IN_YEAR;
            case month:
                return this.priceMean;
            case year:
                return this.priceMean / MONTHS_IN_YEAR;
            case one_off:
                return -1;
            default:
                return -1;
        }
    }
}