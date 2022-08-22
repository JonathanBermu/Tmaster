package com.example.Users.Services;

import com.example.Users.Models.TeamModel;
import com.example.Users.Repositories.TeamRepository;
import com.example.Users.Types.Team.AddTeamType;
import com.example.Users.Types.Team.DeleteTeamType;
import com.example.Users.Types.Team.UpdateTeamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    public ResponseEntity addTeam(AddTeamType request) {
        TeamModel team = new TeamModel();
        team.setName(request.getName());
        team.setImg(request.getImg());
        team.setState(1);
        team.setCountryId(request.getCountryId());
        teamRepository.save(team);
        return new ResponseEntity<>("Team added successfully", HttpStatus.OK);
    }
    public ResponseEntity updateTeam(UpdateTeamType request) {
        TeamModel team = teamRepository.findById(request.getId()).get(0);
        team.setName(request.getName());
        team.setImg(request.getImg());
        team.setCountryId(request.getCountryId());
        teamRepository.save(team);
        return new ResponseEntity<>("Team updated succesfully", HttpStatus.OK);
    }

    public ResponseEntity deleteTeam(DeleteTeamType request){
        TeamModel team = teamRepository.findById(request.getId()).get(0);
        team.setState(2);
        teamRepository.save(team);
        return new ResponseEntity<>("Team has been deleted", HttpStatus.OK);
    }
}
