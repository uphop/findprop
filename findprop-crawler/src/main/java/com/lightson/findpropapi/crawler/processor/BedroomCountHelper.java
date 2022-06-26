package com.lightson.findpropapi.crawler.processor;

import java.util.HashMap;
import java.util.Map;

public class BedroomCountHelper {
    public static final Map<String, Integer> BEDROOM_COUNT_MAP = new HashMap<String, Integer>() {
        {
            put("One Bedroom", 1);
            put("Two Bedrooms", 2);
            put("Three Bedrooms", 3);
            put("Four or More Bedrooms", 4);
        }
    };
}
