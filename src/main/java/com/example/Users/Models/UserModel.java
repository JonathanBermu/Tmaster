package com.example.Users.Models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserModel {
    @Id
    private String id;
    private String username;

    public UserModel() {
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserModel(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
