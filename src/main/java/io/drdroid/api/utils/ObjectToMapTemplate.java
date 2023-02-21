package io.drdroid.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ObjectToMapTemplate {

    private static final ObjectMapper oMapper = new ObjectMapper();

    protected static Map convertObjectToMap(Object obj) {
        return oMapper.convertValue(obj, Map.class);
    }

}
