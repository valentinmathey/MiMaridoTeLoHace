package com.egg.MiMaridoTeLoHace.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.MiMaridoTeLoHace.Enums.Roles;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Repositories.UserRepository;
import com.egg.MiMaridoTeLoHace.Services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(name = "role", required = false) String role, Model model) throws MiException {
        
        if (role == null || role.isEmpty()) {
            model.addAttribute("users", null);
        } else if (role.equals("customer")) {
            model.addAttribute("users", userRepository.findByRole(Roles.CUSTOMER));
        } else if (role.equals("provider")) {
            model.addAttribute("users", userRepository.findByRole(Roles.PROVIDER));
        }

        return "dashboard";
    }
}
