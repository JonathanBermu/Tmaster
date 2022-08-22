package com.example.Users.Repositories;

import com.example.Users.Models.PlayerModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PlayerRepository extends CrudRepository<PlayerModel, String> {
    List<PlayerModel> getById(Integer id);
}
