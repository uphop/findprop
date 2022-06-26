package com.lightson.findpropapi.crawler.adapter;

import java.io.Serializable;

public class PropertyDataRentRawDataPoint implements Serializable {
    private int price;
    private double lat;
    private double lng;
    private int bedrooms;
    private PropertyDataPropertyTypeEnum type;

    public PropertyDataRentRawDataPoint(int price, double lat, double lng, int bedrooms,
            PropertyDataPropertyTypeEnum type) {
        this.price = price;
        this.lat = lat;
        this.lng = lng;
        this.bedrooms = bedrooms;
        this.type = type;
    }

    public PropertyDataRentRawDataPoint() {
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public PropertyDataPropertyTypeEnum getType() {
        return type;
    }

    public void setType(PropertyDataPropertyTypeEnum type) {
        this.type = type;
    }

    public void setType(String inputType) {
        if (inputType.toLowerCase() == "semi-detached_house") {
            this.type = PropertyDataPropertyTypeEnum.semi_detached_house;
        } else {
            this.type = PropertyDataPropertyTypeEnum.valueOf(inputType);
        }
    }

    @Override
    public String toString() {
        return "PropertyDataRentRawDataPoint [bedrooms=" + bedrooms + ", lat=" + lat + ", lng=" + lng + ", price="
                + price + ", type=" + type + "]";
    }

}