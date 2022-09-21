package com.start.timemanager.repository;

import com.start.timemanager.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);
     @Query("select u from User u where u.email=email")
     Optional<User> findUserByEmailO(String email);
}
