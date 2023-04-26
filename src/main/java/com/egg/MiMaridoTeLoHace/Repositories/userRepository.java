package com.egg.MiMaridoTeLoHace.Repositories;

import com.egg.MiMaridoTeLoHace.Enums.Professions;
import com.egg.MiMaridoTeLoHace.Enums.Roles;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;

public interface UserRepository extends JpaRepository<User, String> {
    
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User searchByEmail(@Param("email") String email);
    
    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findByRole(@Param("role") Roles role);
    
    @Query("SELECT u FROM User u WHERE u.profession = :profession")
    Optional<User> searchByProfession(@Param("profession") Professions profession);
    
    @Query("SELECT u FROM User u WHERE u.name = :name")
    List<User> findByName(@Param("name") String name);
    
    @Query("SELECT u FROM User u WHERE u.lastname = :lastname")
    List<User> findByNameAndLastName(@Param("lastname") String lastname);
    
//    @Query("SELECT u FROM User u WHERE u.name = :name AND u.lastname = :lastname")
//    List<User> findByNameAndLastName(@Param("name") String name), @Param("lastname") String lastname);
    
    @Query("SELECT u FROM User u WHERE u.raiting = :raiting")
    Optional<User> findByRaiting(@Param("raiting") Double raiting);
    
    
    
    
}
