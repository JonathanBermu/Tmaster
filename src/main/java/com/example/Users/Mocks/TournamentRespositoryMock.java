package com.example.Users.Mocks;

import com.example.Users.Models.SportsModel;
import com.example.Users.Models.Tournament;
import com.example.Users.Models.UserModel;
import com.example.Users.Repositories.TournamentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class TournamentRespositoryMock implements TournamentRepository {
    @Override
    public List<Tournament> findById(Integer id) {
        SportsModel sport = new SportsModel();
        sport.setId(1);
        sport.setName("Any name");
        UserModel user = new UserModel();
        user.setId(1L);
        Tournament tournament = new Tournament();
        tournament.setName("ANy name");
        tournament.setUser(user);
        tournament.setRounds(4);
        tournament.setSport(sport);
        tournament.setTeams(4);
        return List.of(tournament);
    }

    @Override
    public List<Tournament> findByUserId(Long id) {
        return null;
    }

    @Override
    public List<Tournament> findAllByOrderByIdDesc() {
        return null;
    }

    @Override
    public Page<Tournament> findByNameContainingIgnoreCaseOrderById(String filter, Pageable pageable) {
        return null;
    }


    @Override
    public Page<Tournament> findByUserIdAndNameContainingIgnoreCaseOrderById(Long id, String filter, Pageable pageable) {
        return null;
    }


    @Override
    public <S extends Tournament> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Tournament> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Tournament> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<Tournament> findAll() {
        return null;
    }

    @Override
    public Iterable<Tournament> findAllById(Iterable<String> strings) {
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
    public void delete(Tournament entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Tournament> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
