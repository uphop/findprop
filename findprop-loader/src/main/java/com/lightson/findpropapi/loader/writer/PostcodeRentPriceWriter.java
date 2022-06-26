package com.lightson.findpropapi.loader.writer;

import javax.sql.DataSource;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.stereotype.Component;
import com.lightson.findpropapi.loader.model.TargetPostcodeRentPrice;

@Component
public class PostcodeRentPriceWriter extends JdbcBatchItemWriter<TargetPostcodeRentPrice> {

    public PostcodeRentPriceWriter(DataSource dataSource) {
        setDataSource(dataSource);
        setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());

        String sql = """
                INSERT INTO postcode_rent_price
                    (property_type, bedrooms, price_count, price_mean,
                    price_low, price_median, price_high, currency, period, source, published,
                    recorded_from, recorded_to, postcode_id)
                SELECT
                    :property_type, :bedrooms, :price_count, :price_mean,
                    :price_low, :price_median, :price_high, :currency, :period, :source, :published,
                    :recorded_from, :recorded_to, p.id
                FROM postcode p
                WHERE p.code = :postcode
                LIMIT 1
                    """;

        setSql(sql);
    }
}
