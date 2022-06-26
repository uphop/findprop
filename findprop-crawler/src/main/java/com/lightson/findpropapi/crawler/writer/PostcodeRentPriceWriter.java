package com.lightson.findpropapi.crawler.writer;

import java.util.List;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.lightson.findpropapi.crawler.model.PostcodeRentPrice;
import com.lightson.findpropapi.crawler.model.TargetRentPrice;

@Component
public class PostcodeRentPriceWriter extends FlatFileItemWriter<TargetRentPrice> {
    private static final String POSTCODE_LIST_RENT_PRICE_FILENAME = "data/postcode_rent_price_list.csv";

    public PostcodeRentPriceWriter() {
        setName("PostcodeRentPriceCsvWriter");
        setResource(new FileSystemResource(POSTCODE_LIST_RENT_PRICE_FILENAME));
        setShouldDeleteIfExists(true);
        setHeaderCallback(new PostcodeRentPriceHeaderWriter());
        setLineAggregator(new DelimitedLineAggregator<TargetRentPrice>());
    }

    private DelimitedLineAggregator<PostcodeRentPrice> getDefaultLineAggregator() {
        BeanWrapperFieldExtractor<PostcodeRentPrice> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[] { "postcode", "longitude", "latitude", "bedroomCategory", "countOfRents",
                "mean", "lowerQuartile", "median", "upperQuartile",
                "currency", "period", "source", "published", "recordedFrom", "recordedTo" });
        fieldExtractor.afterPropertiesSet();

        DelimitedLineAggregator<PostcodeRentPrice> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        lineAggregator.setFieldExtractor(fieldExtractor);

        return lineAggregator;
    }

    @Override
    public String doWrite(List<? extends TargetRentPrice> items) {
        StringBuilder lines = new StringBuilder();
        // change line aggregator to pick up only postcode prices from the wrapper
        DelimitedLineAggregator<PostcodeRentPrice> aggregator = getDefaultLineAggregator();
        for (TargetRentPrice item : items) {
            List<PostcodeRentPrice> psPrices = item.getPostcodeRentPrices();
            for (PostcodeRentPrice psPrice : psPrices) {
                lines.append(aggregator.aggregate(psPrice)).append(this.lineSeparator);
            }
        }
        return lines.toString();
    }
}
