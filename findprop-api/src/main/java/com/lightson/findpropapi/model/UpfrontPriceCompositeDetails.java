package com.lightson.findpropapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpfrontPriceCompositeDetails implements Serializable {
    
    private List<UpfrontPriceDetails> price;
    private final static int DEFAULT_ANNUAL_RENT_BAND_THRESHOLD = 50000;
    private final static int DEFAULT_HOLDING_DEPOSIT_WEEKS = 1;
    private final static int DEFAULT_DEPOSIT_LOWER_BAND_WEEKS = 5;
    private final static int DEFAULT_DEPOSIT_HIGHER_BAND_WEEKS = 6;

    public UpfrontPriceCompositeDetails(List<UpfrontPriceDetails> price) {
        this.price = price;
    }

    protected void initPrice(RentPriceDetails rentPriceDetails) {
        if (rentPriceDetails != null) {
            price = new ArrayList<UpfrontPriceDetails>();

            // add holding deposit
            UpfrontPriceDetails holdingDeposit = new UpfrontPriceDetails();
            holdingDeposit.setCurrency(rentPriceDetails.getCurrency());
            holdingDeposit.setPeriod(RentPricePeriodEnum.one_off);
            holdingDeposit.setUpfrontFeeType(UpfrontFeeEnum.holding_deposit);
            holdingDeposit.setPriceMean(UpfrontPriceCompositeDetails
                    .getHoldingDeposit(rentPriceDetails));
            price.add(holdingDeposit);

            // add rental deposit excludinf holding deposit
            UpfrontPriceDetails rentalDeposit = new UpfrontPriceDetails();
            rentalDeposit.setCurrency(rentPriceDetails.getCurrency());
            rentalDeposit.setPeriod(RentPricePeriodEnum.one_off);
            rentalDeposit.setUpfrontFeeType(UpfrontFeeEnum.tenancy_deposit);
            rentalDeposit.setPriceMean(UpfrontPriceCompositeDetails
                    .getRentalDeposit(rentPriceDetails));
            price.add(rentalDeposit);
        }
    }

    public UpfrontPriceCompositeDetails() {
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
}
