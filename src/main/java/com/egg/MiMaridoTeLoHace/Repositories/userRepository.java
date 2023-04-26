package com.egg.MiMaridoTeLoHace.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.egg.MiMaridoTeLoHace.Entities.User;

public interface UserRepository extends JpaRepository <User, String> {
    @Query("SELECT p FROM User p WHERE p.email = :email")
    public User searchByEmail(@Param("email") String email);
}
