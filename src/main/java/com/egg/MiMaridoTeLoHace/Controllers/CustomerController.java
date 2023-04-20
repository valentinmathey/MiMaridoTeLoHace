 package com.egg.MiMaridoTeLoHace.Controllers;

import com.egg.MiMaridoTeLoHace.Entities.Customer;
import com.egg.MiMaridoTeLoHace.Entities.Image;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Services.CustomerService;
import com.egg.MiMaridoTeLoHace.Services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

 @Controller
@RequestMapping("/Customer")
public class CustomerController {
    @Autowired
    CustomerService customerServices;
    @Autowired
    ImageService imageService;

    @GetMapping("user/{id}")
    public String perfil(@PathVariable("id") String id, ModelMap model){
        model.addAttribute("entityReturn", customerServices.getById(id));
        return "User";
    }

    //eric: en el GET se envia el objeto donde se van a almacenar los datos del formCustomer.html
    @GetMapping("/register")
    public String form(ModelMap model){
        model.addAttribute("Customer", new Customer());
        return "FormCustomer";
    }

    //eric: el POST se reciben los datos enviados del html y los envia al services para que se ocupe de la creacion del customer
    @Transactional
    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public String create(@ModelAttribute Customer customer, @RequestParam("image") MultipartFile archivo) throws MiException {

        try {
            Image image = imageService.convertMultipartToImage(archivo);
            System.out.println(image.toString());
            customerServices.createCustomer(customer, image);
        } catch (Exception e){
            throw new MiException("EL ERROR SE ENCUENTRA EN EL POST" + e);
        }

        return "index";
    }
    
    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable("id") String id) throws MiException{
        customerServices.deleteCustomer(id);
        return "index.html";
    }
    
    @PutMapping("modify/{id}")
    public String modify(@PathVariable("id") String id, @RequestBody Customer customer) throws MiException {
        Customer customerModify = customerServices.getById(id);
        customerModify.setName(customer.getName());
        customerModify.setLocation(customer.getLocation());
        customerModify.setEmail(customer.getEmail());

        customerServices.modifyCustomer(customerModify);
        return "index.html";
    }


}
