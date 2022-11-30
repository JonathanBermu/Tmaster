package com.example.Users.Services;

import com.example.Users.Models.LeaguePositions;
import com.example.Users.Models.MatchModel;
import com.example.Users.Models.TeamModel;
import com.example.Users.Models.Tournament;
import com.example.Users.Repositories.LeaguePositionsRepository;
import com.example.Users.Repositories.MatchRepository;
import com.example.Users.Repositories.TeamRepository;
import com.example.Users.Repositories.TournamentRepository;
import com.example.Users.Types.Match.MatchStatuses;
import com.example.Users.Types.Match.SetMatchResults;
import com.example.Users.Types.Match.SetMatchRound;
import com.example.Users.Types.Match.SetMatchTeams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Service
public class MatchService {
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private LeaguePositionsRepository leaguePositionsRepository;
    public ResponseEntity getMatchesTournament(Integer tournamentId) {
        List<MatchModel> matches =  matchRepository.findByTournamentIdOrderByRoundAsc(tournamentId);
        return new ResponseEntity(matches, HttpStatus.OK);
    }
    public ResponseEntity setMatchResults(SetMatchResults req){
        MatchModel match = matchRepository.findById(req.getId()).get(0);
        match.setLocalScore(req.getLocalScore());
        match.setVisitorScore(req.getVisitorScore());
        match.setState(MatchStatuses.FINISHED.getValue());
        matchRepository.save(match);
        return new ResponseEntity("ok", HttpStatus.OK);
    }

