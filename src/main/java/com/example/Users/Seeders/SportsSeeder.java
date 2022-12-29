package com.example.Users.Seeders;

import com.example.Users.Models.SportsModel;
import com.example.Users.Repositories.SportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
    public class SportsSeeder implements CommandLineRunner {
        @Autowired
        private SportsRepository sportsRepository;

        @Override
        public void run(String... args) throws Exception {
            SportsModel sport = new SportsModel();
            sport.setState(1);
            sport.setName("Football (Soccer)");
            SportsModel sport2 = new SportsModel();
            sport2.setState(1);
            sport2.setName("Football (American)");
            SportsModel sport3 = new SportsModel();
            sport3.setState(1);
            sport3.setName("Basketball");
            SportsModel sport4 = new SportsModel();
            sport4.setState(1);
            sport4.setName("Baseball");
            SportsModel sport5 = new SportsModel();
            sport5.setState(1);
            sport5.setName("Tennis");
            SportsModel sport6 = new SportsModel();
            sport6.setState(1);
            sport6.setName("Hokey");
            List<SportsModel> sports = List.of(sport, sport2, sport3, sport4, sport5, sport6);
            for (SportsModel element : sports) {
                try {
                    sportsRepository.save(element);
                } catch (Exception e) {
                }
            }

        }
}
