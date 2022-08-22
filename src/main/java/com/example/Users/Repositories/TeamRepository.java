package com.example.Users.Repositories;

import com.example.Users.Models.CountryModel;
import com.example.Users.Models.RecoveryCodeModel;
import com.example.Users.Models.TeamModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TeamRepository extends CrudRepository<TeamModel, String>{
    List<TeamModel> findById(Integer id);
}
