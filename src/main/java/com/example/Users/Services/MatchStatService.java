package com.example.Users.Services;

import com.example.Users.Models.*;
import com.example.Users.Repositories.*;
import com.example.Users.Types.Match.MatchDetails;
import com.example.Users.Types.MatchType.AddMatchStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchStatService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private MatchStatRepository matchStatRepository;

    @Autowired
    private MatchStatTypeRepository matchStatTypeRepository;


    public ResponseEntity<?> addMatchStat2(AddMatchStat matchStat) {
        List<PlayerModel> player = null;

        List<MatchStatTypes> matchStatType = matchStatTypeRepository.findById(matchStat.getStatType());
        if(matchStatType.size() == 0) {
            return new ResponseEntity<>("Bad stat type", HttpStatus.BAD_REQUEST);
        }
        if(matchStatType.get(0).getTeamStat() != 1 && matchStat.getPlayerId() == null) {
            return new ResponseEntity<>("Bad stat type", HttpStatus.BAD_REQUEST);
        }
        if(matchStat.getPlayerId() != null) {
            player = playerRepository.findById(matchStat.getPlayerId());
            if (player.size() == 0) {
                return new ResponseEntity<>("The player doesn't exist", HttpStatus.BAD_REQUEST);
            }
        }
        List<TeamModel> team = teamRepository.findById(matchStat.getTeamId());
        if (team.size() == 0) {
            return new ResponseEntity<>("The team doesn't exist", HttpStatus.BAD_REQUEST);
        }

        List<MatchModel> match = matchRepository.findById(matchStat.getMatchId());
        if (match.size() == 0) {
            return new ResponseEntity<>("The match doesn't exist", HttpStatus.BAD_REQUEST);
        }

        MatchStats newMatchStat = new MatchStats();
        newMatchStat.setMinute(matchStat.getMinute());
        MatchStatTypes matchType = matchStatTypeRepository.findById(matchStat.getStatType()).get(0);
        newMatchStat.setStatType(matchType);
        newMatchStat.setScore(matchStat.getScore());
        newMatchStat.setMatch(match.get(0));
        newMatchStat.setPlayer(player != null ? player.get(0) : null);
        newMatchStat.setTeam(team.get(0));
        newMatchStat.setStatus(1);
        matchStatRepository.save(newMatchStat);

        return new ResponseEntity<>("Match stat added", HttpStatus.OK);
    }

    public ResponseEntity getMatchStats(Integer id) {
        MatchModel match = matchRepository.findById(id).get(0);
        List<MatchStats> matchStats = matchStatRepository.findByMatchId(match.getId());
        MatchDetails matchDetails = new MatchDetails(match, matchStats);
        return new ResponseEntity<>(matchDetails, HttpStatus.OK);

    }
    public void deleteMatchStat(Integer id) {
        MatchStats matchStat = matchStatRepository.findById(id).get();
        if (matchStat == null) {
            throw new RuntimeException("Match Stat not found");
        }
        matchStat.setStatus(2);
        matchStatRepository.save(matchStat);
    }
}
