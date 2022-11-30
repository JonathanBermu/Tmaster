package com.example.Users.Types.Match;

public class SetMatchTeams {
    private Integer localTeamId;
    private Integer visitorTeamId;
    private Integer matchId;

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Integer getLocalTeamId() {
        return localTeamId;
    }

    public void setLocalTeamId(Integer localTeamId) {
        this.localTeamId = localTeamId;
    }

    public Integer getVisitorTeamId() {
        return visitorTeamId;
    }

    public void setVisitorTeamId(Integer visitorTeamId) {
        this.visitorTeamId = visitorTeamId;
    }
}
