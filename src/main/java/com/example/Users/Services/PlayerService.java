package com.example.Users.Services;

import com.example.Users.Models.CountryModel;
import com.example.Users.Models.PlayerModel;
import com.example.Users.Models.TeamModel;
import com.example.Users.Repositories.CountryRepository;
import com.example.Users.Repositories.PlayerRepository;
import com.example.Users.Repositories.TeamRepository;
import com.example.Users.Types.Player.AddPlayer;
import com.example.Users.Types.Player.DeletePlayer;
import com.example.Users.Types.Player.UpdatePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private AWSService aws;
    @Autowired
    private ValidateBase64 validateBase64;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private TeamRepository teamRepository;
    public ResponseEntity addPlayer(AddPlayer request) throws IOException {
        /*if(!validateBase64.isImage(request.getImg())) {
            return new ResponseEntity<> ("Bad request", HttpStatus.BAD_REQUEST);
        }*/
        CountryModel country = countryRepository.findById(request.getCountryId()).get(0);
        TeamModel team = teamRepository.findById(request.getTeamId()).get(0);
        /*String imgType = validateBase64.imageType(request.getImg());*/
        PlayerModel player = new PlayerModel();
        player.setName(request.getName());
        player.setLastName(request.getLastName());
        player.setBirthDate(request.getBirthDate());
        /*String imgName = aws.addFile(request.getImg(), imgType);
        player.setImg(imgName + imgType);*/
        player.setHeight(request.getHeight());
        player.setWeight(request.getWeight());
        player.setRightHanded(request.getRightHanded());
        player.setTeam(team);
        player.setCreatedAt(new Date());
        player.setState(1);
        player.setCountry(country);
        playerRepository.save(player);
        return new ResponseEntity("Player added successfully", HttpStatus.OK);
    }
    public ResponseEntity updatePlayer(UpdatePlayer request) throws IOException {
        System.out.println("bobobobo" + request.getId()+ "------"+ playerRepository.findById(request.getId()).size());
        PlayerModel player = playerRepository.findById(request.getId()).get(0);
        /*if(request.getImg().equals("")) {
            player.setImg(player.getImg());
        } else {
            String imgType = validateBase64.imageType(request.getImg());
            String imgName = aws.addFile(request.getImg(), imgType);
            player.setImg(imgName + imgType);
        }*/
        TeamModel team = teamRepository.findById(request.getTeamId()).get(0);
        CountryModel country = countryRepository.findById(request.getCountryId()).get(0);
        player.setName(request.getName());
        player.setLastName(request.getLastName());
        player.setBirthDate(request.getBirthDate());
        player.setHeight(request.getHeight());
        player.setWeight(request.getWeight());
        player.setRightHanded(request.getRightHanded());
        player.setCountry(country);
        player.setTeam(team);
        playerRepository.save(player);
        return new ResponseEntity("Player updated successfully", HttpStatus.OK);
    }
    public ResponseEntity deletePlayer(DeletePlayer request) {
        PlayerModel player = playerRepository.findById(request.getId()).get(0);
        player.setState(2);
        playerRepository.save(player);
        return new ResponseEntity("Player deleted successfully", HttpStatus.OK);
    }
    public ResponseEntity getAllPlayers(String filter, int page) {
        Pageable pageable = PageRequest.of(page, 20);
        if(filter.equals("null")){
            filter = "";
        }
        Page<PlayerModel> players = playerRepository.findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(filter, filter, pageable);
        return new ResponseEntity(players, HttpStatus.OK);
    }
    public ResponseEntity getAllPlayersByCountry(Integer countryId) {
        CountryModel country = countryRepository.findById(countryId).get(0);
        List<PlayerModel> players = playerRepository.findByCountryId(country.getId());
        return new ResponseEntity(players, HttpStatus.OK);
    }
    public ResponseEntity getAllPlayersByTeam(Integer teamId) {
        TeamModel team = teamRepository.findById(teamId).get(0);
        List<PlayerModel> players = playerRepository.findByTeamId(team.getId());
        return new ResponseEntity(players, HttpStatus.OK);
    }
    public ResponseEntity getPlayer(Integer playerId) {
        PlayerModel player = playerRepository.findById(playerId).get(0);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }
}
