package com.lightson.findpropapi.loader.model;

public class TargetLocalAuthorityUtilityPrice {
    private String property_type;
    private Integer bedrooms;
    private String utility_type;
    private Integer price_mean;
    private String currency;
    private String period;
    private String source;
    private String published;
    private String recorded_from;
    private String recorded_to;
    private String local_authority;
    public TargetLocalAuthorityUtilityPrice(String property_type, Integer bedrooms, String utility_type,
            Integer price_mean, String currency, String period, String source, String published, String recorded_from,
            String recorded_to, String local_authority) {
        this.property_type = property_type;
        this.bedrooms = bedrooms;
        this.utility_type = utility_type;
        this.price_mean = price_mean;
        this.currency = currency;
        this.period = period;
        this.source = source;
        this.published = published;
        this.recorded_from = recorded_from;
        this.recorded_to = recorded_to;
        this.local_authority = local_authority;
    }
    public TargetLocalAuthorityUtilityPrice() {
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
    public String getUtility_type() {
        return utility_type;
    }
    public void setUtility_type(String utility_type) {
        this.utility_type = utility_type;
    }
    public Integer getPrice_mean() {
        return price_mean;
    }
    public void setPrice_mean(Integer price_mean) {
        this.price_mean = price_mean;
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
    public String getLocal_authority() {
        return local_authority;
    }
    public void setLocal_authority(String local_authority) {
        this.local_authority = local_authority;
    }
    @Override
    public String toString() {
        return "TargetLocalAuthorityUtilityPrice [bedrooms=" + bedrooms + ", currency=" + currency
                + ", local_authority=" + local_authority + ", period=" + period + ", price_mean=" + price_mean
                + ", property_type=" + property_type + ", published=" + published + ", recorded_from=" + recorded_from
                + ", recorded_to=" + recorded_to + ", source=" + source + ", utility_type=" + utility_type + "]";
    }
    
    
    
}
