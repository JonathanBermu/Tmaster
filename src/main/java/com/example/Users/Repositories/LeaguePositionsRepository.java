package com.example.Users.Repositories;

import com.example.Users.Models.LeaguePositions;
import com.example.Users.Models.TeamModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LeaguePositionsRepository extends CrudRepository<LeaguePositions, String> {
    List<LeaguePositions> findByTournamentIdAndTeamId(Integer tournamentId, TeamModel team);
    List<LeaguePositions> findByTournamentId(Integer tournamentId);
}
