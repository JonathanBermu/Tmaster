package com.example.Users.Services;

import com.example.Users.Mocks.*;
import com.example.Users.Models.*;
import com.example.Users.Repositories.*;
import com.example.Users.Types.Errors.Errors;
import com.example.Users.Types.MatchMade;
import com.example.Users.Types.Tournaments.AddNewTournament;
import com.example.Users.Types.Tournaments.UpdateTournament;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TournamentService {
    @Autowired
    private Environment environment;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private Errors errors;
    @Autowired
    private SportsRepository sportsRepository;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private PayloadService payloadService;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LeaguePositionsRepository leaguePositionsRepository;

    public ResponseEntity getAllTournaments() {
        System.out.println(environment.getProperty("baeldung.presentation"));
        List<Tournament> tournaments = (List<Tournament>) tournamentRepository.findAllByOrderByIdDesc();
        if(tournaments.size() == 0) {
            return new ResponseEntity("Not enough tournaments", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(tournaments, HttpStatus.OK);
    }
    public ResponseEntity getTournament(Integer id) {
        Tournament tournament = tournamentRepository.findById(id).get(0);
        return new ResponseEntity<>(tournament, HttpStatus.OK);
    }
    public ResponseEntity getAllTournamentsUser(String auth) throws JsonProcessingException {
        JsonNode userPayload = payloadService.getPayload(auth);
        List<Tournament> tournaments = tournamentRepository.findByUserId(userPayload.get("user_id").asLong());
        return new ResponseEntity(tournaments, HttpStatus.OK);
    }

    public ResponseEntity updateTournament(UpdateTournament req, String auth, Optional<Boolean> mocking) throws JsonProcessingException {

        JsonNode userPayload = payloadService.getPayload(auth);
        Tournament tournament = tournamentRepository.findById(req.getId()).get(0);
        if(tournament.getUser().getId() != userPayload.get("user_id").asInt()) {
            return new ResponseEntity<>("You can't perform this action", HttpStatus.BAD_REQUEST);
        }
        tournament.setName(req.getName());
        tournamentRepository.save(tournament);
        return new ResponseEntity<>("Tournament updated", HttpStatus.OK);
    }

    public ResponseEntity addNewTournament(AddNewTournament request, String authorization, Optional<Boolean> mocking) throws JsonProcessingException {

        JsonNode userPayload = payloadService.getPayload(authorization);
        Integer teamsAmount = request.getTeamsIds().size();

        ResponseEntity validNumberTeams = this.validNumberTeams(teamsAmount, request);
        if(validNumberTeams != null) return validNumberTeams;

        ArrayList<TeamModel> teams = this.getTeamsObjects(request);

        ResponseEntity validSportsTeams = this.validSportsTeams(teams, request);
        if(validSportsTeams != null) return validSportsTeams;

        /* variables for key tournament*/
        Integer numberTeams = request.getTeams();
        Integer rounds = this.getRounds(numberTeams, request.getType());

        SportsModel sport = sportsRepository.findById(request.getSport_id()).get(0);
        UserModel user = userRepository.findById(Math.toIntExact(userPayload.get("user_id").asInt())).get(0);
        Tournament tournament = this.createTournamentObj(request, rounds, user, sport);
        tournamentRepository.save(tournament);

        List<LeaguePositions> leaguePos = this.setLeaguePositionsNewTournament(teams, tournament);
        leaguePositionsRepository.saveAll(leaguePos);


        switch (request.getType()) {
            //Single keys tournament
            case 1:
                List<MatchModel> matches = this.setSingleKeyTournaments(numberTeams, tournament, rounds);
                matchRepository.saveAll(matches);
                break;
                //Back and forth tournament
            case 2:
                List<MatchModel> matches2 = this.setBFKeyTournament(numberTeams, tournament, rounds);
                matchRepository.saveAll(matches2);
                break;
                //league
            case 3:
                List<MatchModel> matches3 = this.setLeagueTournament(teams, tournament);
                matchRepository.saveAll(matches3);
                break;
            //league back and forth
            case 4:
                List<MatchModel> matches4 = this.setBFLeagueTournament(teams, tournament);
                matchRepository.saveAll(matches4);
                break;
            default:
                break;
        }


        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    ResponseEntity validNumberTeams(Integer teamsAmount, AddNewTournament request) {
        System.out.println(teamsAmount + "---" + request.getTeams());
        if(teamsAmount != request.getTeams()) {
            return new ResponseEntity<>("Invalid number of teams", HttpStatus.BAD_REQUEST);
        }
        if(teamsAmount % 2 != 0) {
            return new ResponseEntity<>("Invalid number of teams, odd number of teams", HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    ArrayList<TeamModel> getTeamsObjects(AddNewTournament request) {
        ArrayList<TeamModel> teams = new ArrayList<>();
        request.getTeamsIds().forEach(el -> {
            teams.add(teamRepository.findById(el).get(0));
        });
        return teams;
    }

    ResponseEntity validSportsTeams(ArrayList<TeamModel> teams, AddNewTournament request) {
        AtomicReference<Boolean> teamsTournamentSameSport = new AtomicReference<>(true);
        teams.forEach(team-> {
            if(team.getSport().getId() != request.getSport_id()) {
                teamsTournamentSameSport.set(false);
            }
        });
        if(!teamsTournamentSameSport.get()) {
            return new ResponseEntity<>("Invalid sports in teams", HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    Tournament createTournamentObj(AddNewTournament request, Integer rounds, UserModel user, SportsModel sport) {
        Tournament tournament = new Tournament();
        tournament.setName(request.getName());
        tournament.setSport(sport);
        tournament.setTeams(request.getTeams());
        tournament.setRounds(rounds);
        tournament.setType(request.getType());
        tournament.setUser(user);
        return tournament;
    }

    List<LeaguePositions> setLeaguePositionsNewTournament(ArrayList<TeamModel> teams, Tournament tournament) {
        List<LeaguePositions> leaguePositions = new ArrayList<>();
        teams.forEach(el -> {
            LeaguePositions result = new LeaguePositions();
            result.setTeamId(el);
            result.setPoints(0);
            result.setL(0);
            result.setGoals(0);
            result.setD(0);
            result.setW(0);
            result.setGoalsAgainst(0);
            result.setTournament(tournament);
            leaguePositions.add(result);
        });
        return leaguePositions;
    }
    List<Integer> RandomizeOrder (Integer numberTeams) {
        List<Integer> indexesOrder = new ArrayList<Integer>();
        Random random = new Random();
        for (Integer i = 0; i < numberTeams; i++) {
            Integer newRandom = random.nextInt(numberTeams);
            if(indexesOrder.contains(newRandom)) {
                i--;
            } else {
                indexesOrder.add(newRandom);
            }
        }
        return indexesOrder;
    }

    public List<MatchModel> setSingleKeyTournaments(int numberTeams, Tournament tournament, int rounds) {
        List<Integer> indexesOrder = this.RandomizeOrder(numberTeams);
        List<MatchModel> matches = new ArrayList<>();
        List<MatchModel> firstRoundMatches = this.createFirstRoundMatches(numberTeams, tournament, rounds);
        List<MatchModel> restRoundsMatches = this.createOtherRoundsMatches(numberTeams, tournament, rounds);
        matches.addAll(firstRoundMatches);
        matches.addAll(restRoundsMatches);
        return matches;
    }

    List<MatchModel> createFirstRoundMatches(int numberTeams, Tournament tournament, int rounds) {
        List<MatchModel> matches = new ArrayList<>();
        for (Integer j = 0; j < numberTeams; j = j + 2) {
            MatchModel match = new MatchModel();
            match.setTournament(tournament);
            match.setRound(rounds);
            matches.add(match);
        }
        return matches;
    }

    List<MatchModel> createOtherRoundsMatches(int numberTeams, Tournament tournament, int rounds) {
        List<MatchModel> matches = new ArrayList<>();
        Integer restantTeams = numberTeams;
        Integer currentRound = rounds;
        for (Integer k = rounds - 1; k > 0; k--) {
            currentRound--;
            restantTeams /= 2;
            for (Integer m = restantTeams; m > 0; m = m - 2) {
                MatchModel match = new MatchModel();
                match.setTournament(tournament);
                match.setRound(currentRound);
                matches.add(match);
            }
        }
        return matches;
    }



    public List<MatchModel> setBFKeyTournament(int numberTeams, Tournament tournament, int rounds) {
        List<MatchModel> matches = new ArrayList<>();
        List<MatchModel> firstRoundMatches = this.createFirstRoundMatchesBF(numberTeams, tournament, rounds);
        List<MatchModel> restRoundsMatches = this.createOtherRoundsMatchesBF(numberTeams, tournament, rounds);
        matches.addAll(firstRoundMatches);
        matches.addAll(restRoundsMatches);
        return matches;
    }

    List<MatchModel> createFirstRoundMatchesBF(int numberTeams, Tournament tournament, int rounds) {
        List<MatchModel> matches = new ArrayList<>();
        for (Integer j = 0; j < numberTeams; j = j + 2) {
            MatchModel match = new MatchModel();
            match.setTournament(tournament);
            match.setRound(rounds);
            matches.add(match);
            MatchModel match2ndLeg = new MatchModel();
            match2ndLeg.setTournament(tournament);
            match2ndLeg.setRound(rounds);
            matches.add(match2ndLeg);
        }
        return matches;
    }

    List<MatchModel> createOtherRoundsMatchesBF(int numberTeams, Tournament tournament, int rounds) {
        List<MatchModel> matches = new ArrayList<>();
        Integer restantTeamsBF = numberTeams;
        Integer currentRoundBF = rounds;
        for(Integer k = rounds -1; k > 0; k--) {
            currentRoundBF--;
            restantTeamsBF /= 2;
            Integer finalK = k;
            for (Integer m = restantTeamsBF; m > 0; m = m-2) {
                MatchModel match = new MatchModel();
                match.setTournament(tournament);
                match.setRound(currentRoundBF);
                MatchModel match2ndLeg = new MatchModel();
                match2ndLeg.setTournament(tournament);
                match2ndLeg.setRound(currentRoundBF);
                if(currentRoundBF == 1 ) {
                    matches.add(match);
                } else {
                    matches.add(match);
                    matches.add(match2ndLeg);
                }
            }
        }
        return matches;
    }



    public List<MatchModel> setLeagueTournament(ArrayList<TeamModel> teams, Tournament tournament) {
        List<MatchMade> matchesMade = new ArrayList<>();
        List<MatchModel> matches = new ArrayList<>();
        teams.forEach((team) -> {
            teams.forEach((rival) -> {
                if(team.getId() != rival.getId()) {
                    AtomicReference<Boolean> repeated = new AtomicReference<>(false);
                    matchesMade.forEach((createdMatch) -> {
                        if(createdMatch.getTeam1().getId() == rival.getId() && createdMatch.getTeam2().getId() == team.getId()) {
                            repeated.set(true);
                        }
                    });
                    if(!repeated.get()) {
                        MatchModel match = new MatchModel();
                        match.setTournament(tournament);
                        match.setRound(0);
                        match.setLocal(team);
                        match.setVisitor(rival);
                        matches.add(match);

                        MatchMade matchMade = new MatchMade();
                        matchMade.setTeam1(team);
                        matchMade.setTeam2(rival);
                        matchesMade.add(matchMade);
                    }
                }
            });
        });
        return matches;
    }


    public List<MatchModel> setBFLeagueTournament(ArrayList<TeamModel> teams, Tournament tournament) {
        List<MatchModel> matches = new ArrayList<>();
        teams.forEach((team) -> {
            teams.forEach((rival) -> {
                if(team.getId() != rival.getId()) {
                    MatchModel match = new MatchModel();
                    match.setTournament(tournament);
                    match.setRound(0);
                    match.setLocal(team);
                    match.setVisitor(rival);
                    matches.add(match);
                }
            });
        });
        return matches;
    }


    Integer getRounds(Integer numberTeams, Integer type) {
        switch (type) {
            case 1:
                //keys tournament
                switch (numberTeams) {
                    case 2: return 1;
                    case 4: return 2;
                    case 8: return 3;
                    case 16: return 4;
                    case 32: return 5;
                    case 64: return 6;
                    default: return 0;
                }
                case 2:
                    //keys back and forth (except the final)
                switch (numberTeams) {
                    case 2: return 1;
                    case 4: return 2;
                    case 8: return 3;
                    case 16: return 4;
                    case 32: return 5;
                    case 64: return 6;
                    default: return 0;
                }
            case 3:
                //league tournament
                switch (numberTeams) {
                    case 2: return 1;
                    case 4: return 3;
                    case 6: return 5;
                    case 8: return 7;
                    case 10: return 9;
                    case 12: return 11;
                    case 14: return 13;
                    case 16: return 15;
                    case 18: return 17;
                    case 20: return 19;
                    default: return 0;
                }
            case 4:
                //league back and fourth
                switch (numberTeams) {
                    case 2: return 2;
                    case 4: return 6;
                    case 6: return 10;
                    case 8: return 14;
                    case 10: return 18;
                    case 12: return 22;
                    case 14: return 26;
                    case 16: return 30;
                    case 18: return 34;
                    case 20: return 38;
                    default: return 0;
                }
                default: return 0;
        }
    }
}
