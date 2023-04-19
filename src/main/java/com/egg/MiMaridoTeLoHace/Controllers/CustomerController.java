 package com.egg.MiMaridoTeLoHace.Controllers;

import com.egg.MiMaridoTeLoHace.Entities.Customer;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerServices;

    @GetMapping("user/{id}")
    public String perfil(@PathVariable("id") String id, ModelMap model){
        model.addAttribute(customerServices.searchById(id));
        return "user.html";
    }
    
    @GetMapping("create")
    public String create(@RequestBody Customer customer) throws MiException {
        customerServices.createCustomer(customer);
        return "index.html";
    }
    
    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable("id") String id) throws MiException{
        customerServices.deleteCustomer(id);
        return "index.html";
    }
    
    @PutMapping("modify/{id}")
    public String modify(@PathVariable("id") String id, @RequestBody Customer customer) throws MiException {
        Customer customerModify = customerServices.searchById(id);
        customerModify.setName(customer.getName());
        customerModify.setLocation(customer.getLocation());
        customerModify.setEmail(customer.getEmail());

        customerServices.modifyCustomer(customerModify);
        return "index.html";
    }


}
