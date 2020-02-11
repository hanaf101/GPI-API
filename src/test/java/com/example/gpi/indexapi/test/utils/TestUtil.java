package com.example.gpi.indexapi.test.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class TestUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    public static <T> T readStreamAndGetObject(String fileName, Class<T> valueType) throws IOException {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        URL url = valueType.getResource("/"+ fileName + ".json");
        return mapper.readValue(url, valueType);
    }
}