    public ResponseEntity setBatchRound(List<SetMatchRound> req) {
        List<Boolean> resArray = new ArrayList<>(List.of());
        req.forEach(el -> {
            ResponseEntity res = this.setMatchRound(el);
            if(res.getStatusCode() != HttpStatus.OK) {
                resArray.add(false);
            }
            resArray.add(true);
        });
        if(resArray.contains(false)) {
            return new ResponseEntity<>("Round set successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Error setting round", HttpStatus.CONFLICT);

    }
    public ResponseEntity setMatchRound(SetMatchRound req) {
        MatchModel match = matchRepository.findById(req.getId()).get(0);
        Tournament tournament = tournamentRepository.findById(match.getTournament().getId()).get(0);
        if(tournament.getRounds() < req.getRound()) {
            return new ResponseEntity<>("Cannot do this action", HttpStatus.BAD_REQUEST);
        }
        if(!
                (tournament.getCurrentRound() == null || tournament.getCurrentRound() == 0)
                        && tournament.getRounds() == req.getRound()
        ) {
            return new ResponseEntity<>("Cannot do this action", HttpStatus.BAD_REQUEST);
        }
        List<MatchModel> matchesInRound = matchRepository.findByTournamentIdAndRound(tournament.getId(), req.getRound());
        if(matchesInRound.size() > tournament.getTeams() / 2 ) {
            return new ResponseEntity<>("Cannot do this action", HttpStatus.BAD_REQUEST);
        }
        Boolean teamsPlayed = this.teamsPlayedCurrentRound(matchesInRound, match);
        if(teamsPlayed) {
            return new ResponseEntity<>("Cannot do this action", HttpStatus.BAD_REQUEST);
        }
        Boolean matchInRound = this.matchInRound(matchesInRound, match);
        //scheduled = 1; happened = 2
        if(matchInRound && match.getState() == MatchStatuses.FINISHED.getValue()) {
            return new ResponseEntity<>("Cannot do this action", HttpStatus.BAD_REQUEST);
        }
        match.setRound(req.getRound());
        match.setDate(req.getDate());
        match.setState(MatchStatuses.SCHEDULED.getValue());
        matchRepository.save(match);
        return new ResponseEntity<>("Match updated successfully", HttpStatus.OK);
    }
    private Boolean teamsPlayedCurrentRound(List<MatchModel> matches, MatchModel match) {
        Stream<MatchModel> matchesPlayedByTeams = matches.stream().filter(m ->
                (m.getLocal() == match.getLocal()) ||
                        (m.getVisitor() == match.getLocal()) ||
                        (m.getLocal() == match.getVisitor()) ||
                        (m.getVisitor() == match.getVisitor())
        );
        if (matchesPlayedByTeams.count() > 0) {
            return true;
        }
        return false;
    }

    private Boolean matchInRound(List<MatchModel> matches, MatchModel match) {
        Stream<MatchModel> matchesInRound =
                matches.stream()
                        .filter(m -> (match.getVisitor() == m.getVisitor() && match.getLocal() == m.getVisitor()));
        if(matchesInRound.count() > 0) return true;
        else return false;
    }
    public ResponseEntity setMatchResultsBulk(List<SetMatchResults> req){
        List<MatchModel> matches = new ArrayList<>();
        Tournament tournament = tournamentRepository.findById(matches.get(0).getTournament().getId()).get(0);
        AtomicReference<Boolean> discrepancyResults = new AtomicReference<>(false);
        req.forEach(m -> {
            MatchModel match = matchRepository.findById(m.getId()).get(0);
            match.setLocalScore(m.getLocalScore());
            match.setVisitorScore(m.getVisitorScore());
            match.setState(MatchStatuses.FINISHED.getValue());

            if(tournament.getType().equals(1) || tournament.getType().equals(2) &&
            m.getLocalScore().equals(m.getVisitorScore())){
                match.setLocalScorePens(m.getLocalScorePens());
                match.setVisitorScorePens(m.getVisitorScorePens());
            }

            matches.add(match);
            List<LeaguePositions> localResult = leaguePositionsRepository.findByTournamentIdAndTeamId(match.getTournament().getId(), match.getLocal());
            List<LeaguePositions> visitorResult = leaguePositionsRepository.findByTournamentIdAndTeamId(match.getTournament().getId(), match.getVisitor());
            LeaguePositions lr = localResult.get(0);
            LeaguePositions vr = visitorResult.get(0);
            Integer w, d, l, g, ga, points, w2, d2, l2, g2, ga2, points2;
            w = lr.getW();
            d = lr.getD();
            l = lr.getL();
            g = lr.getGoals();
            ga = lr.getGoalsAgainst();
            points = lr.getPoints();

            w2 = vr.getW();
            d2 = vr.getD();
            l2 = vr.getL();
            g2 = vr.getGoals();
            ga2 = vr.getGoalsAgainst();
            points2 = vr.getPoints();

            if(match.getLocalScore() > match.getVisitorScore()) {
                lr.setW(w + 1);
                vr.setL(l2 + 1);
                lr.setPoints(points + 3);
            } else if (match.getLocalScore() < match.getVisitorScore()) {
                vr.setW(w2+1);
                lr.setL(l+1);
                vr.setPoints(points2 + 3);
            } else {
                lr.setD(d+1);
                vr.setD(d2+1);
                vr.setPoints(points2 + 1);
                lr.setPoints(points2 + 1);
            }

            lr.setGoals(g + match.getLocalScore());
            vr.setGoals(g2 + match.getVisitorScore());

            vr.setGoalsAgainst(ga2 + match.getLocalScore());
            lr.setGoalsAgainst(ga + match.getVisitorScore());

            leaguePositionsRepository.save(lr);
            leaguePositionsRepository.save(vr);
        });
        if(discrepancyResults.get()) {
            return new ResponseEntity("Discrepancy in results", HttpStatus.CONFLICT);
        }
        matchRepository.saveAll(matches);
        return new ResponseEntity("ok", HttpStatus.OK);
    }

    public ResponseEntity setMatchTeams(List<SetMatchTeams> request) {
        AtomicReference<Boolean> missingBFMatch = new AtomicReference<>(false);
        AtomicReference<Boolean> discrepancyFound = new AtomicReference<>(false);
        List<MatchModel> matchesInRound = new ArrayList<>();
        request.stream().forEach(el-> {
            MatchModel match = matchRepository.findById(el.getMatchId()).get(0);
            TeamModel local = teamRepository.findById(el.getLocalTeamId()).get(0);
            TeamModel visitor = teamRepository.findById(el.getVisitorTeamId()).get(0);
            match.setVisitor(visitor);
            match.setLocal(local);
            matchesInRound.add(match);
        });
        Tournament tournament = matchesInRound.get(0).getTournament();
        Integer nMatches = this.getNMatches(matchesInRound.get(0).getRound(), tournament.getType());
        if(matchesInRound.size() < nMatches){
            System.out.println("1");
            return new ResponseEntity<>("Cannot do this action", HttpStatus.BAD_REQUEST);
        }
        Stream<MatchModel> matchesSameRound = matchesInRound.stream().filter(el -> el.getRound() == matchesInRound.get(0).getRound());
        Integer matchesSameRoundCount = Math.toIntExact(matchesSameRound.count());
        if(matchesSameRoundCount != matchesInRound.size()) {
            return new ResponseEntity<>("Cannot do this action", HttpStatus.BAD_REQUEST);
        }
        matchesInRound.stream().forEach(el-> {
            Stream<MatchModel> repeatedMatches =
                    matchesInRound.stream().filter(el2-> el.getId() != el2.getId())
                            .filter(el3-> el.getLocal().getId() == el3.getLocal().getId()
                                    || el.getLocal().getId() == el3.getVisitor().getId()
                            || el.getVisitor().getId() == el3.getLocal().getId()
                            || el.getVisitor().getId() == el3.getVisitor().getId());

            Stream<MatchModel> repeatedMatches2 =
                    matchesInRound.stream().filter(el2-> el.getId() != el2.getId())
                            .filter(el3-> el.getLocal().getId() == el3.getLocal().getId()
                                    || el.getLocal().getId() == el3.getVisitor().getId()
                                    || el.getVisitor().getId() == el3.getLocal().getId()
                                    || el.getVisitor().getId() == el3.getVisitor().getId());

            Integer streamSize = Math.toIntExact(repeatedMatches2.count());
            if(streamSize > 0 && tournament.getType() == 2) {
                MatchModel firstStream = repeatedMatches.findFirst().get();
                if(firstStream.getLocal().getId() != el.getVisitor().getId()
                || firstStream.getVisitor().getId() != el.getLocal().getId()) {
                    discrepancyFound.set(true);
                }
            }
            if(streamSize > 0 && tournament.getType() ==1) {
                discrepancyFound.set(true);
            }
            if(tournament.getType() == 2) {
                AtomicReference<Boolean> BFMatchFound = new AtomicReference<>(false);
                Stream<MatchModel> otherMatches = matchesInRound.stream().filter(el2-> el.getId() != el2.getId());
                otherMatches.map(el4 -> {
                   if(el.getVisitor().getId() == el4.getLocal().getId() && el.getLocal().getId() == el4.getVisitor().getId()) {
                       BFMatchFound.set(true);
                   }
                   if(!BFMatchFound.get() && el4.getRound() != 1) {
                        missingBFMatch.set(true);
                   }
                   return null;
                });
            }
        });
        if(discrepancyFound.get() || missingBFMatch.get()) {
            System.out.println("3");
            return new ResponseEntity<>("Cannot do this action", HttpStatus.BAD_REQUEST);
        }
        request.stream().forEach(req-> {
            TeamModel local = teamRepository.findById(req.getLocalTeamId()).get(0);
            TeamModel visitor = teamRepository.findById(req.getVisitorTeamId()).get(0);
            MatchModel match = matchRepository.findById(req.getMatchId()).get(0);
            match.setLocal(local);
            match.setLocal(visitor);
            matchRepository.save(match);
        });
        return new ResponseEntity<>("Teams set successfully", HttpStatus.OK);
    }


    private Integer getNMatches(Integer round,Integer tournamentType) {
        switch (tournamentType) {
            case 1:
                switch (round) {
                    case 1:
                        return 1;
                    case 2:
                        return 2;
                    case 3:
                        return 4;
                    case 4:
                        return 8;
                    case 5:
                        return 16;
                    case 6:
                        return 32;
                    case 7:
                        return 64;
                    default: return 0;
                }
            case 2:
                switch (round) {
                    case 1:
                        return 1;
                    case 2:
                        return 4;
                    case 3:
                        return 8;
                    case 4:
                        return 16;
                    case 5:
                        return 32;
                    case 6:
                        return 64;
                    case 7:
                        return 128;
                    default: return 0;
                }
            default: return 0;
        }
    }
}
