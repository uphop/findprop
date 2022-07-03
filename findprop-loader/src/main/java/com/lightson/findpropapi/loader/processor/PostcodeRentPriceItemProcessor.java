package com.lightson.findpropapi.loader.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.lightson.findpropapi.loader.model.SourcePostcodeRentPrice;
import com.lightson.findpropapi.loader.model.TargetPostcodeRentPrice;

@Component
public class PostcodeRentPriceItemProcessor
        implements ItemProcessor<SourcePostcodeRentPrice, TargetPostcodeRentPrice> {

    @Override
    public TargetPostcodeRentPrice process(SourcePostcodeRentPrice source) throws Exception {
        TargetPostcodeRentPrice target = new TargetPostcodeRentPrice();
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
        target.setPostcode(source.getPostcode());

        return target;
    }
}
