package com.egg.MiMaridoTeLoHace.Entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Data
public class Review {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String message;
    private int rating;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User UserCustomerId;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private User UserProviderId;
}