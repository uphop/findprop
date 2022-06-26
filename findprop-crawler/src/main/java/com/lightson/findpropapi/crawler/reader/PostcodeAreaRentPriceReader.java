package com.lightson.findpropapi.crawler.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.lightson.findpropapi.crawler.model.SourceRentPrice;

@Component
public class PostcodeAreaRentPriceReader extends FlatFileItemReader<SourceRentPrice>
        implements ItemReader<SourceRentPrice> {

    private static final String POSTCODE_AREA_LIST_FILENAME = "data/postcode_area_list.csv";

    public PostcodeAreaRentPriceReader() {
        setName("PostcodeAreaRentPriceCsvReader");
        setResource(new FileSystemResource(POSTCODE_AREA_LIST_FILENAME));
        setLinesToSkip(1);
        setLineMapper(getDefaultLineMapper());
    }

    private DefaultLineMapper<SourceRentPrice> getDefaultLineMapper() {
        DefaultLineMapper<SourceRentPrice> defaultLineMapper = new DefaultLineMapper<SourceRentPrice>();

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames(new String[] { "postcodeArea", "bedroomCategory" });
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        BeanWrapperFieldSetMapper<SourceRentPrice> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<SourceRentPrice>();
        beanWrapperFieldSetMapper.setTargetType(SourceRentPrice.class);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        return defaultLineMapper;
    }
}
