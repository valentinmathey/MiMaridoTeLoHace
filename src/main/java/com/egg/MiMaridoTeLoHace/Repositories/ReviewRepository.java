package com.egg.MiMaridoTeLoHace.Repositories;

import com.egg.MiMaridoTeLoHace.Entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {

    @Query("SELECT r FROM Review r WHERE r.UserProviderId =: provider_id")
    List<Review> getReviewByUserProvider(@Param("provider_id") String provider);

    @Query("SELECT r FROM Review r WHERE r.UserCustomerId =: customer_id")
    List<Review> getReviewByUserCustomer(@Param("customer_id") String customer);
}
