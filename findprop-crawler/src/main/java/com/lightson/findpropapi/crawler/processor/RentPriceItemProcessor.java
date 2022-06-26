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
    private final Double MAX_RANGE = 250.0;
    private final Integer PD_API_CALL_DELAY = 500;

    @Autowired
    PropertyDataAdapter pdAdapter;

    @Autowired
    private PostcodeRepository postcodeRepository;

    private final Logger log = LoggerFactory.getLogger(RentPriceItemProcessor.class);

    @Override
    public TargetRentPrice process(SourceRentPrice source) throws Exception {
        // convert filters
        PropertyTypeEnum propertyType = PropertyTypeHelper.PROPERTY_TYPE_MAP.get(source.getBedroomCategory());
        Integer bedrooms = BedroomCountHelper.BEDROOM_COUNT_MAP.get(source.getBedroomCategory());
        

        // call PD API
        TimeUnit.MILLISECONDS.sleep(PD_API_CALL_DELAY);
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
        paRentPrice.setPostcode_area(source.getPostcodeArea());
        paRentPrice.setProperty_type(propertyType);
        paRentPrice.setBedrooms(bedrooms);
        paRentPrice.setPrice_count(pdResponse.getRaw_data().length);
        paRentPrice.setPrice_mean(pdResponse.getAverage());
        paRentPrice.setPrice_low(pdResponse.get100pc_range()[0]);
        paRentPrice.setPrice_median(pdResponse.getAverage());
        paRentPrice.setPrice_high(pdResponse.get100pc_range()[1]);
        paRentPrice.setCurrency(UnitTypeHelper.getCurrency(pdResponse.getUnit()));
        paRentPrice.setPeriod(UnitTypeHelper.getPeriod(pdResponse.getUnit()));
        paRentPrice.setSource(SOURCE);
        paRentPrice.setPublished(dateStr);
        paRentPrice.setRecorded_from(dateStr);
        paRentPrice.setRecorded_to(dateStr);
        // add postcode area rent price to wrapper
        target.setPostcodeAreaRentPrice(paRentPrice);

        // map postcode rent prices
        List<PostcodeRentPrice> psRentPrices = new ArrayList<PostcodeRentPrice>();
        for (PropertyDataRentRawDataPoint sourceDataPoint : pdResponse.getRaw_data()) {
            PostcodeRentPrice targetDataPoint = new PostcodeRentPrice();

            // find nearest postcode
            Postcode postcode = this.postcodeRepository.findByDistance(sourceDataPoint.getLng(),
                    sourceDataPoint.getLat(), MAX_RANGE);
            if (postcode == null) {
                // skip postcode prices if not able to determine postcode by location
                log.error(String.format(
                        "Cannot find nearest postcode for longitude %f, latitude %f, maxRange %f",
                        sourceDataPoint.getLng(), sourceDataPoint.getLat(), MAX_RANGE));

                continue;
            }

            if(!postcode.getCode().startsWith(source.getPostcodeArea())) {
                // skip postcode price for postcodes which seem not to be in this area
                continue;
            }

            targetDataPoint.setPostcode(postcode.getCode());
            targetDataPoint.setLongitude(sourceDataPoint.getLng());
            targetDataPoint.setLatitude(sourceDataPoint.getLat());
            targetDataPoint.setProperty_type(propertyType);
            targetDataPoint.setPrice_count(1);
            targetDataPoint.setPrice_mean(sourceDataPoint.getPrice());
            targetDataPoint.setPrice_low(sourceDataPoint.getPrice());
            targetDataPoint.setPrice_median(sourceDataPoint.getPrice());
            targetDataPoint.setPrice_high(sourceDataPoint.getPrice());
            targetDataPoint.setBedrooms(sourceDataPoint.getBedrooms());
            targetDataPoint.setCurrency(UnitTypeHelper.getCurrency(pdResponse.getUnit()));
            targetDataPoint.setPeriod(UnitTypeHelper.getPeriod(pdResponse.getUnit()));
            targetDataPoint.setSource(SOURCE);
            targetDataPoint.setPublished(dateStr);
            targetDataPoint.setRecorded_from(dateStr);
            targetDataPoint.setRecorded_to(dateStr);
            psRentPrices.add(targetDataPoint);
        }
        // add postcode rent prices to wrapper
        target.setPostcodeRentPrices(psRentPrices);

        return target;
    }
}
