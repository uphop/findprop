package com.lightson.findpropapi.crawler.writer;

import java.util.Arrays;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.stereotype.Component;

import com.lightson.findpropapi.crawler.model.TargetRentPrice;

@Component
public class CompositeRentPriceWriter extends CompositeItemWriter<TargetRentPrice> {

    public CompositeRentPriceWriter() {
        // configure two writers - for postcode area rent prices and postcode rent prices
        setDelegates(Arrays.asList(new PostcodeAreaRentPriceWriter(), new PostcodeRentPriceWriter()));
    }
}
