package com.lightson.findpropapi.loader.writer;

import javax.sql.DataSource;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.stereotype.Component;

import com.lightson.findpropapi.loader.model.TargetRegion;

@Component
public class RegionWriter extends JdbcBatchItemWriter<TargetRegion> {

    public RegionWriter(DataSource dataSource) {
        setDataSource(dataSource);
        setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        setSql("INSERT INTO region (code, name) VALUES (:code, :name)");
    }
}
