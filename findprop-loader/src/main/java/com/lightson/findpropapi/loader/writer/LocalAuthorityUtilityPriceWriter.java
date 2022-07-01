package com.lightson.findpropapi.loader.writer;

import javax.sql.DataSource;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.stereotype.Component;
import com.lightson.findpropapi.loader.model.TargetLocalAuthorityUtilityPrice;

@Component
public class LocalAuthorityUtilityPriceWriter extends JdbcBatchItemWriter<TargetLocalAuthorityUtilityPrice> {

    public LocalAuthorityUtilityPriceWriter(DataSource dataSource) {
        setDataSource(dataSource);
        setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());

        StringBuilder sql = new StringBuilder();
        sql.append(
                "INSERT INTO local_authority_utility_price (property_type, bedrooms, utility_type, price_mean, currency, period, source, published, recorded_from, recorded_to, local_authority_id) ");
        sql.append(
                "SELECT :property_type, :bedrooms, :utility_type, :price_mean, :currency, :period, :source, :published, :recorded_from, :recorded_to, la.id ");
        sql.append("FROM local_authority la WHERE la.name = :local_authority LIMIT 1 ");

        setSql(sql.toString());
    }
}
