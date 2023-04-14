package com.egg.MiMaridoTeLoHace.Repositories;

import com.egg.MiMaridoTeLoHace.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    
}
