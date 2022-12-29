package com.example.Users.Mocks;

import com.example.Users.Types.Interfaces.ValidateBase64Interface;

public class ValidateBase64Mock implements ValidateBase64Interface {
    @Override
    public Boolean isImage(String base64) {
        return true;
    }

    @Override
    public String imageType(String base64) {
        return "PNG";
    }
}
