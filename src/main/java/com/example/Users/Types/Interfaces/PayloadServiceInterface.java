package com.example.Users.Types.Interfaces;

import com.example.Users.config.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface PayloadServiceInterface {
    Json json = null;
    JsonNode getPayload(String token) throws JsonProcessingException;
}
