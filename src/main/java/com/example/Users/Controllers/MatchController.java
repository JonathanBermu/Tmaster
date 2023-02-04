package com.example.Users.Controllers;

import com.example.Users.Services.MatchService;
import com.example.Users.Types.Match.SetMatchResults;
import com.example.Users.Types.Match.SetMatchRound;
import com.example.Users.Types.Match.SetMatchTeams;
import com.example.Users.Types.Match.setDateMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins= "http://localhost:4000")
public class MatchController {
    @Autowired
    private MatchService matchService;

    @GetMapping(value = "matches/tournament/{tournament_id}")
    public ResponseEntity getMatchesTournament(@PathVariable(name = "tournament_id") Integer tournamentId) {
        return matchService.getMatchesTournament(tournamentId);
    }

    @PostMapping(value = "matches")
    public ResponseEntity setMatchesResults(@RequestBody SetMatchResults req) {
        return matchService.setMatchResults(req);
    }
    @PostMapping(value = "matches/bulk")
    public ResponseEntity setMatchesResultsBulk(@RequestBody List<SetMatchResults> req) {
        System.out.println(req.get(0).getLocalScore() + req.get(0).getVisitorScore() );
        return matchService.setMatchResultsBulk(req);
    }

    @PostMapping(value = "matches/round/bulk")
    public ResponseEntity setMatchesRoundBulk(@RequestBody List<SetMatchRound> req) {
        return matchService.setBatchRound(req);
    }

    @PostMapping(value = "matches/set_round")
    public ResponseEntity setMatchRound(@RequestBody SetMatchRound req) {
        return matchService.setMatchRound(req);
    }

    @PostMapping(value = "matches/set_date")
    public ResponseEntity setMatchDate(@RequestBody setDateMatch req) {
        return matchService.setDateMatch(req);
    }

    @PostMapping(value = "matches/set_teams")
    public ResponseEntity setMatchTeams(@RequestBody List<SetMatchTeams> req) {
        return matchService.setMatchTeams(req);
    }
}
