package com.lightson.findpropapi.loader.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.lightson.findpropapi.loader.model.SourcePostcode;
import com.lightson.findpropapi.loader.model.TargetPostcode;

@Component
public class PostcodeItemProcessor implements ItemProcessor<SourcePostcode, TargetPostcode> {
    @Override
    public TargetPostcode process(SourcePostcode source) throws Exception {
        TargetPostcode target = new TargetPostcode();
        target.setCode(source.getPcds());
        target.setLatitude(source.getLat());
        target.setLongitude(source.getLong());
        target.setLocalAuthorityCode(source.getOslaua());
        return target;
    }
}
