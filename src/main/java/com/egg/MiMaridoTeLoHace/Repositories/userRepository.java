package com.egg.MiMaridoTeLoHace.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

public interface userRepository extends JpaRepository<User, String> {
    
}
