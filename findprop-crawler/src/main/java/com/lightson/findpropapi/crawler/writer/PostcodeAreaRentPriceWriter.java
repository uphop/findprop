package com.lightson.findpropapi.crawler.writer;

import java.util.List;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.lightson.findpropapi.crawler.model.PostcodeAreaRentPrice;
import com.lightson.findpropapi.crawler.model.TargetRentPrice;

@Component
public class PostcodeAreaRentPriceWriter extends FlatFileItemWriter<TargetRentPrice> {
    private static final String POSTCODE_AREA_LIST_RENT_PRICE_FILENAME = "data/postcode_area_rent_price_list.csv";

    public PostcodeAreaRentPriceWriter() {
        setName("PostcodeAreaRentPriceCsvWriter");
        setResource(new FileSystemResource(POSTCODE_AREA_LIST_RENT_PRICE_FILENAME));
        setShouldDeleteIfExists(true);
        setHeaderCallback(new PostcodeAreaRentPriceHeaderWriter());
        setLineAggregator(new DelimitedLineAggregator<TargetRentPrice>());
    }

    private DelimitedLineAggregator<PostcodeAreaRentPrice> getDefaultLineAggregator() {
        BeanWrapperFieldExtractor<PostcodeAreaRentPrice> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[] { "postcode_area", "property_type", "bedrooms", "price_count",
                "price_mean", "price_low", "price_median", "price_high",
                "currency", "period", "source", "published", "recorded_from", "recorded_to" });
        fieldExtractor.afterPropertiesSet();

        DelimitedLineAggregator<PostcodeAreaRentPrice> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        lineAggregator.setFieldExtractor(fieldExtractor);

        return lineAggregator;
    }

    @Override
    public String doWrite(List<? extends TargetRentPrice> items) {
        StringBuilder lines = new StringBuilder();
        // change line aggregator to pick up only postcode area prices from the wrapper
        DelimitedLineAggregator<PostcodeAreaRentPrice> aggregator = getDefaultLineAggregator();
        for (TargetRentPrice item : items) {
            PostcodeAreaRentPrice paPrice = item.getPostcodeAreaRentPrice();
            lines.append(aggregator.aggregate(paPrice)).append(this.lineSeparator);
        }
        return lines.toString();
    }
}
