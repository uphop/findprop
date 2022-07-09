package com.lightson.findpropapp.ui.main;

public class RentCostEntry {
    private int priceMean;
    private String label;
    private RentCostEntryType type;
    private String description;

    public RentCostEntry() {

    }

    public RentCostEntry(int priceMean, String label, RentCostEntryType type, String description) {
        this.priceMean = priceMean;
        this.label = label;
        this.type = type;
        this.description = description;
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

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFormattedLabel() {
        return String.format("%s (%s)", this.getType().getDisplayName(), this.getLabel());
    }

    public RentCostEntryType getType() {
        return type;
    }

    public void setType(RentCostEntryType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
