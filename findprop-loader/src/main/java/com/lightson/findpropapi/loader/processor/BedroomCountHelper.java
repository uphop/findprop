package com.lightson.findpropapi.loader.processor;

import java.util.HashMap;
import java.util.Map;

public class BedroomCountHelper {
    public static final Map<String, Integer> BEDROOM_COUNT_MAP = new HashMap<String, Integer>() {
        {
            put("Room", 0);
            put("Studio", 0);
            put("One Bedroom", 1);
            put("Two Bedrooms", 2);
            put("Three Bedrooms", 3);
            put("Four or More Bedrooms", 4);
        }
    };
}
