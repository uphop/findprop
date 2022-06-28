package com.example.findpropapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private UpfrontPriceLocalAuthorityDetails upfrontDetails;
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

    public List<RentPriceLocalAuthorityDetails> getSimilarLocalAuthorityDetails() {
        return similarLocalAuthorityDetails;
    }

    public void setSimilarLocalAuthorityDetails(List<RentPriceLocalAuthorityDetails> similarLocalAuthorityDetails) {
        this.similarLocalAuthorityDetails = similarLocalAuthorityDetails;
    }

    public RentPricePostcodeDetails getPostcodeDetails() {
        return postcodeDetails;
    }

    public void setPostcodeDetails(RentPricePostcodeDetails postcodeDetails) {
        this.postcodeDetails = postcodeDetails;
    }

    public UtilityPriceLocalAuthorityDetails getUtilityDetails() {
        return utilityDetails;
    }

    public void setUtilityDetails(UtilityPriceLocalAuthorityDetails utilityDetails) {
        this.utilityDetails = utilityDetails;
    }

    public UpfrontPriceLocalAuthorityDetails getUpfrontDetails() {
        return upfrontDetails;
    }

    public void setUpfrontDetails(UpfrontPriceLocalAuthorityDetails upfrontDetails) {
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
