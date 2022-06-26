package com.lightson.findpropapi.crawler.writer;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

public class PostcodeAreaRentPriceHeaderWriter implements FlatFileHeaderCallback {
    private final String header = "postcodeArea,bedroomCategory,countOfRents,mean,lowerQuartile,median,upperQuartile,currency,period,source,published,recordedFrom,recordedTo";

    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.write(header);
    }
}
