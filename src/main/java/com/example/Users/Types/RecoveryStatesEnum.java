package com.example.Users.Types;

public enum RecoveryStatesEnum {
    VALID(1), INVALID(2);
    RecoveryStatesEnum(Integer i){
        RecoveryState = i;
    }
    private int RecoveryState;
    public Integer getValue() {return RecoveryState;}
}
