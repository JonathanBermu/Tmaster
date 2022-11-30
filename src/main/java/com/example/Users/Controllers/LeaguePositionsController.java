package com.example.Users.Controllers;

import com.example.Users.Services.LeaguePositionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@CrossOrigin(origins= "http://localhost:4000")
public class LeaguePositionsController {
    @Autowired
    private LeaguePositionsService leaguePositionsService;
    @GetMapping(value = "league/{tournament_id}")
    public ResponseEntity getLeaguePositions(@PathVariable(name = "tournament_id") Integer tournamentId) {
        return leaguePositionsService.getLeaguePositions(tournamentId);
    }
}
