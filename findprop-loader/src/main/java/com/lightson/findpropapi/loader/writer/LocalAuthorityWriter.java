package com.lightson.findpropapi.loader.writer;

import javax.sql.DataSource;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.stereotype.Component;

import com.lightson.findpropapi.loader.model.TargetLocalAuthority;

@Component
public class LocalAuthorityWriter extends JdbcBatchItemWriter<TargetLocalAuthority> {

    public LocalAuthorityWriter(DataSource dataSource) {
        setDataSource(dataSource);
        setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        setSql("INSERT INTO local_authority (code, name, region_id) SELECT :code, :name, r.id FROM region r WHERE r.code = :regionCode LIMIT 1");
    }
}
