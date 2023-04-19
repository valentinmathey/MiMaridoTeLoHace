package com.egg.MiMaridoTeLoHace.Repositories;

import com.egg.MiMaridoTeLoHace.Entities.Provider;
import com.egg.MiMaridoTeLoHace.Enums.Locations;
import com.egg.MiMaridoTeLoHace.Enums.Professions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, String> {
    @Query("SELECT p FROM Provider p WHERE p.location = :location")
    List<Provider> searchByLocation(
            @Param("location") Locations location);

    @Query("SELECT p FROM Provider p WHERE p.profession = :profession")
    List<Provider> searchByProfession(
            @Param("profession") Professions profession);

    @Query("SELECT p FROM Provider p WHERE p.location = :location AND p.profession = :profession")
    List<Provider> searchByLocationAndProfession(
            @Param("location") Locations location,
            @Param("profession") Professions profession);
    
    @Query("SELECT p FROM Provider p WHERE p.email = :email")
    public Provider searchByEmail(@Param("email") String email);
}
