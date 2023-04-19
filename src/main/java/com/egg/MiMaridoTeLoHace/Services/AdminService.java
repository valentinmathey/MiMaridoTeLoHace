package com.egg.MiMaridoTeLoHace.Services;

import java.util.ArrayList;
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
    public void createAdmin (Admin admin) throws MiException{

        validateData(admin);

        try {
            
            admin.setRole(Roles.ADMIN);

            adminRepository.save(admin);

        } catch (Exception e) {
            throw new MiException("ERROR al cerar nuevo perfil de Administrador");
        }

    }
    
    @Transactional
    public void modifyAdmin(Admin admin) throws MiException {

        validateData(admin);

        try {

            validateData(admin);

            adminRepository.save(admin);

        } catch (Exception e) {
            throw new MiException("ningun EMAIL coincide en base de datos");
        }
    }

   @Transactional 
    public void deleteAdmin(String id) throws MiException{

        try {

            Optional<Admin> adminId = adminRepository.findById(id);    
            
            if (adminId.isPresent()) {
            
                Admin admin = adminId.get();
                adminRepository.delete(admin);
            }

        } catch (Exception e) {
            throw new MiException("Ningun ADMIN encontrado");
        }
    }
    
    public List<Admin> getAll() throws MiException {
        try {
            return adminRepository.findAll();
        } catch (Exception e) {
            throw new MiException("LISTA VACIA O NULA");
        }
    }

    public Admin searchById(String id) throws MiException{
        
        try {
            
            Optional<Admin> adminIdConsult = adminRepository.findById(id);

            if (adminIdConsult.isPresent()) {
                Admin newAdmin = adminRepository.findById(id).get();
                return newAdmin;
            }

            return null;

        } catch (Exception e) {
            throw new MiException("ID de Administrador no encontrada");
        }

    }

    public List<Admin> listAdmins(){

        List<Admin> admins = new ArrayList<>();
        admins = adminRepository.findAll();
        return admins;
    }

    private void validateData(Admin admin) throws MiException {
        if (admin.getName().isEmpty() || admin.getName() == null) {
            throw new MiException("NOMBRE PROVIDER invalido o vacio");
        } else if (admin.getName().isEmpty() || admin.getName() == null) {
            throw new MiException("EMAIL invalido o vacio");
        }
    }

}
