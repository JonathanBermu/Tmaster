package com.example.Users.Services;

import com.example.Users.Models.TeamModel;
import com.example.Users.Models.UserModel;
import com.example.Users.Types.Team.AddTeamType;
import com.example.Users.Types.Team.DeleteTeamType;
import com.example.Users.Types.Team.UpdateTeamType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TeamServiceTest {
    private String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJyb2xlIjoiMSJ9.LtSq0JjigSzPyWMx4xeh4Lawo_YHd1Umz0UCGSkCoIQ";
    @Test
    void addTeam() throws IOException {
        TeamService teamService = new TeamService();
        AddTeamType newTeam = new AddTeamType();
        teamService.addTeam(newTeam, this.token, Optional.of(true));
    }

    @Test
    void updateTeam() throws IOException {
        TeamService teamService = new TeamService();
        UpdateTeamType updateTeam = new UpdateTeamType();
        updateTeam.setId(1);
        updateTeam.setImg("anything");
        teamService.updateTeam(updateTeam, this.token, Optional.of(true));
    }

    @Test
    void deleteTeam() throws JsonProcessingException {
        DeleteTeamType teamToDelete = new DeleteTeamType();
        teamToDelete.setId(1);
        TeamService teamService = new TeamService();
        teamService.deleteTeam(teamToDelete, this.token, Optional.of(true));
    }

    @Test
    List<TeamModel> getTeam() {
        UserModel user = new UserModel();
        user.setId(1L);
        TeamModel team = new TeamModel();
        team.setName("Fc anything");
        team.setUser(user);
        team.setId(1);
        team.setImg("UIUA FHAOI HFOIAHFO IAHOGI HADOIGH OAIHG ODAIGH OAIH");
        return List.of(team);
    }
}