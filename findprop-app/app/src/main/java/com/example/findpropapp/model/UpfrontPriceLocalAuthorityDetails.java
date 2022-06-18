package com.example.findpropapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UpfrontPriceLocalAuthorityDetails implements Serializable {
    private String localAuthority;
    private String postcodeArea;
    private List<UpfrontPriceDetails> price;
    public final static int DEFAULT_ANNUAL_RENT_BAND_THRESHOLD = 50000;
    public final static int DEFAULT_DEPOSIT_LOWER_BAND_WEEKS = 5;
    public final static int DEFAULT_DEPOSIT_HIGHER_BAND_WEEKS = 5;

    public UpfrontPriceLocalAuthorityDetails(String localAuthority, List<UpfrontPriceDetails> price) {
        this.localAuthority = localAuthority;
        this.price = price;
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

    public String getPostcodeArea() {
        return postcodeArea;
    }

    public void setPostcodeArea(String postcodeArea) {
        this.postcodeArea = postcodeArea;
    }
}
