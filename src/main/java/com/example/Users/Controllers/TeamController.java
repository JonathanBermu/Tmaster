package com.example.Users.Controllers;

import com.example.Users.Services.TeamService;
import com.example.Users.Types.Team.AddTeamType;
import com.example.Users.Types.Team.DeleteTeamType;
import com.example.Users.Types.Team.UpdateTeamType;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TeamController {
    @Autowired
    private TeamService teamService;

    @PostMapping(value = "teams/add_team")
    public ResponseEntity addTeam(@RequestBody AddTeamType request) throws IOException {
        return teamService.addTeam(request);
    }
    @PostMapping(value = "teams/delete_team")
    public ResponseEntity deleteTeam(@RequestBody DeleteTeamType request) {
        return teamService.deleteTeam(request);
    }
    @PostMapping(value = "teams/update_team")
    public ResponseEntity updateTeam(@RequestBody UpdateTeamType request) throws IOException {
        return teamService.updateTeam(request);
    }
}
