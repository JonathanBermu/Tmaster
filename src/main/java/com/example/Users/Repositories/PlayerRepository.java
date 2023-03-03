package com.example.Users.Repositories;

import com.example.Users.Models.PlayerModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends CrudRepository<PlayerModel, String> {
    List<PlayerModel> findById(Integer id);
    List<PlayerModel> findAll();
    List<PlayerModel> findByCountryId(Integer id);
    List<PlayerModel> findByTeamId(Integer id);
    Page<PlayerModel> findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String name, String lastName, Pageable pageable);
}
