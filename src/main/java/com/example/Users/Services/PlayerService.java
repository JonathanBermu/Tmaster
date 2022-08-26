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

import java.io.IOException;
import java.util.Date;
@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private AWSService aws;
    @Autowired
    private ValidateBase64 validateBase64;
    public ResponseEntity addPlayer(AddPlayer request) throws IOException {
        if(!validateBase64.isImage(request.getImg())) {
            return new ResponseEntity<> ("Bad request", HttpStatus.BAD_REQUEST);
        }
        String imgType = validateBase64.imageType(request.getImg());
        PlayerModel player = new PlayerModel();
        player.setName(request.getName());
        player.setLastName(request.getLastName());
        player.setBirthDate(request.getBirthDate());
        String imgName = aws.addFile(request.getImg(), imgType);
        player.setImg(imgName + imgType);
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
    public ResponseEntity updatePlayer(UpdatePlayer request) throws IOException {
        PlayerModel player = playerRepository.getById(request.getId()).get(0);
        if(request.getImg().equals("")) {
            player.setImg(player.getImg());
        } else {
            String imgType = validateBase64.imageType(request.getImg());
            String imgName = aws.addFile(request.getImg(), imgType);
            player.setImg(imgName + imgType);
        }
        player.setName(request.getName());
        player.setLastName(request.getLastName());
        player.setBirthDate(request.getBirthDate());
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
