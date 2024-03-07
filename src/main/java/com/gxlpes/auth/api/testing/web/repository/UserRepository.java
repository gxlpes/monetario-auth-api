package com.gxlpes.auth.api.testing.web.repository;

import java.util.Optional;

import com.gxlpes.auth.api.testing.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);

}
