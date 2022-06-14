package com.lightson.findpropapi.entity;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "local_authority_utility_price")
public class LocalAuthorityUtilityPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "property_type")
    private String propertyType;

    @Column(name = "bedrooms")
    private Integer bedrooms;

    @Column(name = "utility_type")
    private String utilityType;

    @Column(name = "price_mean")
    private Integer priceMean;

    @Column(name = "currency")
    private String currency;

    @Column(name = "period")
    private String period;

    @Column(name = "source")
    private String source;

    @Column(name = "published")
    private Date published;

    @Column(name = "recorded_from")
    private Date recordedFrom;

    @Column(name = "recorded_to")
    private Date recordedTo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "local_authority_id", nullable = false)
    private LocalAuthority localAuthority;

    public LocalAuthorityUtilityPrice(Long id, String propertyType, Integer bedrooms, String utilityType,
            Integer priceMean, String currency, String period, String source, Date published, Date recordedFrom,
            Date recordedTo, LocalAuthority localAuthority) {
        this.id = id;
        this.propertyType = propertyType;
        this.bedrooms = bedrooms;
        this.utilityType = utilityType;
        this.priceMean = priceMean;
        this.currency = currency;
        this.period = period;
        this.source = source;
        this.published = published;
        this.recordedFrom = recordedFrom;
        this.recordedTo = recordedTo;
        this.localAuthority = localAuthority;
    }

    public LocalAuthorityUtilityPrice() {
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

    public String getUtilityType() {
        return utilityType;
    }

    public void setUtilityType(String utilityType) {
        this.utilityType = utilityType;
    }

    public Integer getPriceMean() {
        return priceMean;
    }

    public void setPriceMean(Integer priceMean) {
        this.priceMean = priceMean;
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
        return "LocalAuthorityUtilityPrice [bedrooms=" + bedrooms + ", currency=" + currency + ", id=" + id
                + ", localAuthority=" + localAuthority + ", period=" + period + ", priceMean=" + priceMean
                + ", propertyType=" + propertyType + ", published=" + published + ", recordedFrom=" + recordedFrom
                + ", recordedTo=" + recordedTo + ", source=" + source + ", utilityType=" + utilityType + "]";
    }


   
}
