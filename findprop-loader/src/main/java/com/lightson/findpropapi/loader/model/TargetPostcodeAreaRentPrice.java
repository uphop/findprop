package com.lightson.findpropapi.loader.model;

public class TargetPostcodeAreaRentPrice {
    private String property_type;
    private Integer bedrooms;
    private Integer price_count;
    private Integer price_mean;
    private Integer price_low;
    private Integer price_median;
    private Integer price_high;
    private String currency;
    private String period;
    private String source;
    private String published;
    private String recorded_from;
    private String recorded_to;
    private String postcode_area;
    public TargetPostcodeAreaRentPrice(String property_type, Integer bedrooms, Integer price_count, Integer price_mean,
            Integer price_low, Integer price_median, Integer price_high, String currency, String period, String source,
            String published, String recorded_from, String recorded_to, String postcode_area) {
        this.property_type = property_type;
        this.bedrooms = bedrooms;
        this.price_count = price_count;
        this.price_mean = price_mean;
        this.price_low = price_low;
        this.price_median = price_median;
        this.price_high = price_high;
        this.currency = currency;
        this.period = period;
        this.source = source;
        this.published = published;
        this.recorded_from = recorded_from;
        this.recorded_to = recorded_to;
        this.postcode_area = postcode_area;
    }
    public TargetPostcodeAreaRentPrice() {
    }
    public String getProperty_type() {
        return property_type;
    }
    public void setProperty_type(String property_type) {
        this.property_type = property_type;
    }
    public Integer getBedrooms() {
        return bedrooms;
    }
    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }
    public Integer getPrice_count() {
        return price_count;
    }
    public void setPrice_count(Integer price_count) {
        this.price_count = price_count;
    }
    public Integer getPrice_mean() {
        return price_mean;
    }
    public void setPrice_mean(Integer price_mean) {
        this.price_mean = price_mean;
    }
    public Integer getPrice_low() {
        return price_low;
    }
    public void setPrice_low(Integer price_low) {
        this.price_low = price_low;
    }
    public Integer getPrice_median() {
        return price_median;
    }
    public void setPrice_median(Integer price_median) {
        this.price_median = price_median;
    }
    public Integer getPrice_high() {
        return price_high;
    }
    public void setPrice_high(Integer price_high) {
        this.price_high = price_high;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getPeriod() {
        return period;
    }
    public void setPeriod(String period) {
        this.period = period;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getPublished() {
        return published;
    }
    public void setPublished(String published) {
        this.published = published;
    }
    public String getRecorded_from() {
        return recorded_from;
    }
    public void setRecorded_from(String recorded_from) {
        this.recorded_from = recorded_from;
    }
    public String getRecorded_to() {
        return recorded_to;
    }
    public void setRecorded_to(String recorded_to) {
        this.recorded_to = recorded_to;
    }
    public String getPostcode_area() {
        return postcode_area;
    }
    public void setPostcode_area(String postcode_area) {
        this.postcode_area = postcode_area;
    }
    @Override
    public String toString() {
        return "TargetPostcodeAreaRentPrice [bedrooms=" + bedrooms + ", currency=" + currency + ", period=" + period
                + ", price_count=" + price_count + ", price_high=" + price_high + ", price_low=" + price_low
                + ", price_mean=" + price_mean + ", price_median=" + price_median + ", property_type=" + property_type
                + ", published=" + published + ", recorded_from=" + recorded_from + ", recorded_to=" + recorded_to
                + ", postcode_area=" + postcode_area + ", source=" + source + "]";
    }
    
}
