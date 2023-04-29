package com.egg.MiMaridoTeLoHace.Controllers;


import com.egg.MiMaridoTeLoHace.Entities.User;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.ProcessBuilder.Redirect;

import javax.servlet.http.HttpSession;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import com.egg.MiMaridoTeLoHace.Entities.User;



@Controller
@RequestMapping("/")
public class PortalController {
    @Autowired
    UserService userService;
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_PROVIDER', 'ROLE_ADMIN')")
    @GetMapping("/home")
    public String home(HttpSession session, Model model){

        User logued = (User) session.getAttribute("userSession");

        if (logued.getRole().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }

        return "home";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model){

        if (error!=null) {
            String mssg = "USUARIO O CONTRASEÑA INVALIDOS";
            model.addAttribute("mssg", mssg);
        }

        return "login";
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }
    
    @GetMapping("about")
    public String about(){
        return "about";
    }

    @GetMapping("/search")
    public String showProviders(@RequestParam("profession") String profession, ModelMap model) throws MiException {
        List<User> searchReturn = userService.searchByProfession(profession);
        if (!searchReturn.isEmpty()) {
            model.addAttribute("searchReturn", searchReturn);
        } else {
            return "redirect:/";
        }
        return "provider";
    }
}
