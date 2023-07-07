package com.multiversebackend.snipprio.repository;

import com.multiversebackend.snipprio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    //will interact with all user crud database methods
}
