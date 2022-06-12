package com.lightson.findpropapi.loader.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.lightson.findpropapi.loader.model.SourcePostcode;

@Component
public class PostcodeReader extends FlatFileItemReader<SourcePostcode> implements ItemReader<SourcePostcode> {

    private static final String POSTCODE_LIST_FILENAME = "data/postcode_list.csv";

    public PostcodeReader() {
        setName("PostcodeCsvReader");
        setResource(new FileSystemResource(POSTCODE_LIST_FILENAME));
        setLinesToSkip(1);
        setLineMapper(getDefaultLineMapper());
    }

    public DefaultLineMapper<SourcePostcode> getDefaultLineMapper() {
        DefaultLineMapper<SourcePostcode> defaultLineMapper = new DefaultLineMapper<SourcePostcode>();

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames(new String[] { "pcd", "pcd2", "pcds", "dointr", "doterm", "oscty", "ced",
                "oslaua", "osward", "parish", "usertype", "oseast1m", "osnrth1m", "osgrdind", "oshlthau", "nhser",
                "ctry", "rgn", "streg", "pcon", "eer", "teclec", "ttwa", "pct", "itl", "statsward", "oa01", "casward",
                "park", "lsoa01", "msoa01", "ur01ind", "oac01", "oa11", "lsoa11", "msoa11", "wz11", "ccg", "bua11",
                "buasd11", "ru11ind", "oac11", "lat", "long", "lep1", "lep2", "pfa", "imd", "calncv", "stp" });
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        BeanWrapperFieldSetMapper<SourcePostcode> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<SourcePostcode>();
        beanWrapperFieldSetMapper.setTargetType(SourcePostcode.class);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        return defaultLineMapper;
    }
}
