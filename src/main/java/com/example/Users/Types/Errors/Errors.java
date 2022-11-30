package com.example.Users.Types.Errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class Errors {
    public ResponseEntity badRequest() {
        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }
}
