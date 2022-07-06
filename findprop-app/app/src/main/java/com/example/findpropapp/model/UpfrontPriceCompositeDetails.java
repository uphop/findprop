package com.example.findpropapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

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
