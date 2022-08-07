package com.example.Users.Services;

import com.example.Users.Models.UserModel;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.swing.plaf.nimbus.State;
import java.util.List;
import java.util.Arrays;

@Service
public class UserService {
    public UserService() {
    }

    public List<UserModel> getUser(UserModel userModel) {
        UserModel userModel1 = new UserModel("1", "Pablo");
        UserModel userModel2 = new UserModel("2", "Juan");
        UserModel userModel3 = new UserModel("3", "Manuel");
        return Arrays.asList(userModel1, userModel2, userModel3, userModel);
    }
}
