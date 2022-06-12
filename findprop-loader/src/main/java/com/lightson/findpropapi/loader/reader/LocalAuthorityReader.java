package com.lightson.findpropapi.loader.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import com.lightson.findpropapi.loader.model.SourceLocalAuthority;

@Component
public class LocalAuthorityReader extends FlatFileItemReader<SourceLocalAuthority>
		implements ItemReader<SourceLocalAuthority> {

	private static final String LOCAL_AUTHORITY_LIST_FILENAME = "data/local_authority_list.csv";

	public LocalAuthorityReader() {
		setName("LocalAuthorityCsvReader");
		setResource(new FileSystemResource(LOCAL_AUTHORITY_LIST_FILENAME));
		setLinesToSkip(1);
		setLineMapper(getDefaultLineMapper());
	}

	public DefaultLineMapper<SourceLocalAuthority> getDefaultLineMapper() {
		DefaultLineMapper<SourceLocalAuthority> defaultLineMapper = new DefaultLineMapper<SourceLocalAuthority>();

		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setNames(new String[] { "LAD21CD", "LAD21NM", "LAD21NMW", "RGN20CD" });
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

		BeanWrapperFieldSetMapper<SourceLocalAuthority> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<SourceLocalAuthority>();
		beanWrapperFieldSetMapper.setTargetType(SourceLocalAuthority.class);
		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

		return defaultLineMapper;
	}
}