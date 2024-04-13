package com.gxlpes.poc.auth.api.web.repository;

import java.util.Optional;

import com.gxlpes.poc.auth.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);

}
