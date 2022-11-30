package com.example.Users.Services;

import com.example.Users.Models.LeaguePositions;
import com.example.Users.Repositories.LeaguePositionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaguePositionsService {
    @Autowired
    private LeaguePositionsRepository leaguePositionsRepository;

    public ResponseEntity getLeaguePositions(Integer tournamentId) {
        List<LeaguePositions> response = leaguePositionsRepository.findByTournamentId(tournamentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
