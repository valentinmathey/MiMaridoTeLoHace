package com.egg.MiMaridoTeLoHace.Entities;

import com.egg.MiMaridoTeLoHace.Enums.Locations;
import com.egg.MiMaridoTeLoHace.Enums.Professions;
import com.egg.MiMaridoTeLoHace.Enums.Roles;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Provider {

    @Id
    @GeneratedValue (generator = "uuid")
    @GenericGenerator (name = "uuid", strategy = "uuid2")
    private String id;

    private String name;
    //agregado el lastname
    private String lastname;
    private String email;
    private String password;
    private String description;
    private int priceTime;
    
    @Enumerated(EnumType.STRING)
    private Roles role;

    @Enumerated(EnumType.STRING)
    private Professions profession;

    @Enumerated(EnumType.STRING)
    private Locations location;

    //agregado raiting
    private int raiting;

    @OneToOne
    private Image image;

}