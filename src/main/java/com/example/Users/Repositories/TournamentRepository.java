package com.example.Users.Repositories;

import com.example.Users.Models.Tournament;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TournamentRepository extends CrudRepository<Tournament, String> {
    List<Tournament> findById(Integer id);
    List<Tournament> findByUserId(Long id);
    List<Tournament> findAllByOrderByIdDesc();
}
