package com.lightson.findpropapi.loader.writer;

import javax.sql.DataSource;

import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.stereotype.Component;

import com.lightson.findpropapi.loader.model.TargetPostcode;

@Component
public class PostcodeWriter extends JdbcBatchItemWriter<TargetPostcode> {
    public PostcodeWriter(DataSource dataSource) {
        setDataSource(dataSource);
        setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        setSql("INSERT INTO postcode (code, longitude, latitude, location, local_authority_id) SELECT :code, :longitude, :latitude, ST_SRID(POINT(:longitude, :latitude), 0), la.id FROM local_authority la WHERE la.code = :localAuthorityCode LIMIT 1");
    }
}
