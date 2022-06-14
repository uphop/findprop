package com.lightson.findpropapi.model;

import java.io.Serializable;

import com.lightson.findpropapi.entity.LocalAuthorityUtilityPrice;

public class UtilityPriceDetails implements Serializable {
    private Integer priceMean;
    private String utilityType;
    private String currency;
    private String period;
    public UtilityPriceDetails(Integer priceMean, String utilityType, String currency, String period) {
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
    public String getUtilityType() {
        return utilityType;
    }
    public void setUtilityType(String utilityType) {
        this.utilityType = utilityType;
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
    @Override
    public String toString() {
        return "UtilityPriceDetails [currency=" + currency + ", period=" + period + ", priceMean=" + priceMean
                + ", utilityType=" + utilityType + "]";
    }
    
    public static UtilityPriceDetails fromLocalAuthorityUtilityPrice(LocalAuthorityUtilityPrice inputUtilityPrice) {
        UtilityPriceDetails outputUtilityPrice = new UtilityPriceDetails();
        outputUtilityPrice.setUtilityType(inputUtilityPrice.getUtilityType());
        outputUtilityPrice.setCurrency(inputUtilityPrice.getCurrency());
        outputUtilityPrice.setPeriod(inputUtilityPrice.getPeriod());
        outputUtilityPrice.setPriceMean(inputUtilityPrice.getPriceMean());
        return outputUtilityPrice;
    }
}