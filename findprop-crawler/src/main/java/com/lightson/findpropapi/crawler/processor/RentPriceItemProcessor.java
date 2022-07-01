package com.lightson.findpropapi.crawler.processor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lightson.findpropapi.crawler.adapter.PropertyDataAdapter;
import com.lightson.findpropapi.crawler.adapter.PropertyDataRentRawDataPoint;
import com.lightson.findpropapi.crawler.adapter.PropertyDataRentResponse;
import com.lightson.findpropapi.crawler.entity.Postcode;
import com.lightson.findpropapi.crawler.model.SourceRentPrice;
import com.lightson.findpropapi.crawler.model.TargetRentPrice;
import com.lightson.findpropapi.crawler.repository.PostcodeRepository;
import com.lightson.findpropapi.crawler.model.PostcodeAreaRentPrice;
import com.lightson.findpropapi.crawler.model.PostcodeRentPrice;
import com.lightson.findpropapi.crawler.model.PropertyTypeEnum;

@Component
public class RentPriceItemProcessor
        implements ItemProcessor<SourceRentPrice, TargetRentPrice> {

    private final String SOURCE = "property_data";

    @Autowired
    PropertyDataAdapter pdAdapter;

    @Autowired
    private PostcodeRepository postcodeRepository;

    @Autowired
    private RentPriceItemProcessorProperties config;

    private final Logger log = LoggerFactory.getLogger(RentPriceItemProcessor.class);

    @Override
    public TargetRentPrice process(SourceRentPrice source) throws Exception {
        // convert filters
        PropertyTypeEnum propertyType = PropertyTypeHelper.PROPERTY_TYPE_MAP.get(source.getBedroomCategory());
        Integer bedrooms = BedroomCountHelper.BEDROOM_COUNT_MAP.get(source.getBedroomCategory());

        // call PD API
        PropertyDataRentResponse pdResponse = pdAdapter.getRentPrices(source.getPostcodeArea(), propertyType.toString(),
                bedrooms);

        // skip item on error
        if (pdResponse == null) {
            return null;
        }

        // prepare wrapper response, with both postcode area / postcode prices
        TargetRentPrice target = new TargetRentPrice();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = dateFormat.format(new Date());

        // map postcode area rent price
        PostcodeAreaRentPrice paRentPrice = new PostcodeAreaRentPrice();
        paRentPrice.setPostcodeArea(source.getPostcodeArea());
        paRentPrice.setBedroomCategory(source.getBedroomCategory());
        paRentPrice.setCountOfRents(pdResponse.getRaw_data().length);
        paRentPrice.setMean(pdResponse.getAverage());
        paRentPrice.setLowerQuartile(pdResponse.get100pc_range()[0]);
        paRentPrice.setMedian(pdResponse.getAverage());
        paRentPrice.setUpperQuartile(pdResponse.get100pc_range()[1]);
        paRentPrice.setCurrency(UnitTypeHelper.getCurrency(pdResponse.getUnit()));
        paRentPrice.setPeriod(UnitTypeHelper.getPeriod(pdResponse.getUnit()));
        paRentPrice.setSource(SOURCE);
        paRentPrice.setPublished(dateStr);
        paRentPrice.setRecordedFrom(dateStr);
        paRentPrice.setRecordedTo(dateStr);
        // add postcode area rent price to wrapper
        target.setPostcodeAreaRentPrice(paRentPrice);

        // map postcode rent prices
        List<PostcodeRentPrice> psRentPrices = new ArrayList<PostcodeRentPrice>();
        for (PropertyDataRentRawDataPoint sourceDataPoint : pdResponse.getRaw_data()) {
            PostcodeRentPrice targetDataPoint = new PostcodeRentPrice();

            // find nearest postcode
            List<Postcode> postcodes = this.postcodeRepository.findByDistance(sourceDataPoint.getLng(),
                    sourceDataPoint.getLat(), config.getMax_range());
            if (postcodes == null || postcodes.size() == 0) {
                // skip postcode prices if not able to determine postcode by location
                log.error(String.format(
                        "Cannot find nearest postcode for longitude %f, latitude %f, maxRange %f",
                        sourceDataPoint.getLng(), sourceDataPoint.getLat(), config.getMax_range()));

                continue;
            }

            // get closest postcode
            Postcode postcode = postcodes.get(0);
            
            if (!postcode.getCode().startsWith(source.getPostcodeArea()) && config.getSkip_non_target_postcode_area()) {
                // skip postcode price for postcodes which seem not to be in this area
                log.error(String.format(
                        "Skipping postcode %s, not in the target postcode area",
                        postcode.getCode()));

                continue;
            }

            targetDataPoint.setPostcode(postcode.getCode());
            targetDataPoint.setBedroomCategory(source.getBedroomCategory());
            targetDataPoint.setCountOfRents(1);
            targetDataPoint.setMean(sourceDataPoint.getPrice());
            targetDataPoint.setLowerQuartile(sourceDataPoint.getPrice());
            targetDataPoint.setMedian(sourceDataPoint.getPrice());
            targetDataPoint.setUpperQuartile(sourceDataPoint.getPrice());
            targetDataPoint.setCurrency(UnitTypeHelper.getCurrency(pdResponse.getUnit()));
            targetDataPoint.setPeriod(UnitTypeHelper.getPeriod(pdResponse.getUnit()));
            targetDataPoint.setSource(SOURCE);
            targetDataPoint.setPublished(dateStr);
            targetDataPoint.setRecordedFrom(dateStr);
            targetDataPoint.setRecordedTo(dateStr);
            psRentPrices.add(targetDataPoint);
        }
        // add postcode rent prices to wrapper
        target.setPostcodeRentPrices(psRentPrices);

        return target;
    }
}
