package com.lightson.findpropapi.loader.processor;

import java.util.HashMap;
import java.util.Map;

public class PropertyTypeHelper {
    public static final Map<String, String> PROPERTY_TYPE_MAP = new HashMap<String, String>() {
        {
            put("Room", "room");
            put("Studio", "studio");
            put("One Bedroom", "flat");
            put("Two Bedrooms", "flat");
            put("Three Bedrooms", "flat");
            put("Four or More Bedrooms", "flat");
        }
    };
}
