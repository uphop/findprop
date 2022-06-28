package com.lightson.findpropapi.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpfrontPriceDetails implements Serializable {
    private Integer priceMean;
    private UpfrontFeeEnum upfrontFeeType;
    private RentPriceCurrencyEnum currency;
    private RentPricePeriodEnum period;

    public UpfrontPriceDetails(Integer priceMean, UpfrontFeeEnum upfrontFeeType, RentPriceCurrencyEnum currency,
            RentPricePeriodEnum period) {
        this.priceMean = priceMean;
        this.upfrontFeeType = upfrontFeeType;
        this.currency = currency;
        this.period = period;
    }

    public UpfrontPriceDetails() {
    }

    public Integer getPriceMean() {
        return priceMean;
    }

    public void setPriceMean(Integer priceMean) {
        this.priceMean = priceMean;
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

    public UpfrontFeeEnum getUpfrontFeeType() {
        return upfrontFeeType;
    }

    public void setUpfrontFeeType(UpfrontFeeEnum upfrontFeeType) {
        this.upfrontFeeType = upfrontFeeType;
    }

    @Override
    public String toString() {
        return "UpfrontPriceDetails [currency=" + currency + ", period=" + period + ", priceMean=" + priceMean
                + ", upfrontFeeType=" + upfrontFeeType + "]";
    }
}