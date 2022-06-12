package com.lightson.findpropapi.loader.model;

public class SourceRegion {
    private String RGN20CD;
    public String getRGN20CD() {
        return RGN20CD;
    }
    public void setRGN20CD(String rGN20CD) {
        RGN20CD = rGN20CD;
    }
    private String RGN20CDO;
    public String getRGN20CDO() {
        return RGN20CDO;
    }
    public void setRGN20CDO(String rGN20CDO) {
        RGN20CDO = rGN20CDO;
    }
    private String RGN20NM;
    public String getRGN20NM() {
        return RGN20NM;
    }
    public void setRGN20NM(String rGN20NM) {
        RGN20NM = rGN20NM;
    }
    private String RGN20NMW;
    public SourceRegion(String rGN20CD, String rGN20CDO, String rGN20NM, String rGN20NMW) {
        RGN20CD = rGN20CD;
        RGN20CDO = rGN20CDO;
        RGN20NM = rGN20NM;
        RGN20NMW = rGN20NMW;
    }
    public SourceRegion() {
    }
    public String getRGN20NMW() {
        return RGN20NMW;
    }
    public void setRGN20NMW(String rGN20NMW) {
        RGN20NMW = rGN20NMW;
    }
    @Override
    public String toString() {
        return "SourceRegion [RGN20CD=" + RGN20CD + ", RGN20CDO=" + RGN20CDO + ", RGN20NM=" + RGN20NM + ", RGN20NMW="
                + RGN20NMW + "]";
    }

    
}
