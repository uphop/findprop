package com.lightson.findpropapi.loader.model;

public class TargetLocalAuthority {
    private String name;
    private String code;
    private String regionCode;

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public TargetLocalAuthority(String name, String code, String regionCode) {
        this.name = name;
        this.code = code;
        this.regionCode = regionCode;
    }

    public TargetLocalAuthority() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TargetLocalAuthority [code=" + code + ", name=" + name + ", regionCode=" + regionCode + "]";
    }
}
