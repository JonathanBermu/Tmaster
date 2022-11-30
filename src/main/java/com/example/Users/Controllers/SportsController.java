package com.example.Users.Controllers;

import com.example.Users.Services.SportsService;
import com.example.Users.Types.Sports.AddSport;
import com.example.Users.Types.Sports.SportId;
import com.example.Users.Types.Sports.UpdateSport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins= "http://localhost:4000")
public class SportsController {
    @Autowired
    private SportsService sportsService;
    @PostMapping("sports/create")
    public ResponseEntity addSport(@RequestBody AddSport request) {
        return sportsService.addSport(request);
    }
    @PostMapping("sports/update")
    public ResponseEntity updateSport(@RequestBody UpdateSport request) {
        return sportsService.updateSport(request);
    }
    @PostMapping("sports/delete")
    public ResponseEntity deleteSport(@RequestBody SportId request) {
        return sportsService.deleteSport(request.getId());
    }
    @GetMapping("sports/get/{sportId}")
    public ResponseEntity getSport(@PathVariable (name = "sportId") Integer id) {
        return sportsService.getSport(id);
    }
    @GetMapping("sports/get_all")
    public ResponseEntity getSports() {
        return sportsService.getSports();
    }
}
