package com.lightson.findpropapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lightson.findpropapi.entity.LocalAuthorityRentPrice;
import com.lightson.findpropapi.entity.PostcodeAreaRentPrice;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpfrontPriceLocalAuthorityDetails implements Serializable {
    private String localAuthority;
    private String postcodeArea;
    private List<UpfrontPriceDetails> price;
    private final static int DEFAULT_ANNUAL_RENT_BAND_THRESHOLD = 50000;
    private final static int DEFAULT_HOLDING_DEPOSIT_WEEKS = 1;
    private final static int DEFAULT_DEPOSIT_LOWER_BAND_WEEKS = 5;
    private final static int DEFAULT_DEPOSIT_HIGHER_BAND_WEEKS = 6;

    public UpfrontPriceLocalAuthorityDetails(List<UpfrontPriceDetails> price) {
        this.price = price;
    }

    public UpfrontPriceLocalAuthorityDetails(LocalAuthorityRentPrice localAuthorityDetails) {
        if (localAuthorityDetails != null) {
            this.localAuthority = localAuthorityDetails.getLocalAuthority().getName();
            RentPriceDetails rentPriceDetails = RentPriceDetails.fromLocalAuthorityRentPrice(localAuthorityDetails);
            initPrice(rentPriceDetails);
        }
    }

    public UpfrontPriceLocalAuthorityDetails(PostcodeAreaRentPrice postcodeAreaRentPrice) {
        if (postcodeAreaRentPrice != null) {
            this.postcodeArea = postcodeAreaRentPrice.getPostcodeArea();
            RentPriceDetails rentPriceDetails = RentPriceDetails.fromPostcodeAreaRentPrice(postcodeAreaRentPrice);
            initPrice(rentPriceDetails);
        }
    }

    private void initPrice(RentPriceDetails rentPriceDetails) {
        if (rentPriceDetails != null) {
            price = new ArrayList<UpfrontPriceDetails>();

            // add holding deposit
            UpfrontPriceDetails holdingDeposit = new UpfrontPriceDetails();
            holdingDeposit.setCurrency(rentPriceDetails.getCurrency());
            holdingDeposit.setPeriod(RentPricePeriodEnum.one_off);
            holdingDeposit.setUpfrontFeeType(UpfrontFeeEnum.holding_deposit);
            holdingDeposit.setPriceMean(UpfrontPriceLocalAuthorityDetails
                    .getHoldingDeposit(rentPriceDetails));
            price.add(holdingDeposit);

            // add rental deposit excludinf holding deposit
            UpfrontPriceDetails rentalDeposit = new UpfrontPriceDetails();
            rentalDeposit.setCurrency(rentPriceDetails.getCurrency());
            rentalDeposit.setPeriod(RentPricePeriodEnum.one_off);
            rentalDeposit.setUpfrontFeeType(UpfrontFeeEnum.tenancy_deposit);
            rentalDeposit.setPriceMean(UpfrontPriceLocalAuthorityDetails
                    .getRentalDeposit(rentPriceDetails));
            price.add(rentalDeposit);
        }
    }

    public UpfrontPriceLocalAuthorityDetails() {
    }

    public List<UpfrontPriceDetails> getPrice() {
        return price;
    }

    public void setPrice(List<UpfrontPriceDetails> price) {
        this.price = price;
    }

    private static int getHoldingDeposit(RentPriceDetails price) {
        int pricePcw = RentPriceDetails.getPricePCW(price);
        return pricePcw * DEFAULT_HOLDING_DEPOSIT_WEEKS;
    }

    private static int getRentalDeposit(RentPriceDetails price) {
        int pricePcw = RentPriceDetails.getPricePCW(price);
        int priceAnnual = pricePcw * RentPriceDetails.WEEKS_IN_YEAR;
        if (priceAnnual > DEFAULT_ANNUAL_RENT_BAND_THRESHOLD) {
            return pricePcw * (DEFAULT_DEPOSIT_HIGHER_BAND_WEEKS - DEFAULT_HOLDING_DEPOSIT_WEEKS);
        } else {
            return pricePcw * (DEFAULT_DEPOSIT_LOWER_BAND_WEEKS - DEFAULT_HOLDING_DEPOSIT_WEEKS);
        }
    }

    public String getLocalAuthority() {
        return localAuthority;
    }

    public void setLocalAuthority(String localAuthority) {
        this.localAuthority = localAuthority;
    }

    public String getPostcodeArea() {
        return postcodeArea;
    }

    public void setPostcodeArea(String postcodeArea) {
        this.postcodeArea = postcodeArea;
    }
}
