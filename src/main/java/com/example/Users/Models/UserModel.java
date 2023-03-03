package com.example.Users.Models;

import javax.persistence.*;

@Entity
@Table
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sec")
    @SequenceGenerator(sequenceName = "user_sec", allocationSize = 1, name = "user_sec")
    private long id;
    @Column(name = "username", nullable = true, unique = true)
    private String username;
    @Column(name = "age", nullable = true)
    private Integer age;
    @Column(name = "email", nullable = true, unique = true)
    private String email;
    @Column(name = "password", nullable = true)
    private String password;
    @Column(name = "role", nullable = true)
    private String role;
    @Column(name="latest_token", nullable = true, length = 2000)
    private String latestToken;
    @Column(name="auth_id", nullable = true)
    private String authId;

    public String getLatestToken() {
        return latestToken;
    }

    public void setLatestToken(String latestToken) {
        this.latestToken = latestToken;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserModel() {
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserModel(long id, String username, Integer age, String email, String password, String role) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
