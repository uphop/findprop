package com.lightson.findpropapi.entity;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "local_authority_rent_price")
public class LocalAuthorityRentPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "property_type")
    @JsonIgnore
    private String propertyType;

    @Column(name = "bedrooms")
    @JsonIgnore
    private Integer bedrooms;

    @Column(name = "price_count")
    private Integer priceCount;

    @Column(name = "price_mean")
    private Integer priceMean;

    @Column(name = "price_low")
    private Integer priceLow;

    @Column(name = "price_median")
    private Integer priceMedian;

    @Column(name = "price_high")
    private Integer priceHigh;

    @Column(name = "currency")
    private String currency;

    @Column(name = "period")
    private String period;

    @Column(name = "source")
    @JsonIgnore
    private String source;

    @Column(name = "published")
    @JsonIgnore
    private Date published;

    @Column(name = "recorded_from")
    @JsonIgnore
    private Date recordedFrom;

    @Column(name = "recorded_to")
    @JsonIgnore
    private Date recordedTo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "local_authority_id", nullable = false)
    @JsonIgnore
    private LocalAuthority localAuthority;

    public LocalAuthorityRentPrice(Long id, String propertyType, Integer bedrooms, Integer priceCount, Integer priceMean,
            Integer priceLow, Integer priceMedian, Integer priceHigh, String currency, String period, String source,
            Date published, Date recordedFrom, Date recordedTo, LocalAuthority localAuthority) {
        this.id = id;
        this.propertyType = propertyType;
        this.bedrooms = bedrooms;
        this.priceCount = priceCount;
        this.priceMean = priceMean;
        this.priceLow = priceLow;
        this.priceMedian = priceMedian;
        this.priceHigh = priceHigh;
        this.currency = currency;
        this.period = period;
        this.source = source;
        this.published = published;
        this.recordedFrom = recordedFrom;
        this.recordedTo = recordedTo;
        this.localAuthority = localAuthority;
    }

    public LocalAuthorityRentPrice() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Integer getPriceCount() {
        return priceCount;
    }

    public void setPriceCount(Integer priceCount) {
        this.priceCount = priceCount;
    }

    public Integer getPriceMean() {
        return priceMean;
    }

    public void setPriceMean(Integer priceMean) {
        this.priceMean = priceMean;
    }

    public Integer getPriceLow() {
        return priceLow;
    }

    public void setPriceLow(Integer priceLow) {
        this.priceLow = priceLow;
    }

    public Integer getPriceMedian() {
        return priceMedian;
    }

    public void setPriceMedian(Integer priceMedian) {
        this.priceMedian = priceMedian;
    }

    public Integer getPriceHigh() {
        return priceHigh;
    }

    public void setPriceHigh(Integer priceHigh) {
        this.priceHigh = priceHigh;
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

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public Date getRecordedFrom() {
        return recordedFrom;
    }

    public void setRecordedFrom(Date recordedFrom) {
        this.recordedFrom = recordedFrom;
    }

    public Date getRecordedTo() {
        return recordedTo;
    }

    public void setRecordedTo(Date recordedTo) {
        this.recordedTo = recordedTo;
    }

    public LocalAuthority getLocalAuthority() {
        return localAuthority;
    }

    public void setLocalAuthority(LocalAuthority localAuthority) {
        this.localAuthority = localAuthority;
    }

    @Override
    public String toString() {
        return "RegionRentPrice [bedrooms=" + bedrooms + ", currency=" + currency + ", id=" + id + ", period=" + period
                + ", priceCount=" + priceCount + ", priceHigh=" + priceHigh + ", priceLow=" + priceLow + ", priceMean="
                + priceMean + ", priceMedian=" + priceMedian + ", propertyType=" + propertyType + ", published="
                + published + ", recordedFrom=" + recordedFrom + ", recordedTo=" + recordedTo + ", localAuthority=" + localAuthority
                + ", source=" + source + "]";
    }
}
