package com.example.Users.Models;

import javax.persistence.*;

@Entity
@Table
public class TeamModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_sec")
    @SequenceGenerator(sequenceName = "team_sec", allocationSize = 1, name = "team_sec")
    private Integer id;
    private String name;
    private Integer state;
    private String img;
    @ManyToOne
    @JoinColumn(name = "sport_id", nullable = false)
    private SportsModel sport;
    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
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

    public SportsModel getSport() {
        return sport;
    }

    public void setSport(SportsModel sport) {
        this.sport = sport;
    }

    public CountryModel getCountry() {
        return country;
    }
    public void setCountry(CountryModel country) {
        this.country = country;
    }
    public Integer getState() {
        return state;
    }
    public void setState(Integer state) {
        this.state = state;
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
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
}
