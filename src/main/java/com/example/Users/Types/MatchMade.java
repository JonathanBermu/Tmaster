package com.example.Users.Types;

import com.example.Users.Models.TeamModel;

public class MatchMade {
    private TeamModel team1;
    private TeamModel team2;

    public TeamModel getTeam1() {
        return team1;
    }

    public void setTeam1(TeamModel team1) {
        this.team1 = team1;
    }

    public TeamModel getTeam2() {
        return team2;
    }

    public void setTeam2(TeamModel team2) {
        this.team2 = team2;
    }
}
