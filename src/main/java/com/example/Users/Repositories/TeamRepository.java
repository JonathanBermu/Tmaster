package com.example.Users.Repositories;

import com.example.Users.Models.TeamModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends CrudRepository<TeamModel, String>{
    List<TeamModel> findById(Integer id);
    List<TeamModel> findAll();
    List<TeamModel> findByCountryId(Integer id);
    Page<TeamModel> findByUserIdAndStateAndNameContainingIgnoreCaseOrderBySportId(Long id, Integer state, String filter, Pageable pageable);
    List<TeamModel> findByState(Integer state);
}
