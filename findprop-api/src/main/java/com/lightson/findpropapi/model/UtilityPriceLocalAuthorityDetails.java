package com.lightson.findpropapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.lightson.findpropapi.entity.LocalAuthorityUtilityPrice;

public class UtilityPriceLocalAuthorityDetails implements Serializable {
    private String localAuthority;
    private List<UtilityPriceDetails> price;

    public UtilityPriceLocalAuthorityDetails(String localAuthority, List<UtilityPriceDetails> price) {
        this.localAuthority = localAuthority;
        this.price = price;
    }

    public UtilityPriceLocalAuthorityDetails(String localAuthority,
            Set<LocalAuthorityUtilityPrice> localAuthorityUtilityPrices) {
        this.localAuthority = localAuthority;

        for (LocalAuthorityUtilityPrice localAuthorityUtilityPrice : localAuthorityUtilityPrices) {
            this.addPrice(localAuthorityUtilityPrice);
        }
    }

    public UtilityPriceLocalAuthorityDetails() {
    }

    public String getLocalAuthority() {
        return localAuthority;
    }

    public void setLocalAuthority(String localAuthority) {
        this.localAuthority = localAuthority;
    }

    public List<UtilityPriceDetails> getPrice() {
        return price;
    }

    public void setPrice(List<UtilityPriceDetails> price) {
        this.price = price;
    }

    public void addPrice(LocalAuthorityUtilityPrice inputPrice) {
        if (price == null) {
            price = new ArrayList<UtilityPriceDetails>();
        }
        if (inputPrice != null) {
            price.add(UtilityPriceDetails.fromLocalAuthorityUtilityPrice(inputPrice));
        }
    }
}
