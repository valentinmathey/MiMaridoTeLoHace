package com.egg.MiMaridoTeLoHace.Entities;


import com.egg.MiMaridoTeLoHace.Enums.Roles;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Admin {
    
    @Id
    @GeneratedValue (generator = "uuid")
    private String id;

    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @OneToOne
    private Image image;

    public Admin() {
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

    public Roles getRol() {
        return role;
    }

    public void setRol(Roles role) {
        this.role = role;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
