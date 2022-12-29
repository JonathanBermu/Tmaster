package com.example.Users.Mocks;

import com.example.Users.Models.CountryModel;
import com.example.Users.Repositories.CountryRepository;

import java.util.List;
import java.util.Optional;

public class CountryRepositoryMock implements CountryRepository {
    @Override
    public List<CountryModel> findById(Integer id) {
        CountryModel country = new CountryModel();
        country.setState(1);
        country.setName("United states");
        country.setId(1);
        List<CountryModel> countries = List.of(country);
        return countries;
    }

    @Override
    public <S extends CountryModel> S save(S entity) {
        return null;
    }

    @Override
    public <S extends CountryModel> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<CountryModel> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<CountryModel> findAll() {
        return null;
    }

    @Override
    public Iterable<CountryModel> findAllById(Iterable<String> strings) {
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
    public void delete(CountryModel entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends CountryModel> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
