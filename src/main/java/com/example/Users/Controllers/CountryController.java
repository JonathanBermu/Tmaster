package com.example.Users.Controllers;

import com.example.Users.Services.CountryService;
import com.example.Users.Types.Country.AddCountryType;
import com.example.Users.Types.Country.DeleteCountryType;
import com.example.Users.Types.Country.UpdateCountryType;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {
    @Autowired
    private CountryService countryService;

    @PostMapping(value = "countries/add_country")
    public ResponseEntity addCountry(@RequestBody AddCountryType request) {
        return countryService.addCountry(request);
    }

    @PostMapping(value="countries/delete_country")
    public ResponseEntity deleteCountry(@RequestBody DeleteCountryType request) {
        return countryService.deleteCountry(request);
    }

    @PostMapping(value = "countries/update_country")
    public ResponseEntity updateCountry(@RequestBody UpdateCountryType request) {
        return countryService.updateCountry(request);
    }
}
