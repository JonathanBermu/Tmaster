package com.example.Users.Services;

import com.example.Users.config.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class PayloadService {
    @Autowired
    private Json json;
    public JsonNode getPayload(String auth) throws JsonProcessingException {
        String[] chunks = auth.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        JsonNode parsed_json = json.parse(payload);
        return parsed_json;
    }
}
