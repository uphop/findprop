package com.lightson.findpropapi.loader.model;

public class TargetRelatedLocalAuthority {
    private String localAuthority;
    private String relatedLocalAuthority;

    public TargetRelatedLocalAuthority(String localAuthority, String relatedLocalAuthority) {
        this.localAuthority = localAuthority;
        this.relatedLocalAuthority = relatedLocalAuthority;
    }

    public TargetRelatedLocalAuthority() {
    }

    public String getLocalAuthority() {
        return localAuthority;
    }

    public void setLocalAuthority(String localAuthority) {
        this.localAuthority = localAuthority;
    }

    public String getRelatedLocalAuthority() {
        return relatedLocalAuthority;
    }

    public void setRelatedLocalAuthority(String relatedLocalAuthority) {
        this.relatedLocalAuthority = relatedLocalAuthority;
    }

    @Override
    public String toString() {
        return "TargetRelatedLocalAuthority [localAuthority=" + localAuthority + ", relatedLocalAuthority="
                + relatedLocalAuthority + "]";
    }

}
