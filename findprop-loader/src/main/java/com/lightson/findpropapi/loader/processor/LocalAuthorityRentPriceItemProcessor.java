package com.lightson.findpropapi.loader.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.lightson.findpropapi.loader.model.SourceLocalAuthorityRentPrice;
import com.lightson.findpropapi.loader.model.TargetLocalAuthorityRentPrice;

@Component
public class LocalAuthorityRentPriceItemProcessor
        implements ItemProcessor<SourceLocalAuthorityRentPrice, TargetLocalAuthorityRentPrice> {

    @Override
    public TargetLocalAuthorityRentPrice process(SourceLocalAuthorityRentPrice source) throws Exception {
        TargetLocalAuthorityRentPrice target = new TargetLocalAuthorityRentPrice();
        target.setProperty_type(PropertyTypeHelper.PROPERTY_TYPE_MAP.get(source.getBedroomCategory()));
        target.setBedrooms(BedroomCountHelper.BEDROOM_COUNT_MAP.get(source.getBedroomCategory()));
        target.setPrice_count(source.getCountOfRents());
        target.setPrice_mean(source.getMean());
        target.setPrice_low(source.getLowerQuartile());
        target.setPrice_median(source.getMedian());
        target.setPrice_high(source.getUpperQuartile());
        target.setCurrency(source.getCurrency());
        target.setPeriod(source.getPeriod());
        target.setSource(source.getSource());
        target.setPublished(source.getPublished());
        target.setRecorded_from(source.getRecordedFrom());
        target.setRecorded_to(source.getRecordedTo());

        target.setLocal_authority(source.getLocalAuthority());

        return target;
    }
}
