package com.lightson.findpropapi.loader.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.lightson.findpropapi.loader.model.SourceRelatedLocalAuthority;
import com.lightson.findpropapi.loader.model.TargetRelatedLocalAuthority;

@Component
public class RelatedLocalAuthorityItemProcessor
        implements ItemProcessor<SourceRelatedLocalAuthority, TargetRelatedLocalAuthority> {

    @Override
    public TargetRelatedLocalAuthority process(SourceRelatedLocalAuthority source) throws Exception {
        TargetRelatedLocalAuthority target = new TargetRelatedLocalAuthority();
        target.setLocalAuthority(source.getLocalAuthority());
        target.setRelatedLocalAuthority(source.getRelatedLocalAuthority());
        return target;
    }
}
