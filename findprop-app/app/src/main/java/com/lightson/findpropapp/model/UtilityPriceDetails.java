package com.lightson.findpropapp.model;

import java.io.Serializable;

public class UtilityPriceDetails implements Serializable {
    private Integer priceMean;
    private UtilityFeeEnum utilityType;
    private RentPriceCurrencyEnum currency;
    private RentPricePeriodEnum period;

    public UtilityPriceDetails(Integer priceMean, UtilityFeeEnum utilityType, RentPriceCurrencyEnum currency,
            RentPricePeriodEnum period) {
        this.priceMean = priceMean;
        this.utilityType = utilityType;
        this.currency = currency;
        this.period = period;
    }

    public UtilityPriceDetails() {
    }

    public Integer getPriceMean() {
        return priceMean;
    }

    public void setPriceMean(Integer priceMean) {
        this.priceMean = priceMean;
    }

    public UtilityFeeEnum getUtilityType() {
        return utilityType;
    }

    public void setUtilityType(UtilityFeeEnum utilityType) {
        this.utilityType = utilityType;
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

    @Override
    public String toString() {
        return "UtilityPriceDetails [currency=" + currency + ", period=" + period + ", priceMean=" + priceMean
                + ", utilityType=" + utilityType + "]";
    }
}