package com.example.Users.Mocks;

import com.example.Users.Types.Interfaces.AWSServiceInterface;

import java.io.IOException;

public class AWSServiceMock implements AWSServiceInterface {
    @Override
    public String addFile(String base64Data, String imgType) throws IOException {
        return "Result";
    }

    @Override
    public String getObject(String key) throws IOException {
        return "Result";
    }
}
