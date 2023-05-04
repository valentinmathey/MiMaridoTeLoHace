package com.egg.MiMaridoTeLoHace.Entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import com.egg.MiMaridoTeLoHace.Enums.WorkStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Data
@Entity
public class Work {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String workName;
    private String workDescription;

    @Enumerated(EnumType.STRING)
    private WorkStatus workStatus;
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User userCustomerId;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private User userProviderId;
    
    //REVIEW--------------------------
    
    private String review;
    private int ratingWork;
}