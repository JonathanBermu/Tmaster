package com.example.Users.Types.Interfaces;

import java.io.IOException;

public interface AWSServiceInterface {
    String addFile(String base64Data, String imgType) throws IOException;
    String getObject(String key) throws IOException;
}
