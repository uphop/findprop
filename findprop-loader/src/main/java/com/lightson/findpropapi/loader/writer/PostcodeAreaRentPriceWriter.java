package com.lightson.findpropapi.loader.writer;

import javax.sql.DataSource;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.stereotype.Component;

import com.lightson.findpropapi.loader.model.TargetPostcodeAreaRentPrice;

@Component
public class PostcodeAreaRentPriceWriter extends JdbcBatchItemWriter<TargetPostcodeAreaRentPrice> {

    public PostcodeAreaRentPriceWriter(DataSource dataSource) {
        setDataSource(dataSource);
        setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());

        StringBuilder sql = new StringBuilder();
        sql.append(
                "INSERT INTO postcode_area_rent_price (property_type, bedrooms, price_count, price_mean, price_low, price_median, price_high, currency, period, source, published, recorded_from, recorded_to, postcode_area) ");
        sql.append(
                "VALUES(:property_type, :bedrooms, :price_count, :price_mean, :price_low, :price_median, :price_high, :currency, :period, :source, :published, :recorded_from, :recorded_to, :postcode_area) ");

        setSql(sql.toString());
    }
}
