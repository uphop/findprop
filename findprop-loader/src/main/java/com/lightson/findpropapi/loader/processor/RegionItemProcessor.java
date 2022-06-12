package com.lightson.findpropapi.loader.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.lightson.findpropapi.loader.model.SourceRegion;
import com.lightson.findpropapi.loader.model.TargetRegion;

@Component
public class RegionItemProcessor implements ItemProcessor<SourceRegion, TargetRegion> {

    @Override
    public TargetRegion process(SourceRegion source) throws Exception {
        TargetRegion target = new TargetRegion();
        target.setCode(source.getRGN20CD());
        target.setName(source.getRGN20NM());
        return target;
    }
}
