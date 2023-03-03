package com.example.Users.Repositories;

import com.example.Users.Models.MatchStats;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchStatRepository extends CrudRepository<MatchStats, Integer> {
    Optional<MatchStats> findById(Integer id);
    List<MatchStats> findByMatchId(Integer matchId);
    List<MatchStats> findByPlayerId(Integer playerId);
    List<MatchStats> findByTeamId(Integer teamId);
    List<MatchStats> findByStatType(Integer statType);
    List<MatchStats> findByScore(Integer score);
}
