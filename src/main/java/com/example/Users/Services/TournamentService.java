package com.example.Users.Services;

import com.example.Users.Mocks.*;
import com.example.Users.Models.*;
import com.example.Users.Repositories.*;
import com.example.Users.Types.Errors.Errors;
import com.example.Users.Types.Interfaces.AWSServiceInterface;
import com.example.Users.Types.Interfaces.ErrorsInterface;
import com.example.Users.Types.Interfaces.PayloadServiceInterface;
import com.example.Users.Types.Interfaces.ValidateBase64Interface;
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
        PayloadServiceInterface payloadS = mocking.isPresent() ? new PayloadServiceMock() : payloadService;
        TournamentRepository tournamentRepo = mocking.isPresent() ? new TournamentRespositoryMock() : tournamentRepository;

        JsonNode userPayload = payloadS.getPayload(auth);
        Tournament tournament = tournamentRepo.findById(req.getId()).get(0);
        if(tournament.getUser().getId() != userPayload.get("user_id").asInt()) {
            return new ResponseEntity<>("You can't perform this action", HttpStatus.BAD_REQUEST);
        }
        tournament.setName(req.getName());
        tournamentRepo.save(tournament);
        return new ResponseEntity<>("Tournament updated", HttpStatus.OK);
    }

    public ResponseEntity addNewTournament(AddNewTournament request, String authorization, Optional<Boolean> mocking) throws JsonProcessingException {
        TeamRepository teamRepo = mocking.isPresent() ? new TeamRepositoryMock() : teamRepository;
        PayloadServiceInterface payloadS = mocking.isPresent() ? new PayloadServiceMock() : payloadService;
        SportsRepository sportsRepo = mocking.isPresent() ? new SportsRepositoryMock() : sportsRepository;
        UserRepository userRepo = mocking.isPresent() ? new UserRepositoryMock() : userRepository;
        TournamentRepository tournamentRepo = mocking.isPresent() ? new TournamentRespositoryMock() : tournamentRepository;
        MatchRepository matchRepo = mocking.isPresent() ? new MatchRepositoryMock() : matchRepository;
        LeaguePositionsRepository leaguePosRepo = mocking.isPresent() ? new LeaguePositionsMock() : leaguePositionsRepository;
        ErrorsInterface errorsS = mocking.isPresent() ? new ErrorsMock() : errors;

        JsonNode userPayload = payloadS.getPayload(authorization);
        Integer teamsAmount = request.getTeamsIds().size();
        if(teamsAmount != request.getTeams()) {
            System.out.println("omg1");
            return errorsS.badRequest();
        }
        if(teamsAmount % 2 != 0) {
            System.out.println("omg2");
            return errorsS.badRequest();
        }
        ArrayList<TeamModel> teams = new ArrayList<>();
        request.getTeamsIds().forEach(el -> {
             teams.add(teamRepo.findById(el).get(0));
        });
        AtomicReference<Boolean> teamsTournamentSameSport = new AtomicReference<>(true);
        teams.forEach(team-> {
            if(team.getSport().getId() != request.getSport_id()) {
                teamsTournamentSameSport.set(false);
            }
        });
        if(!teamsTournamentSameSport.get()) {
            System.out.println("omg3");
            return errorsS.badRequest();
        }
        /* variables for key tournament*/
        Integer numberTeams = request.getTeams();
        Integer rounds = this.getRounds(numberTeams, request.getType());

        Tournament tournament = new Tournament();
        SportsModel sport = sportsRepo.findById(request.getSport_id()).get(0);
        tournament.setName(request.getName());
        tournament.setSport(sport);
        tournament.setTeams(request.getTeams());
        tournament.setRounds(rounds);
        tournament.setType(request.getType());
        UserModel user = userRepo.findById(Math.toIntExact(userPayload.get("user_id").asInt())).get(0);
        tournament.setUser(user);
        tournamentRepo.save(tournament);
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
            leaguePosRepo.save(result);
        });


        switch (request.getType()) {
            //Single keys tournament
            case 1:
               List<Integer> indexesOrder = this.RandomizeOrder(numberTeams);
               for (Integer j = 0; j < numberTeams; j = j + 2) {
                    /*TeamModel local = teams.get(indexesOrder.get(j));
                    TeamModel visitor = teams.get(indexesOrder.get(j + 1));*/

                    MatchModel match = new MatchModel();
                    match.setTournament(tournament);
                    match.setRound(rounds);
                    /*match.setLocal(local);
                    match.setVisitor(visitor);*/
                   matchRepo.save(match);
                    System.out.println("Round " + rounds + " match created");
                }

               Integer restantTeams = numberTeams;
               Integer currentRound = rounds;
                for(Integer k = rounds -1; k > 0; k--) {
                     currentRound--;
                    restantTeams /= 2;
                    Integer finalK = k;
                    for (Integer m = restantTeams; m > 0; m = m-2) {
                        MatchModel match = new MatchModel();
                        match.setTournament(tournament);
                        match.setRound(currentRound);
                        matchRepo.save(match);
                        System.out.println("Round " + currentRound + " match created");
                    }
                }
                break;
                //Back and forth tournament
            case 2:
                List<Integer> indexesOrderBF = this.RandomizeOrder(numberTeams);
                for (Integer j = 0; j < numberTeams; j = j + 2) {
                    /*TeamModel local = teams.get(indexesOrderBF.get(j));
                    TeamModel visitor = teams.get(indexesOrderBF.get(j + 1));*/
                    MatchModel match = new MatchModel();
                    match.setTournament(tournament);
                    match.setRound(rounds);
                    /*match.setLocal(local);
                    match.setVisitor(visitor);*/
                    matchRepo.save(match);
                    System.out.println("Round " + rounds + " 1st leg match created");
                    /*visitor = teams.get(indexesOrderBF.get(j));
                    local = teams.get(indexesOrderBF.get(j + 1));*/
                    MatchModel match2ndLeg = new MatchModel();
                    match2ndLeg.setTournament(tournament);
                    match2ndLeg.setRound(rounds);
                    /*match2ndLeg.setLocal(local);
                    match2ndLeg.setVisitor(visitor);*/
                    matchRepo.save(match2ndLeg);
                    System.out.println("Round " + rounds + " 2nd leg match created");
                }
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
                            matchRepo.save(match);
                            System.out.println("Final round created");
                        } else {
                            matchRepo.save(match);
                            System.out.println("Round " + currentRoundBF + " 1st leg match created");
                            matchRepo.save(match2ndLeg);
                            System.out.println("Round " + currentRoundBF + " 2nd match created");
                        }
                    }
                }

                break;
                //league
            case 3:
                List<MatchMade> matches = new ArrayList<>();
                teams.forEach((team) -> {
                    teams.forEach((rival) -> {
                        if(team.getId() != rival.getId()) {
                            AtomicReference<Boolean> repeated = new AtomicReference<>(false);
                            matches.forEach((createdMatch) -> {
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
                                matchRepo.save(match);

                                MatchMade matchMade = new MatchMade();
                                matchMade.setTeam1(team);
                                matchMade.setTeam2(rival);
                                matches.add(matchMade);
                            }
                        }
                    });
                });
                break;
            //league back and forth
            case 4:
                teams.forEach((team) -> {
                    teams.forEach((rival) -> {
                        if(team.getId() != rival.getId()) {
                            MatchModel match = new MatchModel();
                            match.setTournament(tournament);
                            match.setRound(0);
                            match.setLocal(team);
                            match.setVisitor(rival);
                            matchRepo.save(match);
                        }
                    });
                });
                break;
            default:
                break;
        }


        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    private List<Integer> RandomizeOrder (Integer numberTeams) {
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

    private Integer getRounds(Integer numberTeams, Integer type) {
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
