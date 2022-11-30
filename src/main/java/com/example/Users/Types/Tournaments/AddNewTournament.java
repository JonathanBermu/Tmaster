package com.example.Users.Types.Tournaments;

import java.util.List;

public class AddNewTournament {
    private String name;
    private Integer type;
    private Integer teams;
    private List<Integer> teamsIds;
    private Integer sport_id;

    public Integer getSport_id() {
        return sport_id;
    }

    public void setSport_id(Integer sport_id) {
        this.sport_id = sport_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTeams() {
        return teams;
    }

    public void setTeams(Integer teams) {
        this.teams = teams;
    }

    public List<Integer> getTeamsIds() {
        return teamsIds;
    }

    public void setTeamsIds(List<Integer> teamsIds) {
        this.teamsIds = teamsIds;
    }
}
