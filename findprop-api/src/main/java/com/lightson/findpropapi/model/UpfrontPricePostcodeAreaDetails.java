package com.lightson.findpropapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lightson.findpropapi.entity.PostcodeAreaRentPrice;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpfrontPricePostcodeAreaDetails extends UpfrontPriceCompositeDetails {
    private String postcodeArea;

    public UpfrontPricePostcodeAreaDetails(PostcodeAreaRentPrice postcodeAreaRentPrice) {
        if (postcodeAreaRentPrice != null) {
            this.postcodeArea = postcodeAreaRentPrice.getPostcodeArea();
            RentPriceDetails rentPriceDetails = RentPriceDetails.fromPostcodeAreaRentPrice(postcodeAreaRentPrice);
            initPrice(rentPriceDetails);
        }
    }

    public String getPostcodeArea() {
        return postcodeArea;
    }

    public void setPostcodeArea(String postcodeArea) {
        this.postcodeArea = postcodeArea;
    }
}
