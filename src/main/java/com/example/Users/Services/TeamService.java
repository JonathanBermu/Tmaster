package com.example.Users.Services;

import com.example.Users.Models.TeamModel;
import com.example.Users.Repositories.TeamRepository;
import com.example.Users.Types.ImgTypes;
import com.example.Users.Types.Team.AddTeamType;
import com.example.Users.Types.Team.DeleteTeamType;
import com.example.Users.Types.Team.UpdateTeamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private ValidateBase64 validateBase64;
    @Autowired
    private AWSService aws;

    public ResponseEntity addTeam(AddTeamType request) throws IOException {
        if(!validateBase64.isImage(request.getImg())) {
            return new ResponseEntity<> ("Bad request", HttpStatus.BAD_REQUEST);
        }
        String imgType = validateBase64.imageType(request.getImg());
        TeamModel team = new TeamModel();
        team.setName(request.getName());
        String imgName = aws.addFile(request.getImg(), imgType);
        team.setImg(imgName + imgType);
        team.setState(1);
        team.setCountryId(request.getCountryId());
        teamRepository.save(team);
        return new ResponseEntity<>("Team added successfully", HttpStatus.OK);
    }
    public ResponseEntity updateTeam(UpdateTeamType request) throws IOException {
        TeamModel team = teamRepository.findById(request.getId()).get(0);
        if(request.getImg().equals("")) {
            team.setImg(team.getImg());
        } else {
            String imgType = validateBase64.imageType(request.getImg());
            String imgName = aws.addFile(request.getImg(), imgType);
            team.setImg(imgName + imgType);
        }
        team.setName(request.getName());
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
