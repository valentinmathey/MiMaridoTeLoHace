 package com.egg.MiMaridoTeLoHace.Controllers;

import com.egg.MiMaridoTeLoHace.Entities.Customer;
import com.egg.MiMaridoTeLoHace.Entities.Image;
import com.egg.MiMaridoTeLoHace.Enums.Locations;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Services.CustomerService;
import com.egg.MiMaridoTeLoHace.Services.ImageService;
import com.egg.MiMaridoTeLoHace.converters.ImageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerServices;
    @Autowired
    ImageService imageService;
    @Autowired
    ImageConverter imageConverter;

    @GetMapping("user/{id}")
    public String perfil(@PathVariable("id") String id, ModelMap model){
        model.addAttribute("entityReturn", customerServices.getById(id));
        return "User";
    }


    @GetMapping("/register")
    public String form(ModelMap model){
        model.addAttribute("customer", new Customer());
        model.addAttribute("locations", Locations.values());
        return "FormCustomer";
    }

    @Transactional
    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public String create(@ModelAttribute Customer customer, @RequestParam("img") MultipartFile archivo) throws MiException {
        long maxFileSize = 5242880; //eric: 5MB es el limite a guardar, puede ser modificado
        Image image;
        try {
            if(!archivo.isEmpty() && archivo.getSize() < maxFileSize){
                image = imageConverter.convert(archivo);
                imageService.Save(image);
            } else {
                image = imageService.GetByName("customer-avatar.png");
                imageService.Save(image);
            }
            customerServices.createCustomer(customer, image);
        } catch (Exception e){
            throw new MiException("EL ERROR SE ENCUENTRA EN EL POST: " + e);
        }

        return "redirect:/home";
    }
    
    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable("id") String id) throws MiException{
        customerServices.deleteCustomer(id);
        return "index";
    }
    
    @PutMapping("modify/{id}")
    public String modify(@PathVariable("id") String id, @RequestBody Customer customer) throws MiException {
        Customer customerModify = customerServices.getById(id);
        customerModify.setName(customer.getName());
        customerModify.setEmail(customer.getEmail());

        customerServices.modifyCustomer(customerModify);
        return "index";
    }


}
