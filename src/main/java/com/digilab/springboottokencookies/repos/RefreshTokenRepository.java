package com.digilab.springboottokencookies.repos;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.digilab.springboottokencookies.models.RefreshToken;
import com.digilab.springboottokencookies.models.User;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String token);

    int deleteByUser(User user);

}