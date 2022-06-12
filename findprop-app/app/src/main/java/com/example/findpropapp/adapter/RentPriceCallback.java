package com.example.findpropapp.adapter;

import com.example.findpropapp.model.RentPriceResponse;

public interface RentPriceCallback {
    public void onSuccess(RentPriceResponse rentPrices);
    public void onFailure(Exception e);
}
