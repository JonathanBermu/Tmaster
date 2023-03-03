package com.example.Users.Controllers;

import com.example.Users.Models.MatchStatTypes;
import com.example.Users.Services.MatchStatTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins= "http://localhost:4000")
public class MatchStatTypeController {
    @Autowired
    private MatchStatTypeService matchStatTypeService;

    @GetMapping(value="matches/matchstattypes")
    public List<MatchStatTypes> getAllMatchStatTypes() {
        return matchStatTypeService.getAllMatchStatTypes();
    }
}