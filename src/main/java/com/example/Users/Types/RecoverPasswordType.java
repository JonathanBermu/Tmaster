package com.example.Users.Types;

import java.util.UUID;

public class RecoverPasswordType {
    private String recoveryCode;
    private String password;
    private String repeatPassword;

    public String getRecoveryCode() {
        return recoveryCode;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }
}
