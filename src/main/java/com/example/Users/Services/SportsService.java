package com.example.Users.Services;

import com.example.Users.Models.SportsModel;
import com.example.Users.Repositories.SportsRepository;
import com.example.Users.Types.Sports.AddSport;
import com.example.Users.Types.Sports.UpdateSport;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SportsService {
    @Autowired
    private SportsRepository sportsRepository;
    public ResponseEntity addSport(AddSport request) {
        SportsModel sport = new SportsModel();
        sport.setName(request.getName());
        sport.setState(1);
        sport.setHasCards(request.getHasCards());
        sport.setHasPlayers(request.getHasPlayers());
        sport.setHasGoals(request.getHasGoals());
        sport.setHasStats(request.getHasStats());
        sportsRepository.save(sport);
        return new ResponseEntity<>("Sport added successfully", HttpStatus.OK);
    }
    public ResponseEntity updateSport(UpdateSport request) {
        SportsModel sport = sportsRepository.findById(request.getId()).get(0);
        sport.setName(request.getName());
        sport.setHasCards(request.getHasCards());
        sport.setHasPlayers(request.getHasPlayers());
        sport.setHasGoals(request.getHasGoals());
        sport.setHasStats(request.getHasStats());
        sportsRepository.save(sport);
        return new ResponseEntity<>("Sport updated successfully", HttpStatus.OK);
    }

    public ResponseEntity deleteSport(Integer id) {
        SportsModel sport = sportsRepository.findById(id).get(0);
        sport.setState(2);
        sportsRepository.save(sport);
        return new ResponseEntity<>("Sport deleted successfully", HttpStatus.OK);
    }
    public ResponseEntity getSport(Integer id) {
        SportsModel sport = sportsRepository.findById(id).get(0);
        return new ResponseEntity<>(sport, HttpStatus.OK);
    }
    public ResponseEntity getSports() {
        List<SportsModel> sports = (List<SportsModel>) sportsRepository.findAll();
        return new ResponseEntity<>(sports, HttpStatus.OK);
    }
}
