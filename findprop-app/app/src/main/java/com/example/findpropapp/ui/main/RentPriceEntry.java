package com.example.findpropapp.ui.main;

public class RentPriceEntry {
    private int priceMean;
    private int priceLow;
    private int priceHigh;
    private String label;
    private RentPriceEntryType type;
    private String description;

    public RentPriceEntry(int priceMean, int priceLow, int priceHigh, String label, RentPriceEntryType type, String description) {
        this.priceMean = priceMean;
        this.priceLow = priceLow;
        this.priceHigh = priceHigh;
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

    public RentPriceEntryType getType() {
        return type;
    }

    public void setType(RentPriceEntryType type) {
        this.type = type;
    }

    public int getPriceLow() {
        return priceLow;
    }

    public void setPriceLow(int priceLow) {
        this.priceLow = priceLow;
    }

    public int getPriceHigh() {
        return priceHigh;
    }

    public void setPriceHigh(int priceHigh) {
        this.priceHigh = priceHigh;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
