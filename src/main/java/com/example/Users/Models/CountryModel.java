package com.example.Users.Models;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "country")
public class CountryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_sec")
    @SequenceGenerator(sequenceName = "country_sec", allocationSize = 1, name = "country_sec")
    private Integer id;
    @Column(unique = true)
    private String name;
    private Integer state;
    private Integer lorem;

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

}
