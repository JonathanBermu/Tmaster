package com.example.Users.Mocks;

import com.example.Users.Models.SportsModel;
import com.example.Users.Repositories.SportsRepository;

import java.util.List;
import java.util.Optional;

public class SportsRepositoryMock implements SportsRepository {
    @Override
    public List<SportsModel> findById(Integer id) {
        SportsModel sport = new SportsModel();
        sport.setName("Football");
        sport.setId(1);
        sport.setState(1);
        List<SportsModel> sports = List.of(sport);
        return sports;
    }

    @Override
    public <S extends SportsModel> S save(S entity) {
        return null;
    }

    @Override
    public <S extends SportsModel> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<SportsModel> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<SportsModel> findAll() {
        return null;
    }

    @Override
    public Iterable<SportsModel> findAllById(Iterable<String> strings) {
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
    public void delete(SportsModel entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends SportsModel> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
