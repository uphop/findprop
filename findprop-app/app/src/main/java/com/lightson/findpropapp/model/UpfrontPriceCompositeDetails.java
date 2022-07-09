package com.lightson.findpropapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpfrontPriceCompositeDetails implements Serializable {
    
    private List<UpfrontPriceDetails> price;

    public UpfrontPriceCompositeDetails() {
    }

    public List<UpfrontPriceDetails> getPrice() {
        return price;
    }

    public void setPrice(List<UpfrontPriceDetails> price) {
        this.price = price;
    }

}
