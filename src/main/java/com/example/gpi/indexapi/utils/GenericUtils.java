package com.example.gpi.indexapi.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class GenericUtils {

    @Autowired
    RestTemplate restTemplate;

    private static ObjectMapper mapper = new ObjectMapper();
    public  <T> T readStreamAndGetObject(String endpoint, Class<T> valueType) throws IOException {
        return restTemplate.getForObject(endpoint,valueType);
    }
}
