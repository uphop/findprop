package com.lightson.findpropapi.loader.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.lightson.findpropapi.loader.model.SourceLocalAuthorityUtilityPrice;
import com.lightson.findpropapi.loader.model.TargetLocalAuthorityUtilityPrice;

@Component
public class LocalAuthorityUtilityPriceItemProcessor
        implements ItemProcessor<SourceLocalAuthorityUtilityPrice, TargetLocalAuthorityUtilityPrice> {

    @Override
    public TargetLocalAuthorityUtilityPrice process(SourceLocalAuthorityUtilityPrice source) throws Exception {
        TargetLocalAuthorityUtilityPrice target = new TargetLocalAuthorityUtilityPrice();
        target.setProperty_type(PropertyTypeHelper.PROPERTY_TYPE_MAP.get(source.getBedroomCategory()));
        target.setBedrooms(BedroomCountHelper.BEDROOM_COUNT_MAP.get(source.getBedroomCategory()));
        target.setUtility_type(source.getUtilityCategory());
        target.setPrice_mean(source.getMean());
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
