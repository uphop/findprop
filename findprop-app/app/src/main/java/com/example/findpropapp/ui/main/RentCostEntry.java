package com.example.findpropapp.ui.main;

import com.example.findpropapp.model.UpfrontFeeEnum;
import com.example.findpropapp.model.UtilityFeeEnum;

public class RentCostEntry {
    private int priceMean;
    private String label;
    private RentCostEntryType type;
    private UtilityFeeEnum utilityFee;
    private UpfrontFeeEnum upfrontFee;

    public RentCostEntry(int priceMean, String label, RentCostEntryType type) {
        this.priceMean = priceMean;
        this.label = label;
        this.type = type;
    }

    public RentCostEntry(int priceMean, String label, RentCostEntryType type, UtilityFeeEnum utilityFee) {
        this.priceMean = priceMean;
        this.label = label;
        this.type = type;
        this.utilityFee = utilityFee;
    }

    public RentCostEntry(int priceMean, String label, RentCostEntryType type, UpfrontFeeEnum upfrontFee) {
        this.priceMean = priceMean;
        this.label = label;
        this.type = type;
        this.upfrontFee = upfrontFee;
    }

    public int getPriceMean() {
        return priceMean;
    }

    public void setPriceMean(int priceMean) {
        this.priceMean = priceMean;
    }

    public String getLabel() {
        return label;
    }

    public String getFormattedLabel() {
        return String.format("%s (%s)", this.getType().getDisplayName(), this.getLabel());
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setType(RentCostEntryType type) {this.type = type;}

    public RentCostEntryType getType() { return type;}

    public UtilityFeeEnum getUtilityFee() {
        return utilityFee;
    }

    public void setUtilityFee(UtilityFeeEnum utilityFee) {
        this.utilityFee = utilityFee;
    }

    public UpfrontFeeEnum getUpfrontFee() {
        return upfrontFee;
    }

    public void setUpfrontFee(UpfrontFeeEnum upfrontFee) {
        this.upfrontFee = upfrontFee;
    }
}
