package com.example.Users.Mocks;

import com.example.Users.Models.SportsModel;
import com.example.Users.Models.TeamModel;
import com.example.Users.Models.UserModel;
import com.example.Users.Repositories.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TeamRepositoryMock implements TeamRepository {

    @Override
    public List<TeamModel> findById(Integer id) {
        SportsModel sportsModel = new SportsModel();
        sportsModel.setName("Football");
        sportsModel.setId(1);
        sportsModel.setState(1);

        UserModel user = new UserModel();
        user.setId(1L);

        TeamModel team = new TeamModel();
        team.setSport(sportsModel);
        team.setName("Fc anything");
        team.setUser(user);
        return List.of(team);
    }

    @Override
    public <S extends TeamModel> S save(S entity) {
        return null;
    }

    @Override
    public <S extends TeamModel> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<TeamModel> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<TeamModel> findAll() {
        return null;
    }

    @Override
    public Iterable<TeamModel> findAllById(Iterable<String> strings) {
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
    public void delete(TeamModel entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends TeamModel> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<TeamModel> findByCountryId(Integer id) {
        return null;
    }

    @Override
    public Page<TeamModel> findByUserIdAndStateAndNameContainingIgnoreCaseOrderBySportId(Long id, Integer state, String filter, Pageable pageable) {
        return null;
    }



    @Override
    public List<TeamModel> findByState(Integer state) {
        return null;
    }
}
