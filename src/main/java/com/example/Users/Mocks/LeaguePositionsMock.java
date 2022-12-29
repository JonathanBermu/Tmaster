package com.example.Users.Mocks;

import com.example.Users.Models.LeaguePositions;
import com.example.Users.Models.TeamModel;
import com.example.Users.Repositories.LeaguePositionsRepository;

import java.util.List;
import java.util.Optional;

public class LeaguePositionsMock implements LeaguePositionsRepository {
    @Override
    public List<LeaguePositions> findByTournamentIdAndTeamId(Integer tournamentId, TeamModel team) {
        return null;
    }

    @Override
    public List<LeaguePositions> findByTournamentId(Integer tournamentId) {
        return null;
    }

    @Override
    public <S extends LeaguePositions> S save(S entity) {
        return null;
    }

    @Override
    public <S extends LeaguePositions> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<LeaguePositions> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<LeaguePositions> findAll() {
        return null;
    }

    @Override
    public Iterable<LeaguePositions> findAllById(Iterable<String> strings) {
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
    public void delete(LeaguePositions entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends LeaguePositions> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
