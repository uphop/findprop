package com.lightson.findpropapi.loader.writer;

import javax.sql.DataSource;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.stereotype.Component;

import com.lightson.findpropapi.loader.model.TargetRelatedLocalAuthority;

@Component
public class RelatedLocalAuthorityWriter extends JdbcBatchItemWriter<TargetRelatedLocalAuthority> {

    public RelatedLocalAuthorityWriter(DataSource dataSource) {
        setDataSource(dataSource);
        setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO related_local_authority (anchor_local_authority_id, related_local_authority_id) ");
        sql.append(
                "SELECT ala.id, rla.id FROM local_authority ala, local_authority rla WHERE ala.name = :localAuthority AND rla.name = :relatedLocalAuthority ");

        setSql(sql.toString());
    }
}
