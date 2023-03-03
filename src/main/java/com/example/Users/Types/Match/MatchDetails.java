package com.example.Users.Types.Match;

import com.example.Users.Models.MatchModel;
import com.example.Users.Models.MatchStats;

import java.util.List;

public class MatchDetails {
    private MatchModel match;
    private List<MatchStats> matchStats;

    public MatchDetails(MatchModel match, List<MatchStats> matchStats) {
        this.match = match;
        this.matchStats = matchStats;
    }

    public MatchModel getMatch() {
        return match;
    }

    public List<MatchStats> getMatchStats() {
        return matchStats;
    }
}
