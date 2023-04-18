package com.egg.MiMaridoTeLoHace.Controllers;

import com.egg.MiMaridoTeLoHace.Entities.Customer;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Customer")
public class CustomerController {
    @Autowired
    CustomerService customerServices;

    @GetMapping("user/{id}")
    public String Perfil(@PathVariable("id") String id, ModelMap model){
        model.addAttribute(customerServices.searchId(id));
        return "user";
    }
    @GetMapping("create")
    public String Create(@RequestBody Customer customer) throws MiException {
        customerServices.save(customer);
        return "index";
    }
    @DeleteMapping("delete/{id}")
    public String Delete(@PathVariable("id") String id){
        customerServices.delete(id);
        return "index";
    }
    @PutMapping("modify/{id}")
    public String Modify(@PathVariable("id") String id, @RequestBody Customer customer) throws MiException {
        Customer customerModify = customerServices.searchId(id);
        customerModify.setName(customer.getName());
        customerModify.setLocation(customer.getLocation());
        customerModify.setEmail(customer.getEmail());

        customerServices.modify(customerModify);
        return "index";
    }


}
