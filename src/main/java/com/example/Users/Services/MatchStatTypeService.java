package com.example.Users.Services;

import com.example.Users.Models.MatchStatTypes;
import com.example.Users.Repositories.MatchStatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class MatchStatTypeService {

    private final MatchStatTypeRepository matchStatTypeRepository;

    @Autowired
    public MatchStatTypeService(MatchStatTypeRepository matchStatTypeRepository) {
        this.matchStatTypeRepository = matchStatTypeRepository;
    }

    @GetMapping("/matchstattypes")
    public List<MatchStatTypes> getAllMatchStatTypes() {
        return (List<MatchStatTypes>) matchStatTypeRepository.findAll();
    }
}