package com.lightson.findpropapi.crawler.processor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rentpriceitemprocessor")
public class RentPriceItemProcessorProperties {
    private Boolean skip_non_target_postcode_area;
    private Double max_range;
    
    public Boolean getSkip_non_target_postcode_area() {
        return skip_non_target_postcode_area;
    }
    public void setSkip_non_target_postcode_area(Boolean skip_non_target_postcode_area) {
        this.skip_non_target_postcode_area = skip_non_target_postcode_area;
    }
    public Double getMax_range() {
        return max_range;
    }
    public void setMax_range(Double max_range) {
        this.max_range = max_range;
    }
}
