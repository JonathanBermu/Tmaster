package com.example.Users.Mocks;

import com.example.Users.Models.UserModel;
import com.example.Users.Repositories.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserRepositoryMock implements UserRepository {
    @Override
    public List<UserModel> findByUsername(String username) {
        UserModel user = new UserModel();
        user.setId(1L);
        user.setUsername("Anything");
        return List.of(user);
    }

    @Override
    public List<UserModel> findById(long id) {
        UserModel user = new UserModel();
        user.setId(1L);
        user.setUsername("Anything");
        return List.of(user);
    }

    @Override
    public List<UserModel> findByEmail(String email) {
        return null;
    }

    @Override
    public List<UserModel> findByAuthId(String authId) {
        return null;
    }

    @Override
    public <S extends UserModel> S save(S entity) {
        return null;
    }

    @Override
    public <S extends UserModel> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<UserModel> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<UserModel> findAll() {
        return null;
    }

    @Override
    public Iterable<UserModel> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(UserModel entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends UserModel> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
