package com.lightson.findpropapi.loader.processor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.lightson.findpropapi.loader.model.SourceRegionRentPrice;
import com.lightson.findpropapi.loader.model.TargetRegionRentPrice;

@Component
public class RegionRentPriceItemProcessor implements ItemProcessor<SourceRegionRentPrice, TargetRegionRentPrice> {

    @Override
    public TargetRegionRentPrice process(SourceRegionRentPrice source) throws Exception {
        TargetRegionRentPrice target = new TargetRegionRentPrice();
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

        target.setRegion(source.getRegion());

        return target;
    }
}
