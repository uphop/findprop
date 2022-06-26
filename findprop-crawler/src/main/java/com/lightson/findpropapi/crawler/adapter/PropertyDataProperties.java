package com.lightson.findpropapi.crawler.adapter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "propertydata.api")
public class PropertyDataProperties {
    private String base_url;
    private String key;
    private Integer connect_timeout;
    public String getBase_url() {
        return base_url;
    }
    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public Integer getConnect_timeout() {
        return connect_timeout;
    }
    public void setConnect_timeout(Integer connect_timeout) {
        this.connect_timeout = connect_timeout;
    }
}
