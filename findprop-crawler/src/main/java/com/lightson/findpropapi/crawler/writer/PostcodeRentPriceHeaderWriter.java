package com.lightson.findpropapi.crawler.writer;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

public class PostcodeRentPriceHeaderWriter implements FlatFileHeaderCallback {
    private final String header = "postcode,longitude,latitude,property_type,bedrooms,price_count,price_mean,price_low,price_median,price_high,currency,period,source,published,recorded_from,recorded_to";

    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.write(header);
    }
}
