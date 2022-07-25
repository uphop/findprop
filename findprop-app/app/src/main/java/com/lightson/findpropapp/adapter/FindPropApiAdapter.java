package com.lightson.findpropapp.adapter;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lightson.findpropapp.BuildConfig;
import com.lightson.findpropapp.model.RentPricePropertyTypeEnum;
import com.lightson.findpropapp.model.RentPriceResponse;
import com.lightson.findpropapp.network.RequestQueueSingleton;

import org.json.JSONObject;

public class FindPropApiAdapter {
    private static final String TAG = FindPropApiAdapter.class.getSimpleName();
    private static final String FINDPROP_API_BASE_URL = BuildConfig.API_URL;
    private static final String FINDPROP_API_KEY = BuildConfig.API_KEY;
    private final Context ctx;

    public FindPropApiAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public void getRentPrices(double longitude, double latitude, double maxRange, RentPricePropertyTypeEnum propertyType, int bedrooms, RentPriceCallback callback) {
        // prepare URL and call backend
        String url = String.format(FINDPROP_API_BASE_URL + "/rent/price?longitude=%f&latitude=%f&maxRange=%f&propertyType=%s&bedrooms=%d&apiKey=%s",
                longitude, latitude, maxRange, propertyType, bedrooms, FINDPROP_API_KEY);

        call(url, Request.Method.GET, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // parse response and check status
                    ObjectMapper objectMapper = new ObjectMapper();
                    RentPriceResponse parsedResponse = objectMapper.readValue(response.toString(), RentPriceResponse.class);

                    if (parsedResponse.getStatus() != RentPriceResponse.StatusEnum.OK) {
                        callback.onFailure(new Exception("Rent price retrieval failed: " + parsedResponse.getMessage()));
                        return;
                    }

                    callback.onSuccess(parsedResponse);
                } catch (JsonProcessingException e) {
                    Log.e(TAG,e.toString());
                    callback.onFailure(new Exception("Rent price response parsing failed: " + e.toString()));
                }
            }
        });
    }

    private void call(String url, int method, JSONObject jsonRequest, Response.Listener<JSONObject> listener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (method, url, jsonRequest, response -> {
                    listener.onResponse(response);
                }, error -> Log.e(TAG, "That didn't work! > " + error.toString()));
        RequestQueueSingleton.getInstance(this.ctx).addToRequestQueue(jsonObjectRequest);
    }
}
