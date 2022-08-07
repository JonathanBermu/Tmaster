package com.example.Users.Controllers;

import com.example.Users.Models.UserModel;
import com.example.Users.Repositories.UserRepository;
import com.example.Users.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/users")
    public List<UserModel> getUser(@RequestBody UserModel userModel) {
        return userService.getUser(userModel);
    }

    @PostMapping(value = "/add_user")
    public @ResponseBody String addUser(@RequestParam String username, @RequestParam String id) {
        UserModel newUser = new UserModel();
        newUser.setId(id);
        newUser.setUsername(username);
        userRepository.save(newUser);
        return "saved";
    }
    @GetMapping(value = "/login")
    public ResponseEntity login(@RequestParam String username, @RequestParam String id) {
        Optional<UserModel> res = userRepository.findById(id);
        String user = res.map(UserModel::getUsername).orElseGet(() -> "");
        if(user.equals(username)) {
            return new ResponseEntity<>("Hello world", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Username or id is are not valid", HttpStatus.UNAUTHORIZED);
        }
    }
}
