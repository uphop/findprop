package com.lightson.findpropapi.loader.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import com.lightson.findpropapi.loader.model.SourcePostcodeAreaRentPrice;

@Component
public class PostcodeAreaRentPriceReader extends FlatFileItemReader<SourcePostcodeAreaRentPrice>
        implements ItemReader<SourcePostcodeAreaRentPrice> {

    private static final String POSTCODE_AREA_RENT_PRICE_LIST_FILENAME = "data/postcode_area_rent_price_list.csv";

    public PostcodeAreaRentPriceReader() {
        setName("PostcodeAreaRentPriceCsvReader");
        setResource(new FileSystemResource(POSTCODE_AREA_RENT_PRICE_LIST_FILENAME));
        setLinesToSkip(1);
        setLineMapper(getDefaultLineMapper());
    }

    public DefaultLineMapper<SourcePostcodeAreaRentPrice> getDefaultLineMapper() {
        DefaultLineMapper<SourcePostcodeAreaRentPrice> defaultLineMapper = new DefaultLineMapper<SourcePostcodeAreaRentPrice>();

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames(new String[] { "postcodeArea", "bedroomCategory", "countOfRents", "mean",
                "lowerQuartile", "median", "upperQuartile", "currency", "period", "source", "published", "recordedFrom",
                "recordedTo" });
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        BeanWrapperFieldSetMapper<SourcePostcodeAreaRentPrice> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<SourcePostcodeAreaRentPrice>();
        beanWrapperFieldSetMapper.setTargetType(SourcePostcodeAreaRentPrice.class);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        return defaultLineMapper;
    }
}
