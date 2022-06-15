package com.example.findpropapp.model;

import java.io.Serializable;
import java.util.List;

public class UtilityPriceLocalAuthorityDetails implements Serializable {
    private String localAuthority;
    private List<UtilityPriceDetails> price;

    public UtilityPriceLocalAuthorityDetails(String localAuthority, List<UtilityPriceDetails> price) {
        this.localAuthority = localAuthority;
        this.price = price;
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
}
