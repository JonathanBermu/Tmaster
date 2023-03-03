package com.example.Users.Repositories;

import com.example.Users.Models.MatchStatTypes;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchStatTypeRepository extends CrudRepository<MatchStatTypes, Integer> {
    List<MatchStatTypes> findById(int number);
}