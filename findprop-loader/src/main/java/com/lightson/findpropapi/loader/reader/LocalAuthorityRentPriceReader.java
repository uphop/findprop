package com.lightson.findpropapi.loader.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.lightson.findpropapi.loader.model.SourceLocalAuthorityRentPrice;

@Component
public class LocalAuthorityRentPriceReader extends FlatFileItemReader<SourceLocalAuthorityRentPrice>
        implements ItemReader<SourceLocalAuthorityRentPrice> {

    private static final String LOCAL_AUTHORITY_RENT_PRICE_LIST_FILENAME = "data/local_authority_rent_price_list.csv";

    public LocalAuthorityRentPriceReader() {
        setName("LocalAuthorityRentPriceCsvReader");
        setResource(new FileSystemResource(LOCAL_AUTHORITY_RENT_PRICE_LIST_FILENAME));
        setLinesToSkip(1);
        setLineMapper(getDefaultLineMapper());
    }

    public DefaultLineMapper<SourceLocalAuthorityRentPrice> getDefaultLineMapper() {
        DefaultLineMapper<SourceLocalAuthorityRentPrice> defaultLineMapper = new DefaultLineMapper<SourceLocalAuthorityRentPrice>();

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames(new String[] { "localAuthority", "bedroomCategory", "countOfRents", "mean",
                "lowerQuartile", "median", "upperQuartile", "currency", "period", "source", "published", "recordedFrom",
                "recordedTo" });
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        BeanWrapperFieldSetMapper<SourceLocalAuthorityRentPrice> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<SourceLocalAuthorityRentPrice>();
        beanWrapperFieldSetMapper.setTargetType(SourceLocalAuthorityRentPrice.class);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        return defaultLineMapper;
    }
}
