package com.example.Users.Services;

import com.example.Users.Models.*;
import com.example.Users.Types.Tournaments.AddNewTournament;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TournamentServiceTest {
    @Test
    public void testValidNumberTeams() {
        TournamentService tournamentService = new TournamentService();
        AddNewTournament request = new AddNewTournament();
        request.setTeams(8);
        ResponseEntity response = tournamentService.validNumberTeams(8,request);
        assertNull(response);
    }
    @Test
    public void testValidNumberTeams_DiscrepancyNumbers() {
        TournamentService tournamentService = new TournamentService();
        AddNewTournament request = new AddNewTournament();
        request.setTeams(6);
        ResponseEntity response = tournamentService.validNumberTeams(8,request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    public void testValidNumberTeams_NotEvenNumber() {
        TournamentService tournamentService = new TournamentService();
        AddNewTournament request = new AddNewTournament();
        request.setTeams(5);
        ResponseEntity response = tournamentService.validNumberTeams(5,request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    public void testValidSportsTeams() {
        TournamentService tournamentService = new TournamentService();
        AddNewTournament request = new AddNewTournament();
        request.setTeams(4);
        request.setTeamsIds(List.of(1,2,3,4));
        request.setSport_id(2);
        SportsModel sportsModel = new SportsModel();
        sportsModel.setId(2);

        TeamModel team = new TeamModel();
        team.setSport(sportsModel);
        ArrayList<TeamModel> teams = new ArrayList<TeamModel>(Arrays.asList(team, team));

        ResponseEntity response = tournamentService.validSportsTeams(teams, request);
        assertNull(response);
    }
    @Test
    public void testValidSportsTeams_DiscrepancyOfSports() {
        TournamentService tournamentService = new TournamentService();

        AddNewTournament request = new AddNewTournament();
        request.setTeams(4);
        request.setTeamsIds(List.of(1,2,3,4));
        request.setSport_id(32);

        SportsModel sportsModel = new SportsModel();
        sportsModel.setId(2);

        TeamModel team = new TeamModel();
        team.setSport(sportsModel);
        ArrayList<TeamModel> teams = new ArrayList<TeamModel>(Arrays.asList(team, team));

        ResponseEntity response = tournamentService.validSportsTeams(teams, request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    public void testCreateTournamentObj() {
        TournamentService tournamentService = new TournamentService();

        AddNewTournament request = new AddNewTournament();
        request.setName("tournament");
        request.setTeams(4);
        request.setTeamsIds(List.of(1,2,3,4));

        SportsModel sportsModel = new SportsModel();
        sportsModel.setId(32);

        UserModel user = new UserModel();

        TeamModel team = new TeamModel();
        team.setSport(sportsModel);
        Tournament response = tournamentService.createTournamentObj(request, 4, user, sportsModel);
        assertEquals("tournament", response.getName());
        assertEquals(4,response.getRounds());
        assertEquals(4, response.getRounds());
        assertEquals(32, response.getSport().getId());
    }

    @Test
    public void testSetLeaguePositionsNewTournament() {
        // Set up test data
        TournamentService tournamentService = new TournamentService();
        ArrayList<TeamModel> teams = new ArrayList<>();
        TeamModel team1 = new TeamModel();
        team1.setId(1);
        team1.setName("Team 1");
        teams.add(team1);
        Tournament tournament = new Tournament();
        tournament.setId(1);
        tournament.setName("Tournament 1");

        // Call the function
        List<LeaguePositions> leaguePositions = tournamentService.setLeaguePositionsNewTournament(teams, tournament);

        // Verify the result
        assertEquals(1, leaguePositions.size());
        assertEquals(team1, leaguePositions.get(0).getTeamId());
        assertEquals(0, leaguePositions.get(0).getPoints());
        assertEquals(0, leaguePositions.get(0).getL());
        assertEquals(0, leaguePositions.get(0).getGoals());
        assertEquals(0, leaguePositions.get(0).getD());
        assertEquals(0, leaguePositions.get(0).getW());
        assertEquals(0, leaguePositions.get(0).getGoalsAgainst());
        assertEquals(tournament, leaguePositions.get(0).getTournament());
    }

    @Test
    public void testRandomizeOrder() {
        TournamentService tournamentService = new TournamentService();
        int numberTeams = 5;
        List<Integer> result = tournamentService.RandomizeOrder(numberTeams);
        assertEquals(numberTeams, result.size());
    }

        @Test
        public void testCreateFirstRoundMatches() {
            TournamentService tournamentService = new TournamentService();
            // Create a test tournament with name "Test Tournament" and id 1
            Tournament testTournament = new Tournament();
            testTournament.setName("Test Tournament");
            testTournament.setId(1);

            // Call the createFirstRoundMatches() function with number of teams 4, test tournament and round 1
            List<MatchModel> matches = tournamentService.createFirstRoundMatches(4, testTournament, 1);

            // Verify that the size of the returned list is 2 (half the number of teams)
            assertEquals(2, matches.size());

            // Verify that the tournament and round of the first element in the list match the expected values
            assertEquals(testTournament.getName(), matches.get(0).getTournament().getName());
            assertEquals(testTournament.getId(), matches.get(0).getTournament().getId());
            assertEquals(1, matches.get(0).getRound());
        }
    @Test
    public void testCreateFirstRoundMatchesBF() {
        TournamentService tournamentService = new TournamentService();
        // Arrange
        int numberTeams = 4;
        int rounds = 1;
        Tournament tournament = new Tournament();
        tournament.setId(1);
        tournament.setName("Tournament 1");
        // Act
        List<MatchModel> matches = tournamentService.createFirstRoundMatchesBF(numberTeams, tournament, rounds);
        // Assert
        assertEquals(tournament.getId(), matches.get(0).getTournament().getId());
        assertEquals(tournament.getName(), matches.get(0).getTournament().getName());
        assertEquals(rounds, matches.get(0).getRound());
        assertEquals(tournament.getId(), matches.get(1).getTournament().getId());
        assertEquals(tournament.getName(), matches.get(1).getTournament().getName());
        assertEquals(rounds, matches.get(1).getRound());
    }

    @Test
    public void createOtherRoundsMatchesBFTest() {
        TournamentService tournamentService = new TournamentService();
        // Arrange
        int numberTeams = 8;
        Tournament tournament = new Tournament();
        tournament.setId(1);
        tournament.setName("World Cup 2022");
        int rounds = 4;

        // Act
        List<MatchModel> matches = tournamentService.createOtherRoundsMatchesBF(numberTeams, tournament, rounds);

        // Assert
        // Verify that the size of the returned list is equal to the number of matches expected for the given number of teams and rounds
        int expectedNumberOfMatches = (int) (Math.pow(2, rounds - 1) - 1);
        assertEquals(expectedNumberOfMatches, matches.size());

        // Verify that the tournament and round of the first element returned are correct
        assertEquals(tournament.getId(), matches.get(0).getTournament().getId());
        assertEquals(tournament.getName(), matches.get(0).getTournament().getName());
        assertEquals(rounds - 1, (int)matches.get(0).getRound());

        // Verify that the tournament and round of the second element returned are correct
        assertEquals(tournament.getId(), matches.get(1).getTournament().getId());
        assertEquals(tournament.getName(), matches.get(1).getTournament().getName());
        assertEquals(rounds - 1, (int)matches.get(1).getRound());
    }

    @Test
    public void testSetBFLeagueTournament() {
        TournamentService tournamentService = new TournamentService();
        // Create a list of teams
        ArrayList<TeamModel> teams = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            TeamModel team = new TeamModel();
            team.setId(i);
            team.setName("Team " + i);
            teams.add(team);
        }

        // Create a tournament
        Tournament tournament = new Tournament();
        tournament.setId(1);
        tournament.setName("BF League");
        tournament.setType(4);

        // Test the function
        List<MatchModel> matches = tournamentService.setBFLeagueTournament(teams, tournament);

        // Verify the results
        assertEquals(12, matches.size()); // 4 teams * 3 rival teams = 12 matches
        for (MatchModel match : matches) {
            assertEquals(tournament, match.getTournament());
            assertEquals(0, (int) match.getRound());
            assertNotNull(match.getLocal());
            assertNotNull(match.getVisitor());
            assertNotEquals(match.getLocal(), match.getVisitor());
        }
    }

    @Test
    public void testGetRounds_Type1() {
        TournamentService tournamentService = new TournamentService();
        // Test case for type 1 tournament
        assertEquals(1, tournamentService.getRounds(2, 1));
        assertEquals(2, tournamentService.getRounds(4, 1));
        assertEquals(3, tournamentService.getRounds(8, 1));
        assertEquals(4, tournamentService.getRounds(16, 1));
        assertEquals(5, tournamentService.getRounds(32, 1));
        assertEquals(6, tournamentService.getRounds(64, 1));
        assertEquals(0, tournamentService.getRounds(3, 1));
    }

    @Test
    public void testGetRounds_Type2() {
        TournamentService tournamentService = new TournamentService();
        // Test case for type 2 tournament (back and forth except final)
        assertEquals(1, tournamentService.getRounds(2, 2));
        assertEquals(2, tournamentService.getRounds(4, 2));
        assertEquals(3, tournamentService.getRounds(8, 2));
        assertEquals(4, tournamentService.getRounds(16, 2));
        assertEquals(5, tournamentService.getRounds(32, 2));
        assertEquals(6, tournamentService.getRounds(64, 2));
        assertEquals(0, tournamentService.getRounds(3, 2));
    }

    @Test
    public void testGetRoundsForLeagueTournament() {
        TournamentService tournamentService = new TournamentService();
        assertEquals(1, tournamentService.getRounds(2, 3));
        assertEquals(3, tournamentService.getRounds(4, 3));
        assertEquals(5, tournamentService.getRounds(6, 3));
        assertEquals(7, tournamentService.getRounds(8, 3));
        assertEquals(9, tournamentService.getRounds(10, 3));
        assertEquals(11, tournamentService.getRounds(12, 3));
        assertEquals(13, tournamentService.getRounds(14, 3));
        assertEquals(15, tournamentService.getRounds(16, 3));
        assertEquals(17, tournamentService.getRounds(18, 3));
        assertEquals(19, tournamentService.getRounds(20, 3));
        assertEquals(0, tournamentService.getRounds(21, 3));
    }

    @Test
    public void testGetRoundsForLeagueBackAndForth() {
        TournamentService tournamentService = new TournamentService();
        assertEquals(2, tournamentService.getRounds(2, 4));
        assertEquals(6, tournamentService.getRounds(4, 4));
        assertEquals(10, tournamentService.getRounds(6, 4));
        assertEquals(14, tournamentService.getRounds(8, 4));
        assertEquals(18, tournamentService.getRounds(10, 4));
        assertEquals(22, tournamentService.getRounds(12, 4));
        assertEquals(26, tournamentService.getRounds(14, 4));
        assertEquals(30, tournamentService.getRounds(16, 4));
        assertEquals(34, tournamentService.getRounds(18, 4));
        assertEquals(38, tournamentService.getRounds(20, 4));
        assertEquals(0, tournamentService.getRounds(21, 4));
    }




}