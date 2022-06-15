package com.lightson.findpropapi.model;

import java.io.Serializable;

import com.lightson.findpropapi.entity.LocalAuthorityUtilityPrice;

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

    public static UtilityPriceDetails fromLocalAuthorityUtilityPrice(LocalAuthorityUtilityPrice inputUtilityPrice) {
        UtilityPriceDetails outputUtilityPrice = new UtilityPriceDetails();
        outputUtilityPrice.setUtilityType(UtilityFeeEnum.valueOf(inputUtilityPrice.getUtilityType()));
        outputUtilityPrice.setCurrency(RentPriceCurrencyEnum.valueOf(inputUtilityPrice.getCurrency()));
        outputUtilityPrice.setPeriod(RentPricePeriodEnum.valueOf(inputUtilityPrice.getPeriod()));
        outputUtilityPrice.setPriceMean(inputUtilityPrice.getPriceMean());
        return outputUtilityPrice;
    }
}