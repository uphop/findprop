package com.lightson.findpropapi.loader.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import com.lightson.findpropapi.loader.model.SourceRelatedLocalAuthority;

@Component
public class RelatedLocalAuthorityReader extends FlatFileItemReader<SourceRelatedLocalAuthority>
		implements ItemReader<SourceRelatedLocalAuthority> {

	private static final String RELATED_LOCAL_AUTHORITY_LIST_FILENAME = "data/related_local_authority_list.csv";

	public RelatedLocalAuthorityReader() {
		setName("RelatedLocalAuthorityCsvReader");
		setResource(new FileSystemResource(RELATED_LOCAL_AUTHORITY_LIST_FILENAME));
		setLinesToSkip(1);
		setLineMapper(getDefaultLineMapper());
	}

	public DefaultLineMapper<SourceRelatedLocalAuthority> getDefaultLineMapper() {
		DefaultLineMapper<SourceRelatedLocalAuthority> defaultLineMapper = new DefaultLineMapper<SourceRelatedLocalAuthority>();

		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setNames(new String[] { "localAuthority", "relatedLocalAuthority" });
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

		BeanWrapperFieldSetMapper<SourceRelatedLocalAuthority> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<SourceRelatedLocalAuthority>();
		beanWrapperFieldSetMapper.setTargetType(SourceRelatedLocalAuthority.class);
		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

		return defaultLineMapper;
	}
}