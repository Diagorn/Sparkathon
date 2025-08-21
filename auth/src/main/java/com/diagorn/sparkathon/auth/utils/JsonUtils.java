package com.diagorn.sparkathon.auth.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.plexus.util.StringUtils;

/**
 * Utilities to work with JSON
 *
 * @author mikhail.gasin
 */
public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtils() {
    }

    /**
     * Convert object to JSON
     *
     * @param obj - object
     * @return JSON representation
     */
    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not write object as JSON", e);
        }
    }

    /**
     * Convert JSON to POJO
     *
     * @param json  - JSON to convert
     * @param clazz - class to be converted to
     * @param <T>   - POJO data type
     * @return clazz instance
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            throw new IllegalArgumentException("JSON is empty");
        }

        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format(
                    "Could not read value from JSON %s for class %s",
                    json, clazz.getName()
            ), e);
        }
    }
}
