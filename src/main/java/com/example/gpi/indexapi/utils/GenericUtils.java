package com.example.gpi.indexapi.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class GenericUtils {

    @Autowired
    RestTemplate restTemplate;

    public  <T> T readStreamAndGetObject(String endpoint, Class<T> valueType) throws IOException {
        return restTemplate.getForObject(endpoint,valueType);
    }
}
