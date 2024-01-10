package com.digilab.springboottokencookies.repos;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.digilab.springboottokencookies.models.ERole;
import com.digilab.springboottokencookies.models.Role;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
