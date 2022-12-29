package com.example.Users.Mocks;

import com.example.Users.Models.MatchModel;
import com.example.Users.Repositories.MatchRepository;

import java.util.List;
import java.util.Optional;

public class MatchRepositoryMock implements MatchRepository {
    @Override
    public List<MatchModel> findById(Integer id) {
        return null;
    }

    @Override
    public List<MatchModel> findByTournamentIdOrderByRoundAsc(Integer id) {
        return null;
    }

    @Override
    public List<MatchModel> findByTournamentIdAndWinner(Integer tournament_id, Integer winner) {
        return null;
    }

    @Override
    public List<MatchModel> findByTournamentIdAndRound(Integer tournamentId, Integer round) {
        return null;
    }

    @Override
    public List<MatchModel> findByTournamentIdAndState(Integer tournamentId, Integer state) {
        return null;
    }

    @Override
    public <S extends MatchModel> S save(S entity) {
        return null;
    }

    @Override
    public <S extends MatchModel> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<MatchModel> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<MatchModel> findAll() {
        return null;
    }

    @Override
    public Iterable<MatchModel> findAllById(Iterable<String> strings) {
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
    public void delete(MatchModel entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends MatchModel> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
