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

@Entity
public class Provider {

    @Id
    @GeneratedValue (generator = "uuid")
    private String id;

    private String name;
    private String email;
    private String password;
    private String description;
    private int priceTime;
    
    @Enumerated(EnumType.STRING)
    private Roles rol;

    @Enumerated(EnumType.STRING)
    private Professions profession;

    @Enumerated(EnumType.STRING)
    private Locations location;

    @OneToOne
    private Image image;

    public Provider() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriceTime() {
        return priceTime;
    }

    public void setPriceTime(int priceTime) {
        this.priceTime = priceTime;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public Professions getProfession() {
        return profession;
    }

    public void setProfession(Professions profession) {
        this.profession = profession;
    }

    public Locations getLocation() {
        return location;
    }

    public void setLocation(Locations location) {
        this.location = location;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    

}