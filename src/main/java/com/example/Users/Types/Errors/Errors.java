package com.example.Users.Types.Errors;

import com.example.Users.Types.Interfaces.ErrorsInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class Errors implements ErrorsInterface {
    public ResponseEntity badRequest() {
        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }
}
