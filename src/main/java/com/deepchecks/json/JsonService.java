package com.deepchecks.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> String toJson(T request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting to json: ", e);
        }
    }
}
