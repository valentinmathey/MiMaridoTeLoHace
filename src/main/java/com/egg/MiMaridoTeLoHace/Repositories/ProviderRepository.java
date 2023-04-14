/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.MiMaridoTeLoHace.Repositories;

import com.egg.MiMaridoTeLoHace.Entities.Provider;
import com.egg.MiMaridoTeLoHace.Enums.Locations;
import com.egg.MiMaridoTeLoHace.Enums.Professions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author eric
 */
@Repository
public interface ProviderRepository extends JpaRepository<Provider, String> {

    @Query("SELECT p FROM Provider p WHERE p.mail = :mail")
    List<Provider> searchByMail(
            @Param("mail") Spring mail);

    @Query("SELECT p FROM Provider p WHERE p.location = :location")
    List<Provider> searchByLocation(
            @Param("location") Locations location);

    @Query("SELECT p FROM Provider p WHERE p.profession = :profession")
    List<Provider> searchByProfession(
            @Param("profession") Profession profession);

    @Query("SELECT p FROM Provider p WHERE p.location = :location AND p.profession = :profession")
    List<Provider> searchByLocationAndProfession(
            @Param("location") Locations location,
            @Param("profession") Professions profession);
}
