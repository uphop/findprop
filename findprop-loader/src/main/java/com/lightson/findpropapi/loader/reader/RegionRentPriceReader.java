package com.lightson.findpropapi.loader.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import com.lightson.findpropapi.loader.model.SourceRegionRentPrice;

@Component
public class RegionRentPriceReader extends FlatFileItemReader<SourceRegionRentPrice>
        implements ItemReader<SourceRegionRentPrice> {

    private static final String REGION_RENT_PRICE_LIST_FILENAME = "data/region_rent_price_list.csv";

    public RegionRentPriceReader() {
        setName("RegionRentPriceCsvReader");
        setResource(new FileSystemResource(REGION_RENT_PRICE_LIST_FILENAME));
        setLinesToSkip(1);
        setLineMapper(getDefaultLineMapper());
    }

    public DefaultLineMapper<SourceRegionRentPrice> getDefaultLineMapper() {
        DefaultLineMapper<SourceRegionRentPrice> defaultLineMapper = new DefaultLineMapper<SourceRegionRentPrice>();

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames(new String[] { "region", "bedroomCategory", "countOfRents", "mean",
                "lowerQuartile", "median", "upperQuartile", "currency", "period", "source", "published", "recordedFrom",
                "recordedTo" });
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        BeanWrapperFieldSetMapper<SourceRegionRentPrice> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<SourceRegionRentPrice>();
        beanWrapperFieldSetMapper.setTargetType(SourceRegionRentPrice.class);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        return defaultLineMapper;
    }
}
