package com.example.Users.Types.Player;

import java.util.Date;

public class UpdatePlayer {
    private Integer id;
    private String name;
    private String lastName;
    private Date birthDate;
    private String img;
    private Double height;
    private Integer weight;
    private Integer rightHanded;
    private Integer countryId;
    private Integer teamId;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getImg() {
        return img;
    }

    public Double getHeight() {
        return height;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getRightHanded() {
        return rightHanded;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public Integer getTeamId() {
        return teamId;
    }
}
