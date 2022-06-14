package com.lightson.findpropapi.model;

import java.io.Serializable;

public class UpfrontPriceDetails implements Serializable {
    private Integer priceMean;
    private String upfrontFeeType;
    private String currency;
    private String period;
    public UpfrontPriceDetails(Integer priceMean, String upfrontFeeType, String currency, String period) {
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
    public String getUpfrontFeeType() {
        return upfrontFeeType;
    }
    public void setUpfrontFeeType(String upfrontFeeType) {
        this.upfrontFeeType = upfrontFeeType;
    }
    @Override
    public String toString() {
        return "UpfrontPriceDetails [currency=" + currency + ", period=" + period + ", priceMean=" + priceMean
                + ", upfrontFeeType=" + upfrontFeeType + "]";
    }
}