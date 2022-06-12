package com.lightson.findpropapi.loader.writer;

import javax.sql.DataSource;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.stereotype.Component;
import com.lightson.findpropapi.loader.model.TargetLocalAuthorityRentPrice;

@Component
public class LocalAuthorityRentPriceWriter extends JdbcBatchItemWriter<TargetLocalAuthorityRentPrice> {

    public LocalAuthorityRentPriceWriter(DataSource dataSource) {
        setDataSource(dataSource);
        setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());

        String sql = """
                INSERT INTO local_authority_rent_price
                    (property_type, bedrooms, price_count, price_mean,
                    price_low, price_median, price_high, currency, period, source, published,
                    recorded_from, recorded_to, local_authority_id)
                SELECT
                    :property_type, :bedrooms, :price_count, :price_mean,
                    :price_low, :price_median, :price_high, :currency, :period, :source, :published,
                    :recorded_from, :recorded_to, la.id
                FROM local_authority la
                WHERE la.name = :local_authority
                LIMIT 1
                    """;

        setSql(sql);
    }
}
