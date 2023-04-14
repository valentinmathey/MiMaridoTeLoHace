/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.MiMaridoTeLoHace.Repositories;

import com.egg.MiMaridoTeLoHace.Entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mathe
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    
}
