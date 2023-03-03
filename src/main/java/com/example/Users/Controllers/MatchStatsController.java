package com.example.Users.Controllers;

import com.example.Users.Services.MatchStatService;
import com.example.Users.Types.MatchType.AddMatchStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins= "http://localhost:4000")
public class MatchStatsController {
    @Autowired
    private MatchStatService matchStatService;

    @PostMapping(value = "/match-stat")
    public ResponseEntity addMatchStat(@RequestBody AddMatchStat matchStat) {
        return matchStatService.addMatchStat2(matchStat);
    }
    @GetMapping(value = "/match-stat/{id}")
    public ResponseEntity getMatchStats(@PathVariable Integer id) {
        return matchStatService.getMatchStats(id);
    }

    @DeleteMapping(value = "/match-stat/{id}")
    public void deleteMatchStat(@PathVariable Integer id) {
        matchStatService.deleteMatchStat(id);
    }
}