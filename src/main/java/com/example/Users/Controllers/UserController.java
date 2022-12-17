package com.example.Users.Controllers;

import com.example.Users.Repositories.RecoveryCodeRepository;
import com.example.Users.Repositories.UserRepository;
import com.example.Users.Services.RoleService;
import com.example.Users.Services.UserService;
import com.example.Users.Types.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@CrossOrigin(origins= "http://localhost:4000")
public class UserController {

    StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    private final ObjectMapper mapper = new ObjectMapper();


    @Autowired
    private RecoveryCodeRepository recoveryCodeRepository;
    @PostMapping(value = "/add_user")
    public ResponseEntity addUser(@RequestBody AddUserType request, @RequestHeader (HttpHeaders.AUTHORIZATION) String authorization) throws JsonProcessingException {
        if(!roleService.isAdmin(authorization)){
            return new ResponseEntity<>("You can't do this action", HttpStatus.UNAUTHORIZED);
        }
        return userService.addUser(request);
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody LoginType request) throws IOException {
        return userService.login(request);
    }
    @PostMapping(value = "/get_user")
    public ResponseEntity getUser(@RequestHeader (HttpHeaders.AUTHORIZATION) String authorization) throws JsonProcessingException {
        return userService.getUser(authorization);
    }
    @GetMapping(value = "/get_users")
    public ResponseEntity getUsers(@RequestHeader (HttpHeaders.AUTHORIZATION) String authorization) throws JsonProcessingException {
        if(!roleService.isAdmin(authorization)){
            return new ResponseEntity<>("You can't do this action", HttpStatus.UNAUTHORIZED);
        }
        return userService.getUsers();
    }
    @GetMapping(value = "/send_recover_password_email")
    public ResponseEntity sendRecoverPassword(@RequestBody SendRecoverMailType email) throws JsonProcessingException {
        return userService.sendRecoverPasswordEmail(email);
    }
    @PostMapping(value = "/recover_password")
    public ResponseEntity recoverPassword (@RequestBody RecoverPasswordType request) {
        return userService.recoverPassword(request);
    }
    @PostMapping(value = "/update_user")
    public ResponseEntity updateUser(@RequestHeader (HttpHeaders.AUTHORIZATION) String authorization, @RequestBody UpdateUserType request) throws JsonProcessingException {
        if(!roleService.isAdmin(authorization)){
            return new ResponseEntity<>("You can't do this action", HttpStatus.UNAUTHORIZED);
        }
        return userService.updateUser(request);
    }
    @PostMapping(value = "/oauth_login")
    public ResponseEntity oauthLogin(@RequestBody OauthLogin login) throws JsonProcessingException {
        return userService.OAuthLogin(login);
    }
}
