package com.egg.MiMaridoTeLoHace.Services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egg.MiMaridoTeLoHace.Entities.Admin;
import com.egg.MiMaridoTeLoHace.Enums.Roles;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Repositories.AdminRepository;

@Service
public class AdminService {
    
    @Autowired
    AdminRepository adminRepository;

    @Transactional
    public void createAdmin (String name, String email, String password) throws MiException{

        validateData(name, email);

        Admin admin = new Admin();

        admin.setName(name);
        admin.setEmail(password);
        admin.setPassword(password);
        
        admin.setRole(Roles.ADMIN);

        adminRepository.save(admin);

    }

    public void modifyAdmin(String email) throws MiException {

        validateData(email, email);

        try {

            Optional<Admin> adminEmail = adminRepository.findByEmail(email);

            if (adminEmail.isPresent()) {

                Admin admin = adminEmail.get();
                admin.setEmail(email);
                admin.setName(email);
                adminRepository.save(admin);
            }

        } catch (Exception e) {
            throw new MiException("ningun EMAIL coincide en base de datos");
        }
    }

    public void deleteAdmin(String email) throws MiException{

        try {

            Optional<Admin> adminEmail = adminRepository.findByEmail(email);    
            
            if (adminEmail.isPresent()) {
            
                Admin admin = adminEmail.get();
                adminRepository.delete(admin);
            }

        } catch (Exception e) {
            throw new MiException("Ningun EMAIL encontrado");
        }
    }

    public List<Admin> getAll() throws MiException {
        try {
            return adminRepository.findAll();
        } catch (Exception e) {
            throw new MiException("LISTA VACIA O NULA");
        }
    }

    private void validateData(String name, String email) throws MiException {
        if (name.isEmpty() || name == null) {
            throw new MiException("NOMBRE PROVIDER invalido o vacio");
        } else if (email.isEmpty() || email == null) {
            throw new MiException("EMAIL invalido o vacio");
        }
    }

}
