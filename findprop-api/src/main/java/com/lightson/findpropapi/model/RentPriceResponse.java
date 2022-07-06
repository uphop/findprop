package com.lightson.findpropapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lightson.findpropapi.entity.LocalAuthorityRentPrice;
import com.lightson.findpropapi.entity.PostcodeRentPrice;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RentPriceResponse implements Serializable {

    private Date timestamp;
    private Long duration;

    public enum StatusEnum {
        OK,
        FAILURE
    }

    private StatusEnum status;
    private int code;
    private double longitude;
    private double latitude;
    private double maxRange;
    private RentPricePropertyTypeEnum propertyType;
    private int bedrooms;

    private RentPricePostcodeDetails postcodeDetails;
    private List<RentPricePostcodeDetails> relatedPostcodeDetails;
    private RentPriceRegionDetails regionDetails;
    private RentPriceLocalAuthorityDetails localAuthorityDetails;
    private UtilityPriceLocalAuthorityDetails utilityDetails;
    private UpfrontPriceCompositeDetails upfrontDetails;
    private List<RentPriceLocalAuthorityDetails> relatedLocalAuthorityDetails;
    private List<RentPriceLocalAuthorityDetails> similarLocalAuthorityDetails;
    private RentPricePostcodeAreaDetails postcodeAreaDetails;

    public RentPriceResponse() {
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(double maxRange) {
        this.maxRange = maxRange;
    }

    public RentPricePropertyTypeEnum getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(RentPricePropertyTypeEnum propertyType) {
        this.propertyType = propertyType;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public RentPriceRegionDetails getRegionDetails() {
        return regionDetails;
    }

    public void setRegionDetails(RentPriceRegionDetails regionDetails) {
        this.regionDetails = regionDetails;
    }

    public RentPriceLocalAuthorityDetails getLocalAuthorityDetails() {
        return localAuthorityDetails;
    }

    public void setLocalAuthorityDetails(RentPriceLocalAuthorityDetails localAuthorityDetails) {
        this.localAuthorityDetails = localAuthorityDetails;
    }

    public RentPricePostcodeAreaDetails getPostcodeAreaDetails() {
        return postcodeAreaDetails;
    }

    public void setPostcodeAreaDetails(RentPricePostcodeAreaDetails postcodeAreaDetails) {
        this.postcodeAreaDetails = postcodeAreaDetails;
    }

    public List<RentPriceLocalAuthorityDetails> getRelatedLocalAuthorityDetails() {
        return relatedLocalAuthorityDetails;
    }

    public void setRelatedLocalAuthorityDetails(List<RentPriceLocalAuthorityDetails> relatedLocalAuthorityDetails) {
        this.relatedLocalAuthorityDetails = relatedLocalAuthorityDetails;
    }

    public void addRelatedLocalAuthorityDetails(String localAuthority, LocalAuthorityRentPrice inputPrice) {
        if (this.relatedLocalAuthorityDetails == null) {
            this.relatedLocalAuthorityDetails = new ArrayList<RentPriceLocalAuthorityDetails>();
        }
        this.relatedLocalAuthorityDetails.add(new RentPriceLocalAuthorityDetails(localAuthority, inputPrice));
    }

    public List<RentPriceLocalAuthorityDetails> getSimilarLocalAuthorityDetails() {
        return similarLocalAuthorityDetails;
    }

    public void setSimilarLocalAuthorityDetails(List<RentPriceLocalAuthorityDetails> similarLocalAuthorityDetails) {
        this.similarLocalAuthorityDetails = similarLocalAuthorityDetails;
    }

    public void addSimilarLocalAuthorityDetails(String localAuthority, LocalAuthorityRentPrice inputPrice) {
        if (this.similarLocalAuthorityDetails == null) {
            this.similarLocalAuthorityDetails = new ArrayList<RentPriceLocalAuthorityDetails>();
        }
        this.similarLocalAuthorityDetails.add(new RentPriceLocalAuthorityDetails(localAuthority, inputPrice));
    }

    public RentPricePostcodeDetails getPostcodeDetails() {
        return postcodeDetails;
    }

    public void setPostcodeDetails(RentPricePostcodeDetails postcodeDetails) {
        this.postcodeDetails = postcodeDetails;
    }

    public void addRelatedPostcodeDetails(String postcode, double longitude, double latitude, PostcodeRentPrice inputPrice) {
        if (this.relatedPostcodeDetails == null) {
            this.relatedPostcodeDetails = new ArrayList<RentPricePostcodeDetails>();
        }
        this.relatedPostcodeDetails.add(new RentPricePostcodeDetails(postcode, longitude, latitude, inputPrice));
    }

    public UtilityPriceLocalAuthorityDetails getUtilityDetails() {
        return utilityDetails;
    }

    public void setUtilityDetails(UtilityPriceLocalAuthorityDetails utilityDetails) {
        this.utilityDetails = utilityDetails;
    }

    public UpfrontPriceCompositeDetails getUpfrontDetails() {
        return upfrontDetails;
    }

    public void setUpfrontDetails(UpfrontPriceCompositeDetails upfrontDetails) {
        this.upfrontDetails = upfrontDetails;
    }

    public List<RentPricePostcodeDetails> getRelatedPostcodeDetails() {
        return relatedPostcodeDetails;
    }

    public void setRelatedPostcodeDetails(List<RentPricePostcodeDetails> relatedPostcodeDetails) {
        this.relatedPostcodeDetails = relatedPostcodeDetails;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
