package com.lightson.findpropapp.adapter;

import com.lightson.findpropapp.model.RentPriceResponse;

public interface RentPriceCallback {
    public void onSuccess(RentPriceResponse rentPrices);
    public void onFailure(Exception e);
}
