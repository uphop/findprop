package com.lightson.findpropapi.loader.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import com.lightson.findpropapi.loader.model.SourcePostcodeAreaRentPrice;
import com.lightson.findpropapi.loader.model.SourcePostcodeRentPrice;

@Component
public class PostcodeRentPriceReader extends FlatFileItemReader<SourcePostcodeRentPrice> {

    private static final String POSTCODE_RENT_PRICE_LIST_FILENAME = "data/postcode_rent_price_list.csv";

    public PostcodeRentPriceReader() {
        setName("PostcodeRentPriceCsvReader");
        setResource(new FileSystemResource(POSTCODE_RENT_PRICE_LIST_FILENAME));
        setLinesToSkip(1);
        setLineMapper(getDefaultLineMapper());
    }

    public DefaultLineMapper<SourcePostcodeRentPrice> getDefaultLineMapper() {
        DefaultLineMapper<SourcePostcodeRentPrice> defaultLineMapper = new DefaultLineMapper<SourcePostcodeRentPrice>();

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames(new String[] { "postcode", "bedroomCategory",
                "countOfRents", "mean",
                "lowerQuartile", "median", "upperQuartile", "currency", "period", "source", "published", "recordedFrom",
                "recordedTo" });
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        BeanWrapperFieldSetMapper<SourcePostcodeRentPrice> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<SourcePostcodeRentPrice>();
        beanWrapperFieldSetMapper.setTargetType(SourcePostcodeRentPrice.class);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        return defaultLineMapper;
    }
}
