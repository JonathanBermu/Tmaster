package com.example.Users.Repositories;

import com.example.Users.Models.MatchModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MatchRepository extends CrudRepository<MatchModel, String> {
    List<MatchModel> findById(Integer id);
    List<MatchModel> findByTournamentIdOrderByRoundAsc(Integer id);
    List<MatchModel> findByTournamentIdAndWinner(Integer tournament_id, Integer winner);
    List<MatchModel> findByTournamentIdAndRound(Integer tournamentId, Integer round);
    List<MatchModel> findByTournamentIdAndState(Integer tournamentId, Integer state);
}
