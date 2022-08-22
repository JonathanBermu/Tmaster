package com.example.Users.Services;

import com.example.Users.Models.PlayerModel;
import com.example.Users.Repositories.PlayerRepository;
import com.example.Users.Types.Player.AddPlayer;
import com.example.Users.Types.Player.DeletePlayer;
import com.example.Users.Types.Player.UpdatePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;
    public ResponseEntity addPlayer(AddPlayer request) {
        PlayerModel player = new PlayerModel();
        player.setName(request.getName());
        player.setLastName(request.getLastName());
        player.setBirthDate(request.getBirthDate());
        player.setImg(request.getImg());
        player.setHeight(request.getHeight());
        player.setWeight(request.getWeight());
        player.setRightHanded(request.getRightHanded());
        player.setCountryId(request.getCountryId());
        player.setTeamId(request.getTeamId());
        player.setCreatedAt(new Date());
        player.setState(1);
        playerRepository.save(player);
        return new ResponseEntity("Player added successfully", HttpStatus.OK);
    }
    public ResponseEntity updatePlayer(UpdatePlayer request){
        PlayerModel player = playerRepository.getById(request.getId()).get(0);
        player.setName(request.getName());
        player.setLastName(request.getLastName());
        player.setBirthDate(request.getBirthDate());
        player.setImg(request.getImg());
        player.setHeight(request.getHeight());
        player.setWeight(request.getWeight());
        player.setRightHanded(request.getRightHanded());
        player.setCountryId(request.getCountryId());
        player.setTeamId(request.getTeamId());
        playerRepository.save(player);
        return new ResponseEntity("Player updated successfully", HttpStatus.OK);
    }
    public ResponseEntity deletePlayer(DeletePlayer request) {
        PlayerModel player = playerRepository.getById(request.getId()).get(0);
        player.setState(2);
        playerRepository.save(player);
        return new ResponseEntity("Player deleted successfully", HttpStatus.OK);
    }
}
