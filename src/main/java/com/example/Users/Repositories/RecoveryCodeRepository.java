package com.example.Users.Repositories;

import com.example.Users.Models.RecoveryCodeModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecoveryCodeRepository extends CrudRepository<RecoveryCodeModel, String> {
    List<RecoveryCodeModel> findByCode(String code);
}
