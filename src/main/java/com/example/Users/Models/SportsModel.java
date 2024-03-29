package com.example.Users.Models;

import javax.persistence.*;

@Entity
@Table
public class SportsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sport_sec")
    @SequenceGenerator(sequenceName = "sport_sec", allocationSize = 1, name = "sport_sec")
    private Integer id;
    @Column(unique = true)
    private String name;
    //In case its a teams game it will have players, othewise it will take the team name as player (must have one player tough)
    private Integer hasPlayers;
    //False in case its a w/l game, like chess
    private Integer hasGoals;
    //In case stats needed (stats will be added)
    private Integer hasStats;
    //Probably only for football
    private Integer hasCards;
    private Integer State;

    public Integer getState() {
        return State;
    }

    public void setState(Integer state) {
        State = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHasPlayers() {
        return hasPlayers;
    }

    public void setHasPlayers(Integer hasPlayers) {
        this.hasPlayers = hasPlayers;
    }

    public Integer getHasGoals() {
        return hasGoals;
    }

    public void setHasGoals(Integer hasGoals) {
        this.hasGoals = hasGoals;
    }

    public Integer getHasStats() {
        return hasStats;
    }

    public void setHasStats(Integer hasStats) {
        this.hasStats = hasStats;
    }

    public Integer getHasCards() {
        return hasCards;
    }

    public void setHasCards(Integer hasCards) {
        this.hasCards = hasCards;
    }
}
