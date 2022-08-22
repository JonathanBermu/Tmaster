package com.example.Users.Services;

import com.example.Users.Models.CountryModel;
import com.example.Users.Repositories.CountryRepository;
import com.example.Users.Types.Country.AddCountryType;
import com.example.Users.Types.Country.DeleteCountryType;
import com.example.Users.Types.Country.UpdateCountryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;
    public ResponseEntity addCountry(AddCountryType request){
        CountryModel newCountry = new CountryModel();
        newCountry.setName(request.getName());
        newCountry.setState(1);
        countryRepository.save(newCountry);
        return new ResponseEntity<>("Country added successfully", HttpStatus.OK);
    }
    public ResponseEntity deleteCountry(DeleteCountryType request){
        CountryModel country = countryRepository.findById(request.getId()).get(0);
        country.setState(2);
        countryRepository.save(country);
        return new ResponseEntity<>("Country deleted successfully", HttpStatus.OK);
    }

    public ResponseEntity updateCountry(UpdateCountryType request){
        CountryModel country = countryRepository.findById(request.getId()).get(0);
        country.setName(request.getName());
        countryRepository.save(country);
        return new ResponseEntity<>("Country updated successfully", HttpStatus.OK);
    }

}
