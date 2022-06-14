package com.lightson.findpropapi.loader.model;

public class SourceLocalAuthorityUtilityPrice {
    private String localAuthority;
    private String bedroomCategory;
    private String utilityCategory;
    private Integer mean;
    private String currency;
    private String period;
    private String source;
    private String published;
    private String recordedFrom;
    private String recordedTo;
    public SourceLocalAuthorityUtilityPrice(String localAuthority, String bedroomCategory, String utilityCategory,
            Integer mean, String currency, String period, String source, String published, String recordedFrom,
            String recordedTo) {
        this.localAuthority = localAuthority;
        this.bedroomCategory = bedroomCategory;
        this.utilityCategory = utilityCategory;
        this.mean = mean;
        this.currency = currency;
        this.period = period;
        this.source = source;
        this.published = published;
        this.recordedFrom = recordedFrom;
        this.recordedTo = recordedTo;
    }
    public SourceLocalAuthorityUtilityPrice() {
    }
    public String getLocalAuthority() {
        return localAuthority;
    }
    public void setLocalAuthority(String localAuthority) {
        this.localAuthority = localAuthority;
    }
    public String getBedroomCategory() {
        return bedroomCategory;
    }
    public void setBedroomCategory(String bedroomCategory) {
        this.bedroomCategory = bedroomCategory;
    }
    public String getUtilityCategory() {
        return utilityCategory;
    }
    public void setUtilityCategory(String utilityCategory) {
        this.utilityCategory = utilityCategory;
    }
    public Integer getMean() {
        return mean;
    }
    public void setMean(Integer mean) {
        this.mean = mean;
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
    public String getRecordedFrom() {
        return recordedFrom;
    }
    public void setRecordedFrom(String recordedFrom) {
        this.recordedFrom = recordedFrom;
    }
    public String getRecordedTo() {
        return recordedTo;
    }
    public void setRecordedTo(String recordedTo) {
        this.recordedTo = recordedTo;
    }
    @Override
    public String toString() {
        return "SourceLocalAuthorityUtilityPrice [bedroomCategory=" + bedroomCategory + ", currency=" + currency
                + ", localAuthority=" + localAuthority + ", mean=" + mean + ", period=" + period + ", published="
                + published + ", recordedFrom=" + recordedFrom + ", recordedTo=" + recordedTo + ", source=" + source
                + ", utilityCategory=" + utilityCategory + "]";
    }
   
    
}
