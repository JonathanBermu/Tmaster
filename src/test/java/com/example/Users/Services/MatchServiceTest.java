package com.example.Users.Services;

import com.amazonaws.services.xray.model.Http;
import com.example.Users.Models.LeaguePositions;
import com.example.Users.Models.MatchModel;
import com.example.Users.Models.TeamModel;
import com.example.Users.Models.Tournament;
import com.example.Users.Types.Match.MatchStatuses;
import com.example.Users.Types.Match.SetMatchResults;
import com.example.Users.Types.Match.SetMatchRound;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MatchServiceTest {

    @Test
    public void testMatchesHaveEqualRound_DifferentRounds() {
        MatchService matchService = new MatchService();
        List<SetMatchRound> setMatchRounds = new ArrayList<>();
        SetMatchRound matchRound1 = new SetMatchRound();
        matchRound1.setRound(1);
        setMatchRounds.add(matchRound1);
        SetMatchRound matchRound2 = new SetMatchRound();
        matchRound2.setRound(2);
        setMatchRounds.add(matchRound2);

        ResponseEntity response = matchService.matchesHaveEqualRound(setMatchRounds, 1);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testMatchesHaveEqualRound_SameRound() {
        MatchService matchService = new MatchService();
        List<SetMatchRound> setMatchRounds = new ArrayList<>();
        SetMatchRound matchRound1 = new SetMatchRound();
        matchRound1.setRound(1);
        setMatchRounds.add(matchRound1);
        SetMatchRound matchRound2 = new SetMatchRound();
        matchRound2.setRound(1);
        setMatchRounds.add(matchRound2);

        ResponseEntity response = matchService.matchesHaveEqualRound(setMatchRounds, 1);

        assertNull(response);
    }

    @Test
    public void testPreviousRoundSet_NullLocalAndVisitor() {
        MatchService matchService = new MatchService();
        List<MatchModel> matches = new ArrayList<>();
        MatchModel match = new MatchModel();
        match.setLocal(null);
        match.setVisitor(null);
        matches.add(match);

        ResponseEntity response = matchService.previousRoundSet(matches);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testPreviousRoundSet_NonNullLocalAndVisitor() {
        MatchService matchService = new MatchService();
        List<MatchModel> matches = new ArrayList<>();
        MatchModel match = new MatchModel();
        match.setLocal(new TeamModel());
        match.setVisitor(new TeamModel());
        matches.add(match);

        ResponseEntity response = matchService.previousRoundSet(matches);

        assertNull(response);
    }

    @Test
    public void testValidRound_TooManyRounds() {
        MatchService matchService = new MatchService();
        Tournament tournament = new Tournament();
        tournament.setRounds(1);
        SetMatchRound req = new SetMatchRound();
        req.setRound(2);

        ResponseEntity response = matchService.validRound(tournament, req);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testValidRound_CurrentRoundSet() {
        MatchService matchService = new MatchService();
        Tournament tournament = new Tournament();
        tournament.setRounds(2);
        tournament.setCurrentRound(1);
        SetMatchRound req = new SetMatchRound();
        req.setRound(2);

        ResponseEntity response = matchService.validRound(tournament, req);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testValidRound_ValidRound() {
        MatchService matchService = new MatchService();
        Tournament tournament = new Tournament();
        tournament.setRounds(2);
        tournament.setCurrentRound(0);
        SetMatchRound req = new SetMatchRound();
        req.setRound(1);

        ResponseEntity response = matchService.validRound(tournament, req);

        assertNull(response);
    }

    @Test
    public void testCorrectNumbersOfMatchesInRound_TooManyMatches() {
        MatchService matchService = new MatchService();
        Tournament tournament = new Tournament();
        tournament.setTeams(6);
        List<MatchModel> matches = new ArrayList<>();
        matches.add(new MatchModel());
        matches.add(new MatchModel());
        matches.add(new MatchModel());
        matches.add(new MatchModel());
        matches.add(new MatchModel());
        matches.add(new MatchModel());


        ResponseEntity response = matchService.correctNumbersOfMatchesInRound(matches, tournament);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCorrectNumbersOfMatchesInRound_CorrectNumberOfMatches() {
        MatchService matchService = new MatchService();
        Tournament tournament = new Tournament();
        tournament.setTeams(6);
        List<MatchModel> matches = new ArrayList<>();
        matches.add(new MatchModel());
        matches.add(new MatchModel());

        ResponseEntity response = matchService.correctNumbersOfMatchesInRound(matches, tournament);

        assertNull(response);
    }

    @Test
    public void testTeamsPlayedCurrentRound_TeamsPlayed() {
        MatchService matchService = new MatchService();
        List<MatchModel> matches = new ArrayList<>();
        MatchModel match1 = new MatchModel();
        match1.setLocal(new TeamModel());
        match1.setVisitor(new TeamModel());
        matches.add(match1);

        MatchModel match2 = new MatchModel();
        match2.setLocal(match1.getLocal());
        match2.setVisitor(match1.getVisitor());

        ResponseEntity response = matchService.teamsPlayedCurrentRound(matches, match2);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    public void testTeamsPlayedCurrentRound_TeamsNotPlayed() {
        MatchService matchService = new MatchService();
        List<MatchModel> matches = new ArrayList<>();
        MatchModel match1 = new MatchModel();
        match1.setLocal(new TeamModel());
        match1.setVisitor(new TeamModel());
        matches.add(match1);

        MatchModel match2 = new MatchModel();
        match2.setLocal(new TeamModel());
        match2.setVisitor(new TeamModel());

        ResponseEntity response = matchService.teamsPlayedCurrentRound(matches, match2);

        assertNull(response);
    }
    @Test
    public void testMatchInRound_MatchFinished() {
        MatchService matchService = new MatchService();
        List<MatchModel> matches = new ArrayList<>();
        TeamModel t1 = new TeamModel();
        TeamModel t2 = new TeamModel();
        TeamModel t3 = new TeamModel();
        TeamModel t4 = new TeamModel();
        t1.setId(1);
        t2.setId(2);

        MatchModel match1 = new MatchModel();
        match1.setLocal(t1);
        match1.setVisitor(t2);
        match1.setState(MatchStatuses.FINISHED.getValue());
        matches.add(match1);

        MatchModel match2 = new MatchModel();
        match2.setLocal(t2);
        match2.setVisitor(t1);
        match2.setState(MatchStatuses.FINISHED.getValue());
        matches.add(match2);

        Boolean matchIsInRound = false;
        Stream<MatchModel> matchesInRound =
                matches.stream()
                        .filter(m -> (match2.getVisitor() == m.getLocal() && match2.getLocal() == m.getVisitor()));
        if(matchesInRound.count() > 0) matchIsInRound = true;
        ResponseEntity response = matchService.matchInRound(matches, match2);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    public void testMatchInRound_MatchNotFinished() {
        MatchService matchService = new MatchService();
        List<MatchModel> matches = new ArrayList<>();
        MatchModel match1 = new MatchModel();
        match1.setLocal(new TeamModel());
        match1.setVisitor(new TeamModel());
        match1.setState(MatchStatuses.SCHEDULED.getValue());
        matches.add(match1);

        MatchModel match2 = new MatchModel();
        match2.setLocal(match1.getLocal());
        match2.setVisitor(match1.getVisitor());

        ResponseEntity response = matchService.matchInRound(matches, match2);

        assertNull(response);
    }

    @Test
    public void testUpdateMatchResults() {
        MatchService matchService = new MatchService();
        SetMatchResults setMatchResults = new SetMatchResults();
        setMatchResults.setLocalScore(3);
        setMatchResults.setVisitorScore(2);

        MatchModel match = new MatchModel();
        match.setLocalScore(0);
        match.setVisitorScore(0);
        match.setState(MatchStatuses.SCHEDULED.getValue());

        Tournament tournament = new Tournament();
        tournament.setType(1);
        match = matchService.updateMatchResults(setMatchResults, match, tournament);

        assertEquals(3, match.getLocalScore());
        assertEquals(2, match.getVisitorScore());
        assertEquals(MatchStatuses.FINISHED.getValue(), match.getState());
    }

    @Test
    public void testUpdateMatchResults_PenaltyShootout() {
        MatchService matchService = new MatchService();
        SetMatchResults setMatchResults = new SetMatchResults();
        setMatchResults.setLocalScore(3);
        setMatchResults.setVisitorScore(3);
        setMatchResults.setLocalScorePens(5);
        setMatchResults.setVisitorScorePens(4);

        MatchModel match = new MatchModel();
        match.setLocalScore(0);
        match.setVisitorScore(0);
        match.setLocalScorePens(0);
        match.setVisitorScorePens(0);
        match.setState(MatchStatuses.SCHEDULED.getValue());

        Tournament tournament = new Tournament();
        tournament.setType(1);
        match = matchService.updateMatchResults(setMatchResults, match, tournament);

        assertEquals(3, match.getLocalScore());
        assertEquals(3, match.getVisitorScore());
        assertEquals(MatchStatuses.FINISHED.getValue(), match.getState());
        assertEquals(5, match.getLocalScorePens());
        assertEquals(4, match.getVisitorScorePens());
    }

    @Test
    public void testCalculateLeaguePositionsStatsLocalWins() {
        MatchService matchService = new MatchService();
        // Create a sample match
        MatchModel match = new MatchModel();
        match.setLocalScore(3);
        match.setVisitorScore(1);

        // Create sample league positions for local and visitor teams
        LeaguePositions lr = new LeaguePositions();
        lr.setW(1);
        lr.setD(1);
        lr.setL(1);
        lr.setGoals(5);
        lr.setGoalsAgainst(3);
        lr.setPoints(4);

        LeaguePositions vr = new LeaguePositions();
        vr.setW(2);
        vr.setD(1);
        vr.setL(1);
        vr.setGoals(7);
        vr.setGoalsAgainst(4);
        vr.setPoints(7);

        // Call the function
        LeaguePositions[] updatedPositions = matchService.calculateLeaguePositionsStats(lr, vr, match);

        // Verify that the local team's wins are incremented
        assertEquals(2, updatedPositions[0].getW());
        // Verify that the visitor team's wins are incremented
        assertEquals(2, updatedPositions[0].getW());
        // Verify that the local team's losses are incremented
        assertEquals(2, updatedPositions[1].getL());
        // Verify that the visitor team's losses are incremented
        assertEquals(2, updatedPositions[1].getL());
        // Verify that the local team's points are incremented
        assertEquals(7, updatedPositions[0].getPoints());
        // Verify that the visitor team's points are not incremented
        assertEquals(7, updatedPositions[1].getPoints());
        // Verify that the local team's goals are incremented
        assertEquals(8, updatedPositions[0].getGoals());
        // Verify that the visitor team's goals are incremented
        assertEquals(8, updatedPositions[1].getGoals());
        // Verify that the visitor team's goals against are incremented
        assertEquals(7, updatedPositions[1].getGoalsAgainst());
        // Verify that the local team's goals against are incremented
        assertEquals(4, updatedPositions[0].getGoalsAgainst());
    }

    @Test
    public void testCalculateLeaguePositionsStatsVisitorWins() {
        MatchService matchService = new MatchService();
        // Create a sample match
        MatchModel match = new MatchModel();
        match.setLocalScore(1);
        match.setVisitorScore(3);

        // Create sample league positions for local and visitor teams
        LeaguePositions lr = new LeaguePositions();
        lr.setW(1);
        lr.setD(1);
        lr.setL(1);
        lr.setGoals(5);
        lr.setGoalsAgainst(3);
        lr.setPoints(4);

        LeaguePositions vr = new LeaguePositions();
        vr.setW(2);
        vr.setD(1);
        vr.setL(1);
        vr.setGoals(7);
        vr.setGoalsAgainst(4);
        vr.setPoints(7);

        // Call the function
        LeaguePositions[] updatedPositions = matchService.calculateLeaguePositionsStats(lr, vr, match);

        // Verify that the local team's wins are not incremented
        assertEquals(1, updatedPositions[0].getW());
        // Verify that the visitor team's wins are incremented
        assertEquals(3, updatedPositions[1].getW());
        // Verify that the local team's losses are incremented
        assertEquals(2, updatedPositions[0].getL());
        // Verify that the visitor team's losses are not incremented
        assertEquals(1, updatedPositions[1].getL());
        // Verify that the local team's points are not incremented
        assertEquals(4, updatedPositions[0].getPoints());
        // Verify that the visitor team's points are incremented
        assertEquals(10, updatedPositions[1].getPoints());
        // Verify that the local team's goals are incremented
        assertEquals(6, updatedPositions[0].getGoals());
        // Verify that the visitor team's goals are incremented
        assertEquals(10, updatedPositions[1].getGoals());
        // Verify that the visitor team's goals against are incremented
        assertEquals(5, updatedPositions[1].getGoalsAgainst());
        // Verify that the local team's goals against are incremented
        assertEquals(6, updatedPositions[0].getGoalsAgainst());
    }

    @Test
    public void testCalculateLeaguePositionsStatsTiedGame() {
        MatchService matchService = new MatchService();
        // Create a sample match
        MatchModel match = new MatchModel();
        match.setLocalScore(2);
        match.setVisitorScore(2);

        // Create sample league positions for local and visitor teams
        LeaguePositions lr = new LeaguePositions();
        lr.setW(1);
        lr.setD(1);
        lr.setL(1);
        lr.setGoals(5);
        lr.setGoalsAgainst(3);
        lr.setPoints(4);

        LeaguePositions vr = new LeaguePositions();
        vr.setW(2);
        vr.setD(1);
        vr.setL(1);
        vr.setGoals(7);
        vr.setGoalsAgainst(4);
        vr.setPoints(7);

        // Call the function
        LeaguePositions[] updatedPositions = matchService.calculateLeaguePositionsStats(lr, vr, match);

        // Verify that the local team's wins are not incremented
        assertEquals(1, updatedPositions[0].getW());
        // Verify that the visitor team's wins are incremented
        assertEquals(2, updatedPositions[1].getW());
        // Verify that the local team's draws are incremented
        assertEquals(2, updatedPositions[0].getD());
        // Verify that the visitor team's draws are incremented
        assertEquals(2, updatedPositions[1].getD());
        // Verify that the local team's losses are not incremented
        assertEquals(1, updatedPositions[0].getL());
        // Verify that the visitor team's losses are not incremented
        assertEquals(1, updatedPositions[1].getL());
        // Verify that the local team's points are not incremented
        assertEquals(5, updatedPositions[0].getPoints());
        // Verify that the visitor team's points are incremented
        assertEquals(8 , updatedPositions[1].getPoints());
        // Verify that the local team's goals are incremented
        assertEquals(7, updatedPositions[0].getGoals());
        // Verify that the visitor team's goals are incremented
        assertEquals(9, updatedPositions[1].getGoals());
        // Verify that the visitor team's goals against are incremented
        assertEquals(6, updatedPositions[1].getGoalsAgainst());
        // Verify that the local team's goals against are incremented
        assertEquals(5, updatedPositions[0].getGoalsAgainst());
    }
    @Test
    public void rightNumberMatches() {
        MatchService matchService = new MatchService();
        List<MatchModel> matches = List.of(new MatchModel(), new MatchModel(), new MatchModel(), new MatchModel());
        ResponseEntity response = matchService.rightNumberMatches(matches, 4);
        assertNull(response);
    }
    @Test
    public void rightNumberMatches_Fail() {
        MatchService matchService = new MatchService();
        List<MatchModel> matches = List.of(new MatchModel(), new MatchModel(), new MatchModel(), new MatchModel());
        ResponseEntity response = matchService.rightNumberMatches(matches, 8);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testValidMatchesInRound_AllSameRound() {
        MatchService matchService = new MatchService();
        List<MatchModel> matchesInRound = new ArrayList<>();
        MatchModel match = new MatchModel();
        match.setLocal(new TeamModel());
        match.setVisitor(new TeamModel());
        match.setRound(3);
        matchesInRound.add(match);
        matchesInRound.add(match);
        matchesInRound.add(match);
        matchesInRound.add(match);
        ResponseEntity response = matchService.validMatchesInRound(matchesInRound);
        assertNull(response);
    }
    @Test
    public void testValidMatchesInRound_NotAllSameRound() {
        MatchService matchService = new MatchService();
        List<MatchModel> matchesInRound = new ArrayList<>();
        MatchModel match = new MatchModel();
        match.setLocal(new TeamModel());
        match.setVisitor(new TeamModel());
        match.setRound(3);
        matchesInRound.add(match);
        matchesInRound.add(match);
        matchesInRound.add(match);

        MatchModel match2 = new MatchModel();
        match2.setLocal(new TeamModel());
        match2.setVisitor(new TeamModel());
        match2.setRound(5);
        matchesInRound.add(match2);
        ResponseEntity response = matchService.validMatchesInRound(matchesInRound);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testFindMatchesDiscrepancies_DiscrepancyFound() {
    }

    @Test
    public void testValidBFTournamentKey_Valid() {
        MatchService matchService = new MatchService();
        Tournament tournament = new Tournament();
        tournament.setType(2);

        List<MatchModel> matchesInRound = new ArrayList<>();

        // create a match
        TeamModel team = new TeamModel();
        team.setId(1);
        TeamModel team2 = new TeamModel();
        team2.setId(2);


        MatchModel currentMatch = new MatchModel();
        currentMatch.setId(1);
        currentMatch.setRound(2);
        currentMatch.setLocal(team);
        currentMatch.setVisitor(team2);
        matchesInRound.add(currentMatch);

        MatchModel currentMatch2 = new MatchModel();
        currentMatch2.setId(2);
        currentMatch2.setRound(2);
        currentMatch2.setLocal(team2);
        currentMatch2.setVisitor(team);
        matchesInRound.add(currentMatch2);

        // no back and forth match
        System.out.println(currentMatch.getId());
        System.out.println("im fuming");
        Boolean result = matchService.validBFTournamentKey(tournament, matchesInRound, currentMatch);
        assertFalse(result);
    }

    @Test
    public void testValidBFTournamentKey_MissingBF() {
        MatchService matchService = new MatchService();
        Tournament tournament = new Tournament();
        tournament.setType(2);

        List<MatchModel> matchesInRound = new ArrayList<>();

        // create a match
        TeamModel team = new TeamModel();
        team.setId(1);
        TeamModel team2 = new TeamModel();
        team2.setId(2);


        MatchModel currentMatch = new MatchModel();
        currentMatch.setId(1);
        currentMatch.setRound(2);
        currentMatch.setLocal(team);
        currentMatch.setVisitor(team2);
        matchesInRound.add(currentMatch);

        MatchModel currentMatch2 = new MatchModel();
        currentMatch2.setId(2);
        currentMatch2.setRound(2);
        currentMatch2.setLocal(team);
        currentMatch2.setVisitor(team2);
        matchesInRound.add(currentMatch2);

        // no back and forth match
        System.out.println(currentMatch.getId());
        System.out.println("im fuming");
        Boolean result = matchService.validBFTournamentKey(tournament, matchesInRound, currentMatch);
        assertTrue(result);
    }

    @Test
    public void testValidTournamentKey() {
        MatchService matchService = new MatchService();
        Tournament tournament = new Tournament();
        tournament.setType(1);
        Integer streamSize = 1;
        Boolean response = matchService.validTournamentKey(tournament, streamSize);
        assertTrue(response);
    }
    @Test
    public void testValidTournamentKey_Invalid() {
        MatchService matchService = new MatchService();
        Tournament tournament = new Tournament();
        tournament.setType(1);
        Integer streamSize = 0;
        Boolean response = matchService.validTournamentKey(tournament, streamSize);
        assertFalse(response);
    }
    @Test
    public void testValidRepeatedMatchBackAndForth() {
        MatchService matchService = new MatchService();
        TeamModel team1 = new TeamModel();
        team1.setId(1);
        TeamModel team2 = new TeamModel();
        team2.setId(2);
        Tournament tournament = new Tournament();
        tournament.setType(2);
        MatchModel match1 = new MatchModel();
        match1.setId(1);
        MatchModel match2 = new MatchModel();
        match1.setId(1);
        Stream<MatchModel> repeatedMatches = Stream.of(match1, match2);
        repeatedMatches.findFirst().get().setLocal(team1);
        Stream<MatchModel> repeatedMatches2 = Stream.of(match1, match2);
        repeatedMatches2.findFirst().get().setVisitor(team2);
        Stream<MatchModel> repeatedMatches3 = Stream.of(match1, match2);
        MatchModel currentMatch = match1;
        currentMatch.setVisitor(team2);
        currentMatch.setLocal(team1);
        assertTrue(matchService.validRepeatedMatchBackAndForth(2, tournament, repeatedMatches3, currentMatch));

    }

    @Test
    public void testValidRepeatedMatchBackAndForth_() {
        MatchService matchService = new MatchService();
        TeamModel team1 = new TeamModel();
        team1.setId(1);
        TeamModel team2 = new TeamModel();
        team2.setId(2);
        Tournament tournament = new Tournament();
        tournament.setType(2);
        MatchModel match1 = new MatchModel();
        match1.setId(1);
        MatchModel match2 = new MatchModel();
        match1.setId(1);

        Stream<MatchModel> repeatedMatches = Stream.of(match1, match2);
        repeatedMatches.findFirst().get().setLocal(team1);

        Stream<MatchModel> repeatedMatches2 = Stream.of(match1, match2);
        repeatedMatches2.findFirst().get().setVisitor(team2);

        Stream<MatchModel> repeatedMatches3 = Stream.of(match1, match2);


        MatchModel currentMatch = new MatchModel();
        currentMatch.setVisitor(team1);
        currentMatch.setLocal(team2);
        assertFalse(matchService.validRepeatedMatchBackAndForth(2, tournament, repeatedMatches3, currentMatch));
    }

    @Test
    public void testGetRepeatedMatches() {
        MatchService matchService = new MatchService();
        TeamModel team1 = new TeamModel();
        team1.setId(1);
        TeamModel team2 = new TeamModel();
        team2.setId(2);

        MatchModel match1 = new MatchModel();
        match1.setId(1);
        match1.setLocal(team1);
        match1.setVisitor(team2);

        MatchModel match2 = new MatchModel();
        match2.setId(2);
        match2.setLocal(team2);
        match2.setVisitor(team1);

        List<MatchModel> matchesInRound = List.of(match2, match1);

        MatchModel currentMatch = new MatchModel();
        currentMatch.setId(2);
        currentMatch.setLocal(team1);
        currentMatch.setVisitor(team2);

        Stream<MatchModel> matches = matchService.getRepeatedMatches(matchesInRound, currentMatch);
        assertEquals(1, matches.count());
    }

    @Test
    public void testGetRepeatedMatches_NoRepeated() {
        MatchService matchService = new MatchService();
        TeamModel team1 = new TeamModel();
        team1.setId(1);
        TeamModel team2 = new TeamModel();
        team2.setId(2);
        TeamModel team3 = new TeamModel();
        team3.setId(3);
        TeamModel team4 = new TeamModel();
        team4.setId(4);

        MatchModel match1 = new MatchModel();
        match1.setId(1);
        match1.setLocal(team1);
        match1.setVisitor(team2);

        MatchModel match3 = new MatchModel();
        match3.setId(1);
        match3.setLocal(team1);
        match3.setVisitor(team2);

        MatchModel match2 = new MatchModel();
        match2.setId(2);
        match2.setLocal(team2);
        match2.setVisitor(team1);

        List<MatchModel> matchesInRound = List.of(match2, match1);

        MatchModel currentMatch = new MatchModel();
        currentMatch.setId(6);
        currentMatch.setLocal(team4);
        currentMatch.setVisitor(team3);

        Stream<MatchModel> matches = matchService.getRepeatedMatches(matchesInRound, currentMatch);
        assertEquals(0, matches.count());
    }

    @Test
    public void getStreamSize() {
        MatchService matchService = new MatchService();
        MatchModel match2 = new MatchModel();
        match2.setId(2);
        match2.setLocal(new TeamModel());
        match2.setVisitor(new TeamModel());
        Stream<MatchModel> stream = List.of(match2, match2, match2).stream();
        Integer size = matchService.getStreamSize(stream);
        assertEquals(3, size);
        stream = List.of(match2, match2, match2, match2, match2).stream();
        size = matchService.getStreamSize(stream);
        assertEquals(5, size);
    }

    @Test
    public void testGetNMatches() {
        MatchService matchService = new MatchService();
        // Test case 1: tournamentType = 1, round = 1
        Integer expected = 1;
        Integer actual = matchService.getNMatches(1, 1);
        assertEquals(expected, actual);

        // Test case 2: tournamentType = 1, round = 2
        expected = 2;
        actual = matchService.getNMatches(2, 1);
        assertEquals(expected, actual);

        // Test case 2: tournamentType = 1, round = 2
        expected = 4;
        actual = matchService.getNMatches(3, 1);
        assertEquals(expected, actual);

        // Test case 2: tournamentType = 1, round = 2
        expected = 8;
        actual = matchService.getNMatches(4, 1);
        assertEquals(expected, actual);

        // Test case 2: tournamentType = 1, round = 2
        expected = 16;
        actual = matchService.getNMatches(5, 1);
        assertEquals(expected, actual);

        // Test case 3: tournamentType = 1, round = 7
        expected = 32;
        actual = matchService.getNMatches(6, 1);
        assertEquals(expected, actual);

        // Test case 7: tournamentType = 1, round = 8
        expected = 64;
        actual = matchService.getNMatches(7, 1);
        assertEquals(expected, actual);

        // Test case 4: tournamentType = 2, round = 1
        expected = 1;
        actual = matchService.getNMatches(1, 2);
        assertEquals(expected, actual);

        // Test case 4: tournamentType = 2, round = 1
        expected = 4;
        actual = matchService.getNMatches(2, 2);
        assertEquals(expected, actual);

        // Test case 4: tournamentType = 2, round = 1
        expected = 8;
        actual = matchService.getNMatches(3, 2);
        assertEquals(expected, actual);

        // Test case 4: tournamentType = 2, round = 1
        expected = 16;
        actual = matchService.getNMatches(4, 2);
        assertEquals(expected, actual);

        // Test case 4: tournamentType = 2, round = 1
        expected = 32;
        actual = matchService.getNMatches(5, 2);
        assertEquals(expected, actual);

        // Test case 4: tournamentType = 2, round = 1
        expected = 64;
        actual = matchService.getNMatches(6, 2);
        assertEquals(expected, actual);

        // Test case 4: tournamentType = 2, round = 1
        expected = 128;
        actual = matchService.getNMatches(7, 2);
        assertEquals(expected, actual);

        // Test case 6: tournamentType = 3, round = 1
        expected = 0;
        actual = matchService.getNMatches(1, 3);
        assertEquals(expected, actual);
    }


}
