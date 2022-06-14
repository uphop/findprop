package com.lightson.findpropapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lightson.findpropapi.entity.LocalAuthorityRentPrice;

public class UpfrontPriceLocalAuthorityDetails implements Serializable {
    private String localAuthority;
    private List<UpfrontPriceDetails> price;
    private final static int DEFAULT_ANNUAL_RENT_BAND_THRESHOLD = 50000;
    private final static int DEFAULT_DEPOSIT_LOWER_BAND_WEEKS = 5;
    private final static int DEFAULT_DEPOSIT_HIGHER_BAND_WEEKS = 5;
    private final static String DEPOSIT_FEE_NAME = "deposit";

    public UpfrontPriceLocalAuthorityDetails(String localAuthority, List<UpfrontPriceDetails> price) {
        this.localAuthority = localAuthority;
        this.price = price;
    }

    public UpfrontPriceLocalAuthorityDetails(LocalAuthorityRentPrice localAuthorityDetails) {
        this.localAuthority = localAuthorityDetails.getLocalAuthority().getName();

        price = new ArrayList<UpfrontPriceDetails>();
        UpfrontPriceDetails deposit = new UpfrontPriceDetails();
        deposit.setCurrency(localAuthorityDetails.getCurrency());
        deposit.setPeriod(RentPricePeriodEnum.one_off.toString());
        deposit.setUpfrontFeeType(DEPOSIT_FEE_NAME);
        deposit.setPriceMean(getRentalDeposit(localAuthorityDetails.getPriceMean(), localAuthorityDetails.getPeriod()));
        price.add(deposit);
    }

    public UpfrontPriceLocalAuthorityDetails() {
    }

    public String getLocalAuthority() {
        return localAuthority;
    }

    public void setLocalAuthority(String localAuthority) {
        this.localAuthority = localAuthority;
    }

    public List<UpfrontPriceDetails> getPrice() {
        return price;
    }

    public void setPrice(List<UpfrontPriceDetails> price) {
        this.price = price;
    }

    private static int getRentalDeposit(int price, String period) {
        int pricePcw = RentPriceHelper.getPricePCW(price, period);
        int priceAnnual = pricePcw * RentPriceHelper.WEEKS_IN_YEAR;
        if (priceAnnual > DEFAULT_ANNUAL_RENT_BAND_THRESHOLD) {
            return pricePcw * DEFAULT_DEPOSIT_HIGHER_BAND_WEEKS;
        } else {
            return pricePcw * DEFAULT_DEPOSIT_LOWER_BAND_WEEKS;
        }
    }
}
