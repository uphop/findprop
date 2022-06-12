package com.lightson.findpropapi.loader.writer;

import javax.sql.DataSource;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.stereotype.Component;
import com.lightson.findpropapi.loader.model.TargetRegionRentPrice;

@Component
public class RegionRentPriceWriter extends JdbcBatchItemWriter<TargetRegionRentPrice> {

    public RegionRentPriceWriter(DataSource dataSource) {
        setDataSource(dataSource);
        setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());

        String sql = """
            INSERT INTO region_rent_price 
                (property_type, bedrooms, price_count, price_mean, 
                price_low, price_median, price_high, currency, period, source, published, 
                recorded_from, recorded_to, region_id) 
            SELECT 
                :property_type, :bedrooms, :price_count, :price_mean, 
                :price_low, :price_median, :price_high, :currency, :period, :source, :published, 
                :recorded_from, :recorded_to, r.id
            FROM region r
            WHERE r.name = :region
            LIMIT 1
                """;

        setSql(sql);
    }
}
