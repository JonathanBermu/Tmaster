package com.example.Users.Types.Interfaces;

public interface ValidateBase64Interface {
    Boolean isImage(String base64);
    String imageType(String base64);
}
