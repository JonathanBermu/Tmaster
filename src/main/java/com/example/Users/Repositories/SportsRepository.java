package com.example.Users.Repositories;

import com.example.Users.Models.SportsModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SportsRepository extends CrudRepository<SportsModel, String> {
    List<SportsModel> findById(Integer id);
}
