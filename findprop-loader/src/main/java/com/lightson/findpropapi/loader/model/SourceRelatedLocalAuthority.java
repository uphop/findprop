package com.lightson.findpropapi.loader.model;

public class SourceRelatedLocalAuthority {
    private String localAuthority;
    private String relatedLocalAuthority;

    public SourceRelatedLocalAuthority(String localAuthority, String relatedLocalAuthority) {
        this.localAuthority = localAuthority;
        this.relatedLocalAuthority = relatedLocalAuthority;
    }

    public SourceRelatedLocalAuthority() {
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
        return "SourceRelatedLocalAuthority [localAuthority=" + localAuthority
                + ", relatedLocalAuthority=" + relatedLocalAuthority + "]";
    }
}
