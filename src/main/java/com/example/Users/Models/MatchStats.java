package com.example.Users.Models;

import javax.persistence.*;

@Entity
public class MatchStats {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "match_stat_sec")
    @SequenceGenerator(sequenceName = "match_stat_sec", allocationSize = 1, name = "match_stat_sec")
    private Integer id;

    private Integer minute;
    @ManyToOne
    @JoinColumn(name = "stat_type", nullable = false)
    private MatchStatTypes statType;
    private Integer score;
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private MatchModel match;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayerModel player;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private TeamModel team;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public MatchStatTypes getStatType() {
        return statType;
    }

    public void setStatType(MatchStatTypes statType) {
        this.statType = statType;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public MatchModel getMatch() {
        return match;
    }

    public void setMatch(MatchModel match) {
        this.match = match;
    }

    public PlayerModel getPlayer() {
        return player;
    }

    public void setPlayer(PlayerModel player) {
        this.player = player;
    }

    public TeamModel getTeam() {
        return team;
    }

    public void setTeam(TeamModel team) {
        this.team = team;
    }
}
