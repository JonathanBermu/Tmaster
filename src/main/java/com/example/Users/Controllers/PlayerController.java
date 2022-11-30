package com.example.Users.Controllers;

import com.example.Users.Services.PlayerService;
import com.example.Users.Types.Player.AddPlayer;
import com.example.Users.Types.Player.DeletePlayer;
import com.example.Users.Types.Player.UpdatePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins= "http://localhost:4000")
public class PlayerController {
    @Autowired
    private PlayerService playerService;
    @PostMapping(value = "players/add_player")
    public ResponseEntity addUser(@RequestBody AddPlayer request) throws IOException {
        return playerService.addPlayer(request);

    }
    @PostMapping(value = "players/update_player")
    public ResponseEntity updatePlayer(@RequestBody UpdatePlayer request) throws IOException {
        return playerService.updatePlayer(request);
    }
    @PostMapping(value = "players/delete_player")
    public ResponseEntity deleteUser(@RequestBody DeletePlayer request) {
        return playerService.deletePlayer(request);
    }

    @GetMapping(value = "players/all")
    public ResponseEntity getAllPlayers() {
        return playerService.getAllPlayers();
    }
    @GetMapping(value = "players/all/country/{countryId}")
    public ResponseEntity getAllPlayersByCountryId(@PathVariable("countryId") Integer countryId) {
        return playerService.getAllPlayersByCountry(countryId);
    }
    @GetMapping(value = "players/all/team/{teamId}")
    public ResponseEntity getAllPlayersByTeamId(@PathVariable("teamId") Integer teamId) {
        return playerService.getAllPlayersByTeam(teamId);
    }
    @GetMapping(value = "players/{playerId}")
    public ResponseEntity getPlayer(@PathVariable("playerId") Integer playerId) {
        return playerService.getPlayer(playerId);
    }
}
