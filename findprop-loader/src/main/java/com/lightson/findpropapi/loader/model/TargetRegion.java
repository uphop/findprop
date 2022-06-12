package com.lightson.findpropapi.loader.model;

public class TargetRegion {
    private String name;
    private String code;
    
    public TargetRegion(String name, String code) {
        this.name = name;
        this.code = code;
    }
    public TargetRegion() {
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
        return "Region [code=" + code + ", name=" + name + "]";
    }
}
