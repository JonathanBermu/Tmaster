package com.example.Users.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class Json {
    private ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode parse(String str) throws JsonProcessingException {
        return objectMapper.readTree(str);
    }
}
