package com.egg.MiMaridoTeLoHace.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.MiMaridoTeLoHace.Entities.Provider;
 

@Repository
public interface ProviderRepository extends JpaRepository <Provider, String> {
    
}
