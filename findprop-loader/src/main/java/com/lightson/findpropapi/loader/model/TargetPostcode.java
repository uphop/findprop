package com.lightson.findpropapi.loader.model;

public class TargetPostcode {
    private String code;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    private double latitude;
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    private double longitude;
   
    public TargetPostcode() {
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    private String localAuthorityCode;
    
    public TargetPostcode(String code, double latitude, double longitude, String localAuthorityCode) {
        this.code = code;
        this.latitude = latitude;
        this.longitude = longitude;
        this.localAuthorityCode = localAuthorityCode;
    }
    public String getLocalAuthorityCode() {
        return localAuthorityCode;
    }
    public void setLocalAuthorityCode(String localAuthorityCode) {
        this.localAuthorityCode = localAuthorityCode;
    }
    @Override
    public String toString() {
        return "TargetPostcode [code=" + code + ", latitude=" + latitude + ", localAuthorityCode=" + localAuthorityCode
                + ", longitude=" + longitude + "]";
    }
 
}
