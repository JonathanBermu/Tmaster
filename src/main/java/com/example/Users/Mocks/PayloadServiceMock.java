package com.example.Users.Mocks;

import com.example.Users.Types.Interfaces.PayloadServiceInterface;
import com.example.Users.config.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PayloadServiceMock implements PayloadServiceInterface {
    @Override
    public JsonNode getPayload(String token) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = token.equals("user2") ? "{\"user_id\": \"2\"}" : "{\"user_id\": \"1\"}";
        JsonNode root = mapper.readTree(jsonString);
        return root;
    }
}
