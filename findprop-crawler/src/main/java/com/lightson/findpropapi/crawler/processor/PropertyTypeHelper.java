package com.lightson.findpropapi.crawler.processor;

import java.util.HashMap;
import java.util.Map;

import com.lightson.findpropapi.crawler.model.PropertyTypeEnum;

public class PropertyTypeHelper {
    public static final Map<String, PropertyTypeEnum> PROPERTY_TYPE_MAP = new HashMap<String, PropertyTypeEnum>() {
        {
            put("One Bedroom", PropertyTypeEnum.flat);
            put("Two Bedrooms", PropertyTypeEnum.flat);
            put("Three Bedrooms", PropertyTypeEnum.flat);
            put("Four or More Bedrooms", PropertyTypeEnum.flat);
        }
    };
}
