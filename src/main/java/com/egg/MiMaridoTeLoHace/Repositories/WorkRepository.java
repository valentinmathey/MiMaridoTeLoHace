package com.egg.MiMaridoTeLoHace.Repositories;

import com.egg.MiMaridoTeLoHace.Entities.User;
import com.egg.MiMaridoTeLoHace.Entities.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface WorkRepository extends JpaRepository<Work, String> {

    @Query("SELECT w FROM Work w WHERE w.userProviderId = :provider_id")
    List<Work> getWorkByUserProvider(@Param("provider_id") User provider);

    @Query("SELECT w FROM Work w WHERE w.userCustomerId = :customer_id")
    List<Work> getWorkByUserCustomer(@Param("customer_id") User customer);
}
