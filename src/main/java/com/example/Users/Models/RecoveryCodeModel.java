package com.example.Users.Models;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class RecoveryCodeModel {
    public Integer getId() {
        return id;
    }
    public void setUsed(Integer used){
        this.used = used;
    }
    public Integer getUsed() {
        return this.used;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private Integer userId;
    @Column(nullable = true)
    private Integer used;
    @Column(nullable = false)
    private Date createdAt;
}
