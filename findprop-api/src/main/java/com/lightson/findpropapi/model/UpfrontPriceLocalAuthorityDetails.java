package com.lightson.findpropapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lightson.findpropapi.entity.LocalAuthorityRentPrice;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpfrontPriceLocalAuthorityDetails extends UpfrontPriceCompositeDetails {
    private String localAuthority;

    public UpfrontPriceLocalAuthorityDetails(LocalAuthorityRentPrice localAuthorityDetails) {
        if (localAuthorityDetails != null) {
            this.localAuthority = localAuthorityDetails.getLocalAuthority().getName();
            RentPriceDetails rentPriceDetails = RentPriceDetails.fromLocalAuthorityRentPrice(localAuthorityDetails);
            super.initPrice(rentPriceDetails);
        }
    }

    public String getLocalAuthority() {
        return localAuthority;
    }

    public void setLocalAuthority(String localAuthority) {
        this.localAuthority = localAuthority;
    }
}
