package com.lightson.findpropapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lightson.findpropapi.entity.PostcodeRentPrice;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpfrontPricePostcodeDetails extends UpfrontPriceCompositeDetails {
    private String postcode;

    public UpfrontPricePostcodeDetails(PostcodeRentPrice postcodeRentPrice) {
        if (postcodeRentPrice != null) {
            this.postcode = postcodeRentPrice.getPostcode().getCode();
            RentPriceDetails rentPriceDetails = RentPriceDetails.fromPostcodeRentPrice(postcodeRentPrice);
            initPrice(rentPriceDetails);
        }
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
