package com.example.Users.Types;

public enum ImgTypes {
    JPG("/"), PNG("i"), GIF("r"), WEBP("u");
    private String imgChar;
    ImgTypes(String i) {
        imgChar = i;
    }
    public String getValue() {
        return this.imgChar;
    }
}
