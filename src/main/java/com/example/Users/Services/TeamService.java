package com.example.Users.Services;

import com.example.Users.Models.CountryModel;
import com.example.Users.Models.SportsModel;
import com.example.Users.Models.TeamModel;
import com.example.Users.Models.UserModel;
import com.example.Users.Repositories.CountryRepository;
import com.example.Users.Repositories.SportsRepository;
import com.example.Users.Repositories.TeamRepository;
import com.example.Users.Repositories.UserRepository;
import com.example.Users.Types.Team.AddTeamType;
import com.example.Users.Types.Team.DeleteTeamType;
import com.example.Users.Types.Team.UpdateTeamType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private ValidateBase64 validateBase64;
    @Autowired
    private AWSService aws;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private SportsRepository sportsRepository;

    @Autowired
    private PayloadService payloadService;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity addTeam(AddTeamType request, String auth) throws IOException {
        if(!validateBase64.isImage(request.getImg())) {
            return new ResponseEntity<> ("Bad request", HttpStatus.BAD_REQUEST);
        }
        JsonNode userPayload = payloadService.getPayload(auth);
        CountryModel country = countryRepository.findById(request.getCountryId()).get(0);
        String imgType = validateBase64.imageType(request.getImg());
        TeamModel team = new TeamModel();
        team.setName(request.getName());
        String imgName = aws.addFile(request.getImg(), imgType);
        team.setImg(imgName + imgType);
        team.setState(1);
        team.setCountry(country);
        SportsModel sport = sportsRepository.findById(request.getSport_id()).get(0);
        team.setSport(sport);
        UserModel user = userRepository.findById(Math.toIntExact(userPayload.get("user_id").asInt())).get(0);
        team.setUser(user);
        teamRepository.save(team);
        return new ResponseEntity<>("Team added successfully", HttpStatus.OK);

    }
    public ResponseEntity updateTeam(UpdateTeamType request, String auth) throws IOException {
        JsonNode userPayload = payloadService.getPayload(auth);
        TeamModel team = teamRepository.findById(request.getId()).get(0);
        if(team.getUser().getId() != userPayload.get("user_id").asInt()) {
            return new ResponseEntity<>("You can't perform this action", HttpStatus.BAD_REQUEST);
        }
        if(request.getImg().equals("")) {
            team.setImg(team.getImg());
        } else {
            String imgType = validateBase64.imageType(request.getImg());
            String imgName = aws.addFile(request.getImg(), imgType);
            team.setImg(imgName + imgType);
        }
        CountryModel country = countryRepository.findById(request.getCountryId()).get(0);
        SportsModel sport = sportsRepository.findById(request.getSport_id()).get(0);
        team.setName(request.getName());
        team.setCountry(country);
        team.setSport(sport);
        teamRepository.save(team);
        return new ResponseEntity<>("Team updated succesfully", HttpStatus.OK);
    }

    public ResponseEntity deleteTeam(DeleteTeamType request, String auth) throws JsonProcessingException {
        JsonNode userPayload = payloadService.getPayload(auth);
        TeamModel team = teamRepository.findById(request.getId()).get(0);
        System.out.println(team.getUser().getId() + "----" + userPayload.get("user_id").asInt());
        if(team.getUser().getId() !=  userPayload.get("user_id").asInt()) {
            return new ResponseEntity<>("You can't perform this action", HttpStatus.BAD_REQUEST);
        }
        System.out.println("ok3");
        team.setState(2);
        teamRepository.save(team);
        return new ResponseEntity<>("Team has been deleted", HttpStatus.OK);
    }

    public ResponseEntity getAllTeams() {
        List<TeamModel> teams = teamRepository.findByState(1);
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    public ResponseEntity getUserTeams(String auth) throws IOException {
        JsonNode userPayload = payloadService.getPayload(auth);
        System.out.println(userPayload.get("user_id"));
        Integer userId = Math.toIntExact(userPayload.get("user_id").asLong());
        List<TeamModel> teams = teamRepository.findByUserIdAndStateOrderBySportId(Long.valueOf(userId), 1);
        for (Integer i = 0; i < teams.size(); i++) {
            teams.get(i).setImg( aws.getObject(teams.get(i).getImg()));
        }
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    public ResponseEntity getAllTeamsByCountry(Integer countryId) {
        CountryModel country = countryRepository.findById(countryId).get(0);
        List<TeamModel> teams = teamRepository.findByCountryId(country.getId());
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }
    public ResponseEntity getTeam(Integer teamId) throws IOException {
        TeamModel team = teamRepository.findById(teamId).get(0);
        team.setImg(aws.getObject(team.getImg()));
        return new ResponseEntity<>(team, HttpStatus.OK);
    }
}
