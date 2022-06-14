package com.lightson.findpropapi.loader.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import com.lightson.findpropapi.loader.model.SourceLocalAuthorityUtilityPrice;

@Component
public class LocalAuthorityUtilityPriceReader extends FlatFileItemReader<SourceLocalAuthorityUtilityPrice>
        implements ItemReader<SourceLocalAuthorityUtilityPrice> {

    private static final String LOCAL_AUTHORITY_UTILITY_PRICE_LIST_FILENAME = "data/local_authority_utility_price_list.csv";

    public LocalAuthorityUtilityPriceReader() {
        setName("LocalAuthorityUtilityPriceReader");
        setResource(new FileSystemResource(LOCAL_AUTHORITY_UTILITY_PRICE_LIST_FILENAME));
        setLinesToSkip(1);
        setLineMapper(getDefaultLineMapper());
    }

    public DefaultLineMapper<SourceLocalAuthorityUtilityPrice> getDefaultLineMapper() {
        DefaultLineMapper<SourceLocalAuthorityUtilityPrice> defaultLineMapper = new DefaultLineMapper<SourceLocalAuthorityUtilityPrice>();

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames(new String[] { "localAuthority", "bedroomCategory", "utilityCategory", "mean",
                "currency", "period", "source", "published", "recordedFrom",
                "recordedTo" });
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        BeanWrapperFieldSetMapper<SourceLocalAuthorityUtilityPrice> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<SourceLocalAuthorityUtilityPrice>();
        beanWrapperFieldSetMapper.setTargetType(SourceLocalAuthorityUtilityPrice.class);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        return defaultLineMapper;
    }
}
