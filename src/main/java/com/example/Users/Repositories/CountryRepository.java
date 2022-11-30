package com.example.Users.Repositories;

import com.example.Users.Models.CountryModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CountryRepository extends CrudRepository<CountryModel, String>{
    List<CountryModel> findById(Integer id);
    List<CountryModel> findAll();
}
