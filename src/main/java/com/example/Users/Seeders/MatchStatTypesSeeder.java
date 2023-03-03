package com.example.Users.Seeders;

import com.example.Users.Models.MatchStatTypes;
import com.example.Users.Repositories.MatchStatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MatchStatTypesSeeder implements CommandLineRunner {
    @Autowired
    private MatchStatTypeRepository matchStatTypesRepository;

    @Override
    public void run(String... args) throws Exception {
        MatchStatTypes goal = new MatchStatTypes();
        goal.setName("goal");
        goal.setLabel("Goal");
        goal.setTeamStat(0);

        MatchStatTypes yellowCard = new MatchStatTypes();
        yellowCard.setName("yellow_card");
        yellowCard.setLabel("Yellow Card");
        yellowCard.setTeamStat(0);

        MatchStatTypes redCard = new MatchStatTypes();
        redCard.setName("red_card");
        redCard.setLabel("Red Card");
        redCard.setTeamStat(0);

        MatchStatTypes offside = new MatchStatTypes();
        offside.setName("offside");
        offside.setLabel("Offside");
        offside.setTeamStat(1);

        MatchStatTypes ownGoal = new MatchStatTypes();
        ownGoal.setName("own_goal");
        ownGoal.setLabel("Own Goal");
        ownGoal.setTeamStat(0);

        MatchStatTypes penalty = new MatchStatTypes();
        penalty.setName("penalty_goal");
        penalty.setLabel("Penalty Goal");
        penalty.setTeamStat(0);

        MatchStatTypes corner = new MatchStatTypes();
        corner.setName("corner");
        corner.setLabel("Corner");
        corner.setTeamStat(1);

        MatchStatTypes possession = new MatchStatTypes();
        possession.setName("possession");
        possession.setLabel("Possession");
        possession.setTeamStat(1);

        MatchStatTypes fouls = new MatchStatTypes();
        fouls.setName("fouls");
        fouls.setLabel("Fouls");
        fouls.setTeamStat(1);

        MatchStatTypes shots = new MatchStatTypes();
        shots.setName("shots");
        shots.setLabel("Shots");
        shots.setTeamStat(1);

        MatchStatTypes shots_target = new MatchStatTypes();
        shots_target.setName("shots_target");
        shots_target.setLabel("Shots on target");
        shots_target.setTeamStat(1);

        MatchStatTypes passes = new MatchStatTypes();
        passes.setName("passes");
        passes.setLabel("Passes");
        passes.setTeamStat(1);

        List<MatchStatTypes> matchStatTypes = List.of(goal, yellowCard, redCard, offside, ownGoal, penalty, corner, possession, fouls, shots, shots_target, passes);
        for (MatchStatTypes matchStatType : matchStatTypes) {
            try {
                matchStatTypesRepository.save(matchStatType);
            } catch (Exception e) {
            }
        }
    }
}
