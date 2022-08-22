package com.example.Users.Controllers;

import com.example.Users.Services.PlayerService;
import com.example.Users.Types.Player.AddPlayer;
import com.example.Users.Types.Player.DeletePlayer;
import com.example.Users.Types.Player.UpdatePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {
    @Autowired
    private PlayerService playerService;
    @PostMapping(value = "players/add_player")
    public ResponseEntity addUser(@RequestBody AddPlayer request) {
        return playerService.addPlayer(request);

    }
    @PostMapping(value = "players/update_player")
    public ResponseEntity updatePlayer(@RequestBody UpdatePlayer request) {
        return playerService.updatePlayer(request);
    }
    @PostMapping(value = "players/delete_player")
    public ResponseEntity deleteUser(@RequestBody DeletePlayer request) {
        return playerService.deletePlayer(request);
    }
}
