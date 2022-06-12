package com.example.findpropapp.model;

import java.io.Serializable;

public class RentPricePostcodeDetails implements Serializable {
    private String postcode;
    private double longitude;
    private double latitude;
    public RentPricePostcodeDetails(String postcode, double longitude, double latitude) {
        this.postcode = postcode;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public RentPricePostcodeDetails() {
    }
    public String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    @Override
    public String toString() {
        return "RentPricePostcodeDetails [latitude=" + latitude + ", longitude=" + longitude + ", postcode=" + postcode
                + "]";
    }

    
}
