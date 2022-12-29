package com.example.Users.Controllers;

import com.example.Users.Services.TournamentService;
import com.example.Users.Types.Tournaments.AddNewTournament;
import com.example.Users.Types.Tournaments.UpdateTournament;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins= "http://localhost:4000")
public class TournamentController {
    @Autowired
    private TournamentService tournamentService;
    @PostMapping(value = "tournaments/create")
    public ResponseEntity createTournament(@RequestBody AddNewTournament request, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws JsonProcessingException {
        return tournamentService.addNewTournament(request, authorization, Optional.empty());
    }
    @PostMapping(value = "tournaments/update")
    public ResponseEntity updateTournament(@RequestBody UpdateTournament request, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws JsonProcessingException {
        return tournamentService.updateTournament(request, authorization, Optional.empty());
    }
    @GetMapping(value = "tournaments/general")
    public ResponseEntity getTournaments() {
        return tournamentService.getAllTournaments();
    }

    @GetMapping(value = "tournaments/{id}")
    public ResponseEntity getTournament(@PathVariable(name = "id") Integer id) {
        return  tournamentService.getTournament(id);
    }

    @GetMapping(value = "tournaments/user")
    public ResponseEntity getUserTournaments(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws JsonProcessingException {
        return tournamentService.getAllTournamentsUser(authorization);
    }
}
