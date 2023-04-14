package com.egg.MiMaridoTeLoHace.Entities;

import com.egg.MiMaridoTeLoHace.Enums.Ratings;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Review {
    
    @Id
    @GeneratedValue (generator = "uuid")
    private String id;

    private String comment;
    private Provider provider;
    private Customer costumer;

    @Enumerated(EnumType.STRING)
    private Ratings rating;

    public Review() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Customer getCostumer() {
        return costumer;
    }

    public void setCostumer(Customer costumer) {
        this.costumer = costumer;
    }

    public Ratings getRating() {
        return rating;
    }

    public void setRating(Ratings rating) {
        this.rating = rating;
    }
}
