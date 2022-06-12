package com.lightson.findpropapi.loader.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import com.lightson.findpropapi.loader.model.SourceRegion;

@Component
public class RegionReader extends FlatFileItemReader<SourceRegion> implements ItemReader<SourceRegion> {

	private static final String REGION_LIST_FILENAME = "data/region_list.csv";

	public RegionReader() {
		setName("RegionCsvReader");
		setResource(new FileSystemResource(REGION_LIST_FILENAME));
		setLinesToSkip(1);
		setLineMapper(getDefaultLineMapper());
	}

	public DefaultLineMapper<SourceRegion> getDefaultLineMapper() {
		DefaultLineMapper<SourceRegion> defaultLineMapper = new DefaultLineMapper<SourceRegion>();

		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setNames(new String[] { "RGN20CD", "RGN20CDO", "RGN20NM", "RGN20NMW" });
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

		BeanWrapperFieldSetMapper<SourceRegion> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<SourceRegion>();
		beanWrapperFieldSetMapper.setTargetType(SourceRegion.class);
		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

		return defaultLineMapper;
	}
}