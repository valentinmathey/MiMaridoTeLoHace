package com.egg.MiMaridoTeLoHace.Repositories;

import com.egg.MiMaridoTeLoHace.Enums.Professions;
import com.egg.MiMaridoTeLoHace.Enums.Roles;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.egg.MiMaridoTeLoHace.Entities.User;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User searchByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findByRole(@Param("role") Roles role);

    @Query("SELECT u FROM User u WHERE u.profession = :profession AND u.alta = true")
    List<User> searchByProfessionAlta(@Param("profession") Professions profession);

    @Query("SELECT u FROM User u WHERE u.name = :name")
    List<User> findByName(@Param("name") String name);

    @Query("SELECT u FROM User u WHERE u.lastname = :lastname")
    List<User> findByNameAndLastName(@Param("lastname") String lastname);

    @Query("SELECT u FROM User u WHERE u.rating = :rating")
    Optional<User> findByRating(@Param("rating") Double rating);

    @Query("SELECT u FROM User u WHERE u.name LIKE %:search% OR u.lastname LIKE %:search% OR u.profession LIKE %:search% OR u.email LIKE %:search%")
    List<User> searchEngine(@Param("search") String search);

}
