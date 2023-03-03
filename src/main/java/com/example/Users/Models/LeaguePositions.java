package com.example.Users.Models;

import javax.persistence.*;

@Entity
@Table
public class LeaguePositions {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leaguepos_sec")
    @SequenceGenerator(sequenceName = "leaguepos_sec", allocationSize = 1, name = "leaguepos_sec")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = true)
    private TeamModel teamId;
    private Integer points;
    private Integer w;
    private Integer d;
    private Integer l;
    private Integer goals;
    private Integer goalsAgainst;
    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = true)
    private Tournament tournament;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TeamModel getTeamId() {
        return teamId;
    }

    public void setTeamId(TeamModel teamId) {
        this.teamId = teamId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w = w;
    }

    public Integer getD() {
        return d;
    }

    public void setD(Integer d) {
        this.d = d;
    }

    public Integer getL() {
        return l;
    }

    public void setL(Integer l) {
        this.l = l;
    }

    public Integer getGoals() {
        return goals;
    }

    public void setGoals(Integer goals) {
        this.goals = goals;
    }

    public Integer getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(Integer goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }
}
