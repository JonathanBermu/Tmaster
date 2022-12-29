package com.example.Users.Services;

import com.example.Users.Types.Tournaments.AddNewTournament;
import com.example.Users.Types.Tournaments.UpdateTournament;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TournamentServiceTest {
    private String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyLCJyb2xlIjoiMSJ9.v9mfGBFcspmU-C3-38AVxIviP0CWmI2rJ-aQ2tPPbl8";
    @Test
    void updateTournamentSuccess() throws JsonProcessingException {
        UpdateTournament update = new UpdateTournament();
        update.setName("Any name");
        update.setId(22);
        TournamentService tournamentService = new TournamentService();
        String token2 = "user2";
        ResponseEntity res = tournamentService.updateTournament(update, token2, Optional.of(true));
        System.out.println(res.toString() +"ccc"+ res.getStatusCode() +"ccc"+ res.getStatusCodeValue());
        assertEquals(400, res.getStatusCodeValue());
    }
    void updateTournament() throws JsonProcessingException {
        UpdateTournament update = new UpdateTournament();
        update.setName("Any name");
        update.setId(22);
        TournamentService tournamentService = new TournamentService();
        ResponseEntity res = tournamentService.updateTournament(update, token, Optional.of(true));
        assertEquals(400, res.getStatusCodeValue());
    }

    @Test
    void addNewTournament() throws JsonProcessingException {
        AddNewTournament tournament = new AddNewTournament();
        tournament.setName("name");
        tournament.setType(2);
        tournament.setSport_id(1);
        tournament.setTeams(6);
        tournament.setTeamsIds(List.of(1,2,3,4,5,6));
        TournamentService tournamentService = new TournamentService();
        tournamentService.addNewTournament(tournament, token, Optional.of(true));
    }
}