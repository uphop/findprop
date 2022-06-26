package com.lightson.findpropapi.crawler.model;

public class PostcodeRentPrice {
    private String bedroomCategory;
    private Integer countOfRents;
    private Integer mean;
    private Integer lowerQuartile;
    private Integer median;
    private Integer upperQuartile;
    private CurrencyEnum currency;
    private PeriodEnum period;
    private String source;
    private String published;
    private String recordedFrom;
    private String recordedTo;
    private String postcode;
    private Double longitude;
    private Double latitude;
    public PostcodeRentPrice(String bedroomCategory, Integer countOfRents, Integer mean, Integer lowerQuartile,
            Integer median, Integer upperQuartile, CurrencyEnum currency, PeriodEnum period, String source,
            String published, String recordedFrom, String recordedTo, String postcode, Double longitude,
            Double latitude) {
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
        this.postcode = postcode;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public PostcodeRentPrice() {
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
    public CurrencyEnum getCurrency() {
        return currency;
    }
    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }
    public PeriodEnum getPeriod() {
        return period;
    }
    public void setPeriod(PeriodEnum period) {
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
    public String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    @Override
    public String toString() {
        return "PostcodeRentPrice [bedroomCategory=" + bedroomCategory + ", countOfRents=" + countOfRents
                + ", currency=" + currency + ", latitude=" + latitude + ", longitude=" + longitude + ", lowerQuartile="
                + lowerQuartile + ", mean=" + mean + ", median=" + median + ", period=" + period + ", postcode="
                + postcode + ", published=" + published + ", recordedFrom=" + recordedFrom + ", recordedTo="
                + recordedTo + ", source=" + source + ", upperQuartile=" + upperQuartile + "]";
    }
   
}
   
