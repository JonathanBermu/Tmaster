package com.example.Users.Types;

public enum TournamentTypes {
    LEAGUE(1), KEYS(2), KEYSBACKNFORTH(3);

    private Integer tournamentType;

    TournamentTypes(Integer i) {
        this.tournamentType = i;
    }
    public Integer getValue(){return this.tournamentType;}
}
