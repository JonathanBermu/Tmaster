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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
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
        MatchModel firstMatch = matchRepository.findById(req.get(0).getId()).get(0);
        Integer currentRound = req.get(0).getRound();
        Integer previousRound = currentRound + 1;

        ResponseEntity matchesHaveEqualRound = this.matchesHaveEqualRound(req, currentRound);
        if(matchesHaveEqualRound != null ) return matchesHaveEqualRound;

        List<MatchModel> previousRoundMatches = matchRepository.findByTournamentIdAndRound(firstMatch.getTournament().getId(), previousRound);
        ResponseEntity previousRoundSet = this. previousRoundSet(previousRoundMatches);

        if(previousRoundSet != null) return previousRoundSet;
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
    ResponseEntity matchesHaveEqualRound(List<SetMatchRound> req, Integer currentRound) {
        boolean allMatchesEqualRound = req.stream().allMatch(match1 -> match1.getRound() == currentRound);
        if (!allMatchesEqualRound) {
            return new ResponseEntity<>("You can only set the teams of one round at a time", HttpStatus.BAD_REQUEST);
        }
        return null;
    }
    ResponseEntity previousRoundSet(List<MatchModel> previousRoundMatches) {
        boolean hasLocalAndVisitor = previousRoundMatches.stream().allMatch(match1 -> match1.getLocal() != null && match1.getVisitor() != null);
        if (!hasLocalAndVisitor && previousRoundMatches.size() > 0) {
            return new ResponseEntity<>("You can only set this round until the previous round is set (only teams required)", HttpStatus.BAD_REQUEST);
        }
        return null;
    }
    public ResponseEntity setMatchRound(SetMatchRound req) {
        MatchModel match = matchRepository.findById(req.getId()).get(0);
        Tournament tournament = tournamentRepository.findById(match.getTournament().getId()).get(0);

        List<MatchModel> matchesInRound = matchRepository.findByTournamentIdAndRound(tournament.getId(), req.getRound());

        ResponseEntity validRound = this.validRound(tournament, req);
        if(validRound != null) return validRound;

        ResponseEntity rightNumberOfMatches = this.correctNumbersOfMatchesInRound(matchesInRound, tournament);
        if(rightNumberOfMatches == null) return rightNumberOfMatches;

        ResponseEntity teamsPlayed = this.teamsPlayedCurrentRound(matchesInRound, match);
        if(teamsPlayed == null) return teamsPlayed;

        ResponseEntity validMatchInRound = this.matchInRound(matchesInRound, match);
        if(validMatchInRound != null) return  validMatchInRound;

        match.setRound(req.getRound());
        match.setDate(req.getDate());
        match.setState(MatchStatuses.SCHEDULED.getValue());
        matchRepository.save(match);
        return new ResponseEntity<>("Match updated successfully", HttpStatus.OK);
    }

    ResponseEntity validRound(Tournament tournament, SetMatchRound req) {
        if(tournament.getRounds() < req.getRound()) {
            return new ResponseEntity<>("Cannot do this action", HttpStatus.BAD_REQUEST);
        }
        if(!
                (tournament.getCurrentRound() == null || tournament.getCurrentRound() == 0)
                && tournament.getRounds() == req.getRound()
        ) {
            return new ResponseEntity<>("Cannot do this action", HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    ResponseEntity correctNumbersOfMatchesInRound(List<MatchModel> matchesInRound, Tournament tournament) {
        if(matchesInRound.size() > tournament.getTeams() / 2 ) {
            return new ResponseEntity<>("Cannot do this action", HttpStatus.BAD_REQUEST);
        }
        return null;
    }
    ResponseEntity teamsPlayedCurrentRound(List<MatchModel> matches, MatchModel match) {
        Stream<MatchModel> matchesPlayedByTeams = matches.stream().filter(m ->
                (m.getLocal() == match.getLocal()) ||
                        (m.getVisitor() == match.getLocal()) ||
                        (m.getLocal() == match.getVisitor()) ||
                        (m.getVisitor() == match.getVisitor())
        );
        if (matchesPlayedByTeams.count() > 0) {
            return new ResponseEntity<>("Cannot do this action", HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    ResponseEntity matchInRound(List<MatchModel> matches, MatchModel match) {
        Boolean matchIsInRound = false;
        Stream<MatchModel> matchesInRound =
                matches.stream()
                        .filter(m -> (match.getVisitor() == m.getLocal() && match.getLocal() == m.getVisitor()));
        if(matchesInRound.count() > 0) matchIsInRound = true;
        //scheduled = 1; happened = 2
        if(matchIsInRound && match.getState() == MatchStatuses.FINISHED.getValue()) {
            return new ResponseEntity<>("Cannot do this action", HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    MatchModel updateMatchResults(SetMatchResults m, MatchModel match, Tournament tournament) {
        match.setLocalScore(m.getLocalScore());
        match.setVisitorScore(m.getVisitorScore());
        match.setState(MatchStatuses.FINISHED.getValue());

        if(tournament.getType().equals(1) || tournament.getType().equals(2) &&
                m.getLocalScore().equals(m.getVisitorScore())){
            match.setLocalScorePens(m.getLocalScorePens());
            match.setVisitorScorePens(m.getVisitorScorePens());
        }
        return match;
    }
    public ResponseEntity setMatchResultsBulk(List<SetMatchResults> req){
        MatchModel firstMatch = matchRepository.findById(req.get(0).getId()).get(0);
        Tournament tournament = tournamentRepository.findById(firstMatch.getTournament().getId()).get(0);
        List<MatchModel> matches = new ArrayList<>();
        req.forEach(m -> {
            MatchModel matchM = matchRepository.findById(m.getId()).get(0);
            MatchModel match = this.updateMatchResults(m, matchM, tournament);
            matches.add(match);

            List<LeaguePositions> localResult = leaguePositionsRepository.findByTournamentIdAndTeamId(match.getTournament().getId(), match.getLocal());
            List<LeaguePositions> visitorResult = leaguePositionsRepository.findByTournamentIdAndTeamId(match.getTournament().getId(), match.getVisitor());
            LeaguePositions lr = localResult.get(0);
            LeaguePositions vr = visitorResult.get(0);
            LeaguePositions[] leaguePositionsResults = this.calculateLeaguePositionsStats(lr, vr, match);

            leaguePositionsRepository.save(leaguePositionsResults[0]);
            leaguePositionsRepository.save(leaguePositionsResults[1]);
        });
        matchRepository.saveAll(matches);
        return new ResponseEntity("ok", HttpStatus.OK);
    }

    LeaguePositions[] calculateLeaguePositionsStats(LeaguePositions lr, LeaguePositions vr, MatchModel match) {
        //w=wins, d=draws, l=lose, g=goals, ga= goals against
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
            lr.setPoints(points + 1);
        }

        lr.setGoals(g + match.getLocalScore());
        vr.setGoals(g2 + match.getVisitorScore());

        vr.setGoalsAgainst(ga2 + match.getLocalScore());
        lr.setGoalsAgainst(ga + match.getVisitorScore());
        return new LeaguePositions[] {lr, vr};
    }
    public ResponseEntity setMatchTeams(List<SetMatchTeams> request) {

        List<MatchModel> matchesInRound = this.getMatchesInRound(request);
        Tournament tournament = matchesInRound.get(0).getTournament();
        Integer nMatches = this.getNMatches(matchesInRound.get(0).getRound(), tournament.getType());

        ResponseEntity validNumberMatches = this.rightNumberMatches(matchesInRound, nMatches);
        if(validNumberMatches != null) return validNumberMatches;


        ResponseEntity validMatchesInRound = this.validMatchesInRound(matchesInRound);
        if(validMatchesInRound != null) return validMatchesInRound;

        ResponseEntity discrepanciesFound = this.findMatchesDiscrepancies(tournament, matchesInRound);
        if(discrepanciesFound != null) return discrepanciesFound;

        List<MatchModel> matches = new ArrayList<>();
        request.stream().forEach(req-> {
            TeamModel local = teamRepository.findById(req.getLocalTeamId()).get(0);
            TeamModel visitor = teamRepository.findById(req.getVisitorTeamId()).get(0);
            MatchModel match = matchRepository.findById(req.getMatchId()).get(0);
            match.setLocal(local);
            match.setVisitor(visitor);
            matches.add(match);
        });
        matchRepository.saveAll(matches);

        return new ResponseEntity<>("Teams set successfully", HttpStatus.OK);
    }

    List<MatchModel> getMatchesInRound(List<SetMatchTeams> request) {
        List<MatchModel> matchesInRound = new ArrayList<>();
        request.stream().forEach(el-> {
            MatchModel match = matchRepository.findById(el.getMatchId()).get(0);
            TeamModel local = teamRepository.findById(el.getLocalTeamId()).get(0);
            TeamModel visitor = teamRepository.findById(el.getVisitorTeamId()).get(0);
            match.setVisitor(visitor);
            match.setLocal(local);
            matchesInRound.add(match);
        });
        return matchesInRound;
    }

    ResponseEntity rightNumberMatches(List<MatchModel> matchesInRound, Integer nMatches) {
        if(matchesInRound.size() < nMatches){
            return new ResponseEntity<>("Cannot do this action", HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    ResponseEntity validMatchesInRound(List<MatchModel> matchesInRound) {
        Stream<MatchModel> matchesSameRound = matchesInRound.stream().filter(el -> el.getRound() == matchesInRound.get(0).getRound());
        Integer matchesSameRoundCount = Math.toIntExact(matchesSameRound.count());
        if(matchesSameRoundCount != matchesInRound.size()) {
            return new ResponseEntity<>("Cannot do this action", HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    ResponseEntity findMatchesDiscrepancies(Tournament tournament, List<MatchModel> matchesInRound) {
        AtomicReference<Boolean> missingBFMatch = new AtomicReference<>(false);
        AtomicReference<Boolean> discrepancyFound = new AtomicReference<>(false);

        matchesInRound.stream().forEach(el-> {
            Stream<MatchModel> repeatedMatches = this.getRepeatedMatches(matchesInRound, el);
            Integer streamSize = this.getStreamSize(repeatedMatches);

            Boolean invalidBFMatch = this.validRepeatedMatchBackAndForth(streamSize, tournament, repeatedMatches, el);
            discrepancyFound.set(invalidBFMatch ? invalidBFMatch : discrepancyFound.get() );

            Boolean invalidTournamentKey = this.validTournamentKey(tournament, streamSize);
            discrepancyFound.set(invalidTournamentKey ? invalidTournamentKey : discrepancyFound.get());

            Boolean validTournamentKeyBF = this.validBFTournamentKey(tournament, matchesInRound, el);
            missingBFMatch.set(validTournamentKeyBF ? validTournamentKeyBF : missingBFMatch.get());

        });
        if(discrepancyFound.get()) {
            return new ResponseEntity<>("Cannot do this action", HttpStatus.BAD_REQUEST);
        }
        if(missingBFMatch.get()){
            return new ResponseEntity<>("Cannot do this action", HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    Boolean validBFTournamentKey(Tournament tournament, List<MatchModel> matchesInRound, MatchModel currentMatch) {
        AtomicReference<Boolean> missingBFMatch = new AtomicReference<>(false);
        if(tournament.getType() == 2) {
            AtomicReference<Boolean> BFMatchFound = new AtomicReference<>(false);
            Stream<MatchModel> otherMatches = matchesInRound.stream().filter(el2-> currentMatch.getId() != el2.getId());
            otherMatches.forEach(el4 -> {
                System.out.println(currentMatch.getVisitor().getId() == el4.getLocal().getId());
                System.out.println(currentMatch.getLocal().getId() == el4.getVisitor().getId());
                if(currentMatch.getVisitor().getId() == el4.getLocal().getId() && currentMatch.getLocal().getId() == el4.getVisitor().getId()) {
                    BFMatchFound.set(true);
                }
                System.out.println(BFMatchFound.get());
                if(!BFMatchFound.get() && el4.getRound() != 1) {
                    missingBFMatch.set(true);
                }
            });

        }
        return missingBFMatch.get();
    }
    Boolean validTournamentKey(Tournament tournament, Integer streamSize) {
        if(streamSize > 0 && tournament.getType() == 1) {
            return true;
        }
        return false;
    }
    Boolean validRepeatedMatchBackAndForth(Integer streamSize, Tournament tournament, Stream<MatchModel> repeatedMatches, MatchModel currentMatch) {
        if(streamSize > 0 && tournament.getType() == 2) {
            MatchModel firstStream = repeatedMatches.findFirst().get();
             if(firstStream.getLocal().getId() != currentMatch.getVisitor().getId()
                    || firstStream.getVisitor().getId() != currentMatch.getLocal().getId()) {
                return true;
            }
        }
        return false;
    }
    Stream<MatchModel> getRepeatedMatches(List<MatchModel> matchesInRound, MatchModel currentMatch) {
        Stream<MatchModel> repeatedMatches =
                matchesInRound.stream().filter(el2-> currentMatch.getId() != el2.getId())
                        .filter(el3-> currentMatch.getLocal().getId() == el3.getLocal().getId()
                                || currentMatch.getLocal().getId() == el3.getVisitor().getId()
                                || currentMatch.getVisitor().getId() == el3.getLocal().getId()
                                || currentMatch.getVisitor().getId() == el3.getVisitor().getId());
        return repeatedMatches;
    }
    Integer getStreamSize(Stream<MatchModel> stream) {
        Integer streamSize = Math.toIntExact(stream.count());
        return streamSize;
    }

    Integer getNMatches(Integer round,Integer tournamentType) {
        switch (tournamentType) {
            case 1:
                switch (round) {
                    case 1: return 1;
                    case 2: return 2;
                    case 3: return 4;
                    case 4: return 8;
                    case 5: return 16;
                    case 6: return 32;
                    case 7: return 64;
                    default: return 0;
                }
            case 2:
                switch (round) {
                    case 1: return 1;
                    case 2: return 4;
                    case 3: return 8;
                    case 4: return 16;
                    case 5: return 32;
                    case 6: return 64;
                    case 7: return 128;
                    default: return 0;
                }
            default: return 0;
        }
    }
}
