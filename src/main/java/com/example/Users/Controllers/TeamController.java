package com.example.Users.Controllers;

import com.example.Users.Services.TeamService;
import com.example.Users.Types.Team.AddTeamType;
import com.example.Users.Types.Team.DeleteTeamType;
import com.example.Users.Types.Team.UpdateTeamType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin(origins= "http://localhost:4000")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @PostMapping(value = "teams/add_team")
    public ResponseEntity addTeam(@RequestBody AddTeamType request, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws IOException {
        return teamService.addTeam(request, authorization, Optional.empty());
    }
    @PostMapping(value = "teams/delete_team")
    public ResponseEntity deleteTeam(@RequestBody DeleteTeamType request, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws JsonProcessingException {
        return teamService.deleteTeam(request, authorization, Optional.empty());
    }
    @PostMapping(value = "teams/update_team")
    public ResponseEntity updateTeam(@RequestBody UpdateTeamType request, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws IOException {
        return teamService.updateTeam(request, authorization, Optional.empty());
    }
    @GetMapping(value = "teams/all")
    public ResponseEntity getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping(value = "teams/user")
    public ResponseEntity getUserTeam(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws IOException {
        return teamService.getUserTeams(authorization);
    }
    @GetMapping(value = "teams/all/{CountryId}")
    public ResponseEntity getAllTeamsByCountry(@PathVariable("CountryId") Integer countryId) {
        return teamService.getAllTeamsByCountry(countryId);
    }
    @GetMapping(value = "teams/{teamId}")
    public ResponseEntity getTeam(@PathVariable("teamId") Integer teamId) throws IOException {
        return teamService.getTeam(teamId);
    }

}
