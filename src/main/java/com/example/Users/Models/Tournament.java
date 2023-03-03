package com.example.Users.Models;

import javax.persistence.*;

@Entity
@Table
public class Tournament {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tournament_sec")
    @SequenceGenerator(sequenceName = "tournament_sec", allocationSize = 1, name = "tournament_sec")
    @Id
    private Integer id;
    private String name;
    private Integer type;
    private Integer teams;
    private Integer rounds;
    private Integer currentRound;

    @ManyToOne
    @JoinColumn(name = "sport_id", nullable = false)
    private SportsModel sport;
    @ManyToOne
    @JoinColumn(name = "country_id", nullable = true)
    private CountryModel country;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
    public Integer getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(Integer currentRound) {
        this.currentRound = currentRound;
    }
    public SportsModel getSport() {
        return sport;
    }

    public void setSport(SportsModel sport) {
        this.sport = sport;
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

    public CountryModel getCountry() {
        return country;
    }

    public void setCountry(CountryModel country) {
        this.country = country;
    }

    public Integer getRounds() {
        return rounds;
    }

    public void setRounds(Integer rounds) {
        this.rounds = rounds;
    }
}
