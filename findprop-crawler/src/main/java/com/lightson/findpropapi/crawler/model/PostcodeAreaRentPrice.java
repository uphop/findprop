package com.lightson.findpropapi.crawler.model;

public class PostcodeAreaRentPrice {
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
    private String postcodeArea;
    public PostcodeAreaRentPrice(String bedroomCategory, Integer countOfRents, Integer mean, Integer lowerQuartile,
            Integer median, Integer upperQuartile, CurrencyEnum currency, PeriodEnum period, String source,
            String published, String recordedFrom, String recordedTo, String postcodeArea) {
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
        this.postcodeArea = postcodeArea;
    }
    public PostcodeAreaRentPrice() {
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
    public String getPostcodeArea() {
        return postcodeArea;
    }
    public void setPostcodeArea(String postcodeArea) {
        this.postcodeArea = postcodeArea;
    }
    @Override
    public String toString() {
        return "PostcodeAreaRentPrice [bedroomCategory=" + bedroomCategory + ", countOfRents=" + countOfRents
                + ", currency=" + currency + ", lowerQuartile=" + lowerQuartile + ", mean=" + mean + ", median="
                + median + ", period=" + period + ", postcodeArea=" + postcodeArea + ", published=" + published
                + ", recordedFrom=" + recordedFrom + ", recordedTo=" + recordedTo + ", source=" + source
                + ", upperQuartile=" + upperQuartile + "]";
    }
   

}
