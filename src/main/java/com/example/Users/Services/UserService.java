package com.example.Users.Services;

import com.example.Users.Models.RecoveryCodeModel;
import com.example.Users.Models.UserModel;
import com.example.Users.Repositories.RecoveryCodeRepository;
import com.example.Users.Repositories.UserRepository;
import com.example.Users.Types.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;

@Service
public class UserService {
    public UserService() {
    }
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private RecoveryCodeRepository recoveryCodeRepository;
    StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

    public ResponseEntity addUser(AddUserType request) {
        UserModel newUser = new UserModel();
        newUser.setUsername(request.getUsername());
        newUser.setAge(request.getAge());
        newUser.setEmail(request.getEmail());
        newUser.setRole(request.getRole());
        String encryptedPassword = this.passwordEncryptor.encryptPassword(request.getPassword());
        newUser.setPassword(encryptedPassword);
        try{
            userRepository.save(newUser);
        } catch(DataAccessException exception) {
            Integer repeatedEmail = userRepository.findByEmail(request.getEmail()).size();
            Integer repeatedUsername = userRepository.findByUsername(request.getUsername()).size();
            if(repeatedEmail > 0) {
                return new ResponseEntity<>("Email already in use", HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>("Username already in use", HttpStatus.BAD_REQUEST);
            }

        }
        return new ResponseEntity<>("User saved", HttpStatus.OK);
    }
    public ResponseEntity login(LoginType request) {
        List<UserModel> resuser = userRepository.findByUsername(request.getUsername());
        if(resuser.size() < 1) {
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.UNAUTHORIZED);
        }
        String dataPasswordEncrypted = resuser.get(0).getPassword();
        long dataId = resuser.get(0).getId();
        String dataRole = resuser.get(0).getRole();
        if(passwordEncryptor.checkPassword(request.getPassword(), dataPasswordEncrypted)) {
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            String secret =  "MichaelJacksonIsVeryVeryVeryBlackIMnOtGoingtolie";
            Key secretKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                    SignatureAlgorithm.HS256.getJcaName());
            String jwt = Jwts.builder()
                    .claim("user_id", dataId)
                    .claim("role", dataRole)
                    .signWith(secretKey)
                    .compact();
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Bad credentials", HttpStatus.UNAUTHORIZED);
        }
    }
    public ResponseEntity getUsers() {
        List<UserModel> users = (List<UserModel>) userRepository.findAll();;
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    public ResponseEntity sendRecoverPasswordEmail(@RequestBody SendRecoverMailType email){
        List<UserModel> user = (List<UserModel>) userRepository.findByEmail(email.getEmail());
        if(user.size() == 0) {
            return new ResponseEntity<>("If user exists and email was sent to recover password", HttpStatus.OK);
        }
        Integer user_id = Math.toIntExact(user.get(0).getId());
        if(user.get(0).getId() > 0) {
            //Send email process
            UUID uuid = UUID.randomUUID();
            String EmailToSend = "Recovery password requested, http://myrecoverycode.com/"+uuid;
            emailService.sendMail(EmailToSend);
            RecoveryCodeModel newRecoveryCode = new RecoveryCodeModel();
            newRecoveryCode.setCode(uuid.toString());
            newRecoveryCode.setUserId(user_id);
            newRecoveryCode.setUsed(RecoveryStatesEnum.VALID.getValue());
            newRecoveryCode.setCreatedAt(new Date());
            recoveryCodeRepository.save(newRecoveryCode);
        }
        return new ResponseEntity<>("If user exists and email was sent to recover password", HttpStatus.OK);
    }
    public ResponseEntity recoverPassword(@RequestBody RecoverPasswordType request) {
        String uuid = request.getRecoveryCode();
        if(request.getPassword().equals(request.getRepeatPassword())){
            List<RecoveryCodeModel> listRecoveryCodes = recoveryCodeRepository.findByCode(uuid);
            RecoveryCodeModel recoveryCode = listRecoveryCodes.get(0);
            if(recoveryCode.getUsed() == RecoveryStatesEnum.VALID.getValue()) {
                Date createdAt = recoveryCode.getCreatedAt();
                Date now = new Date();
                long diff = (now.getTime()/1000) - (createdAt.getTime()/1000);
                System.out.println(diff);
                if(diff < 60*30){
                    Integer user_id = recoveryCode.getUserId();
                    List<UserModel> users = userRepository.findById(user_id);
                    UserModel user = users.get(0);
                    String encryptedPassword = this.passwordEncryptor.encryptPassword(request.getPassword());
                    user.setPassword(encryptedPassword);
                    userRepository.save(user);
                    recoveryCode.setUsed(RecoveryStatesEnum.INVALID.getValue());
                    recoveryCodeRepository.save(recoveryCode);
                    return new ResponseEntity<>("Password updated", HttpStatus.OK);
                }
                return new ResponseEntity<>("You can't update your password, the code expired", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("You can't update your password", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Error while updating password", HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity updateUser(UpdateUserType request) {
        UserModel user = (UserModel) userRepository.findById(request.getId()).get(0);
        String originalEmail = user.getEmail();
        String originalUsername = user.getUsername();
        user.setAge(request.getAge());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setAge(request.getAge());
        try{
            userRepository.save(user);
        } catch(DataAccessException exception) {
            Integer repeatedEmail = userRepository.findByEmail(request.getEmail()).size();
            Integer repeatedUsername = userRepository.findByUsername(request.getUsername()).size();
            if(repeatedEmail > 0 && !originalEmail.equals(request.getEmail())) {
                return new ResponseEntity<>("Email is already in use", HttpStatus.BAD_REQUEST);
            } else if (repeatedUsername > 0 && !originalUsername.equals(request.getUsername())){
                return new ResponseEntity<>("Username is already in use", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("There was an error, please try again later", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }
}
