package com.example.Users.Services;

import com.example.Users.Types.ImgTypes;
import com.example.Users.Types.Interfaces.ValidateBase64Interface;
import org.springframework.stereotype.Service;

@Service
public class ValidateBase64 implements ValidateBase64Interface {
    public Boolean isImage(String base64) {
        String firstChar = String.valueOf(base64.charAt(0));
        if(firstChar.equals(ImgTypes.GIF.getValue()) ||
                firstChar.equals(ImgTypes.JPG.getValue()) ||
                firstChar.equals(ImgTypes.PNG.getValue()) ||
                firstChar.equals(ImgTypes.WEBP.getValue())) {
            return true;
        }
        return false;
    }
    public String imageType(String base64) {
        String firstChar = String.valueOf(base64.charAt(0));
        if(firstChar.equals(ImgTypes.GIF.getValue())) {
            return ".gif";
        }
        else if(firstChar.equals(ImgTypes.JPG.getValue())) {
            return ".jpg";
        }
        else if(firstChar.equals(ImgTypes.PNG.getValue())) {
            return ".png";
        }
        return ".webp";
    }
}
