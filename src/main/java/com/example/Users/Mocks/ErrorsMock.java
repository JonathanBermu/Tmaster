package com.example.Users.Mocks;

import com.example.Users.Types.Interfaces.ErrorsInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorsMock implements ErrorsInterface {
    @Override
    public ResponseEntity badRequest() {
        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }
}
