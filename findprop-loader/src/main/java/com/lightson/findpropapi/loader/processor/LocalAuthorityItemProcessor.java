package com.lightson.findpropapi.loader.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.lightson.findpropapi.loader.model.SourceLocalAuthority;
import com.lightson.findpropapi.loader.model.TargetLocalAuthority;

@Component
public class LocalAuthorityItemProcessor implements ItemProcessor<SourceLocalAuthority, TargetLocalAuthority> {

    @Override
    public TargetLocalAuthority process(SourceLocalAuthority source) throws Exception {
        TargetLocalAuthority target = new TargetLocalAuthority();
        target.setCode(source.getLAD21CD());
        target.setName(source.getLAD21NM());
        target.setRegionCode(source.getRGN20CD());
        return target;
    }
}
