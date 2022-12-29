package com.example.Users.Seeders;

import com.example.Users.Models.CountryModel;
import com.example.Users.Models.SportsModel;
import com.example.Users.Repositories.CountryRepository;
import com.example.Users.Repositories.SportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CountriesSeeder implements CommandLineRunner {
    @Autowired
    private CountryRepository countryRepository;

    @Override
    public void run(String... args) throws Exception {
        CountryModel country = new CountryModel();
        country.setName("United states");
        country.setState(1);
        CountryModel country2 = new CountryModel();
        country2.setName("Ingland");
        country2.setState(1);
        CountryModel country3 = new CountryModel();
        country3.setName("Argentina");
        country3.setState(1);
        CountryModel country4 = new CountryModel();
        country4.setName("Spain");
        country4.setState(1);
        CountryModel country5 = new CountryModel();
        country5.setName("France");
        country5.setState(1);
        CountryModel country6 = new CountryModel();
        country6.setName("Italy");
        country6.setState(1);
        CountryModel country7 = new CountryModel();
        country7.setName("Germany");
        country7.setState(1);
        CountryModel country8 = new CountryModel();
        country8.setName("Mexico");
        country8.setState(1);
        List<CountryModel> countries = List.of(country, country2, country3, country4, country5, country6, country7, country8);
        for (CountryModel element : countries) {
            try {
                countryRepository.save(element);
            } catch (Exception e) {
            }
        }
    }
}
