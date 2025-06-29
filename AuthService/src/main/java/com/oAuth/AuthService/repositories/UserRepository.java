package com.oAuth.AuthService.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.oAuth.AuthService.models.UserModel;

public interface UserRepository extends MongoRepository<UserModel,String> {

    Optional<UserModel> findByEmail(String email);

}
