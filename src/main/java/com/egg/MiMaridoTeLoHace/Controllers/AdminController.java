package com.egg.MiMaridoTeLoHace.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.MiMaridoTeLoHace.Entities.User;
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
    public String dashboard(Model model) throws MiException {

        model.addAttribute("users", userRepository.findAll(Sort.by(Sort.Direction.ASC, "profession")));

        return "dashboard";
    }

    @PostMapping("/search")
    public String searchEngine(@RequestParam(name = "search", required = false) String search, Model model) {

        List<User> se = userRepository.searchEngine(search);
        model.addAttribute("se", se);
        model.addAttribute("users", userRepository.findAll(Sort.by(Sort.Direction.ASC, "profession")));
        return "dashboard";
        
    }
}
