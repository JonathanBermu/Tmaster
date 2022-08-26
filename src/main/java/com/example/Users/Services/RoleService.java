package com.example.Users.Services;

import com.example.Users.Types.RolesEnum;
import com.example.Users.config.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class RoleService {
    @Autowired
    private Json parseJson;
    public Integer checkTokenRole (String Authorization) throws JsonProcessingException {
        Authorization = Authorization.replace("Bearer ", "");
        String[] chunks = Authorization.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        JsonNode payloadParse = parseJson.parse(payload);
        return payloadParse.get("role").asInt();
    }

    public Boolean isAdmin(String authorization) throws JsonProcessingException {
        Integer role = this.checkTokenRole(authorization);
        if(role != RolesEnum.ADMIN.getValue()){
            return false;
        }
        return true;
    }
}
