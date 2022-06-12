package com.lightson.findpropapi.loader.model;

public class SourceRegionRentPrice {
    private String region;
    private String bedroomCategory;
    private Integer countOfRents;
    private Integer mean;
    private Integer lowerQuartile;
    private Integer median;
    private Integer upperQuartile;
    private String currency;
    private String period;
    private String source;
    private String published;
    private String recordedFrom;
    private String recordedTo;
    public SourceRegionRentPrice(String region, String bedroomCategory, Integer countOfRents, Integer mean,
            Integer lowerQuartile, Integer median, Integer upperQuartile, String currency, String period, String source,
            String published, String recordedFrom, String recordedTo) {
        this.region = region;
        this.bedroomCategory = bedroomCategory;
        this.countOfRents = countOfRents;
        this.mean = mean;
        this.lowerQuartile = lowerQuartile;
        this.median = median;
        this.upperQuartile = upperQuartile;
        this.currency = currency;
        this.period = period;
        this.source = source;
        this.published = published;
        this.recordedFrom = recordedFrom;
        this.recordedTo = recordedTo;
    }
    public SourceRegionRentPrice() {
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    public String getBedroomCategory() {
        return bedroomCategory;
    }
    public void setBedroomCategory(String bedroomCategory) {
        this.bedroomCategory = bedroomCategory;
    }
    public Integer getCountOfRents() {
        return countOfRents;
    }
    public void setCountOfRents(Integer countOfRents) {
        this.countOfRents = countOfRents;
    }
    public Integer getMean() {
        return mean;
    }
    public void setMean(Integer mean) {
        this.mean = mean;
    }
    public Integer getLowerQuartile() {
        return lowerQuartile;
    }
    public void setLowerQuartile(Integer lowerQuartile) {
        this.lowerQuartile = lowerQuartile;
    }
    public Integer getMedian() {
        return median;
    }
    public void setMedian(Integer median) {
        this.median = median;
    }
    public Integer getUpperQuartile() {
        return upperQuartile;
    }
    public void setUpperQuartile(Integer upperQuartile) {
        this.upperQuartile = upperQuartile;
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
        return "SourceRegionRentPrice [bedroomCategory=" + bedroomCategory + ", countOfRents=" + countOfRents
                + ", currency=" + currency + ", lowerQuartile=" + lowerQuartile + ", mean=" + mean + ", median="
                + median + ", period=" + period + ", published=" + published + ", recordedFrom=" + recordedFrom
                + ", recordedTo=" + recordedTo + ", region=" + region + ", source=" + source + ", upperQuartile="
                + upperQuartile + "]";
    }

   
}
