package com.example.Users.Repositories;

import com.example.Users.Models.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserModel, String> {
    List<UserModel> findByUsername(String username);
    List<UserModel> findById(long id);
    List<UserModel> findByEmail(String email);
}
