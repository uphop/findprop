package com.lightson.findpropapi.loader.model;

public class SourceLocalAuthority {
    private String LAD21CD;

    public String getLAD21CD() {
        return LAD21CD;
    }

    public void setLAD21CD(String lAD21CD) {
        LAD21CD = lAD21CD;
    }

    private String LAD21NM;

    public String getLAD21NM() {
        return LAD21NM;
    }

    public void setLAD21NM(String lAD21NM) {
        LAD21NM = lAD21NM;
    }

    private String LAD21NMW;

    public SourceLocalAuthority() {
    }

    public String getLAD21NMW() {
        return LAD21NMW;
    }

    public void setLAD21NMW(String lAD21NMW) {
        LAD21NMW = lAD21NMW;
    }

    private String RGN20CD;

    public SourceLocalAuthority(String lAD21CD, String lAD21NM, String lAD21NMW, String rGN20CD) {
        LAD21CD = lAD21CD;
        LAD21NM = lAD21NM;
        LAD21NMW = lAD21NMW;
        RGN20CD = rGN20CD;
    }

    public String getRGN20CD() {
        return RGN20CD;
    }

    public void setRGN20CD(String rGN20CD) {
        RGN20CD = rGN20CD;
    }

    @Override
    public String toString() {
        return "SourceLocalAuthority [LAD21CD=" + LAD21CD + ", LAD21NM=" + LAD21NM + ", LAD21NMW=" + LAD21NMW
                + ", RGN20CD="
                + RGN20CD + "]";
    }

}
