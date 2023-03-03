package com.example.Users.Repositories;

import com.example.Users.Models.Tournament;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
@Repository
public interface TournamentRepository extends CrudRepository<Tournament, String> {
    List<Tournament> findById(Integer id);
    List<Tournament> findByUserId(Long id);
    List<Tournament> findAllByOrderByIdDesc();
    Page<Tournament> findByNameContainingIgnoreCaseOrderById(String filter, Pageable pageable);
    Page<Tournament> findByUserIdAndNameContainingIgnoreCaseOrderById(Long id, String filter, Pageable pageable);
}
