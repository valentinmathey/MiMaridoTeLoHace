package com.egg.MiMaridoTeLoHace.Controllers;

import com.egg.MiMaridoTeLoHace.Entities.Admin;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Services.AdminService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @GetMapping("/list")
    public String getAll(ModelMap model) throws Exception {
        model.addAttribute("searchReturn",adminService.getAll());
        return "index.html";
    }

    @GetMapping("/create")
    public String create(@RequestBody Admin admin) throws MiException{

        try {

            adminService.createAdmin(admin);
            return "index.html";

        } catch (Exception e) {
            throw new MiException("Error al crear Admin");
        }
    }

    @PutMapping("/modify/{id}")
    public String modify(@PathVariable("id") String id, @RequestBody Admin admin) throws MiException{

        try {
            
            Admin modifiedAdmin = adminService.searchById(id);
            modifiedAdmin.setName(admin.getName());
            modifiedAdmin.setEmail(admin.getEmail());
            adminService.modifyAdmin(modifiedAdmin);

            return "index.html";

        } catch (Exception e) {
            throw new MiException("ERROR al modificar Administrador");
        }

    }


    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) throws MiException{

        try {
            
            adminService.deleteAdmin(id);
            return "index.htm";

        } catch (Exception e) {
            throw new MiException("Admin no encontrado!");
        }

    }
    
    @GetMapping("/list")
    public String list(ModelMap model) {
        
        List<Admin> adminList = adminService.listAdmins();
        model.addAttribute("admins", adminList);

        return "autor_list.hmtl";
        //el nombre este ultimo HTML se puede cambiar, asi como podemos prescindir de este bloque de codigo si es que no queremos listar admins
    }

}
