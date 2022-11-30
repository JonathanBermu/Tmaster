package com.example.Users.Repositories;

import com.example.Users.Models.TeamModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TeamRepository extends CrudRepository<TeamModel, String>{
    List<TeamModel> findById(Integer id);
    List<TeamModel> findAll();
    List<TeamModel> findByCountryId(Integer id);
    List<TeamModel> findByUserId(Long id);
}
