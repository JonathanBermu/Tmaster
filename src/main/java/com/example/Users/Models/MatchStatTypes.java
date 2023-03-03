package com.example.Users.Models;

import javax.persistence.*;

@Entity
public class MatchStatTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "match_type_sec")
    @SequenceGenerator(sequenceName = "match_type_sec", allocationSize = 1, name = "match_type_sec")
    private Integer id;
    @Column(unique = true)
    private String name;
    private String label;
    private Integer teamStat;

    public Integer getTeamStat() {
        return teamStat;
    }

    public void setTeamStat(Integer teamStat) {
        this.teamStat = teamStat;
    }

    public MatchStatTypes() {
    }

    public MatchStatTypes(String name, String label) {
        this.name = name;
        this.label = label;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String labels) {
        this.label = labels;
    }
}