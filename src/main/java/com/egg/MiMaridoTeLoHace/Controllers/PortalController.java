package com.egg.MiMaridoTeLoHace.Controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/")
public class PortalController {

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_PROVIDER', 'ROLE_ADMIN')")
    @GetMapping("/home")
    public String portalHome(){
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

    @GetMapping("index")
    public String index(){
        return "index";
    }
    
    @GetMapping("about")
    public String about(){
        return "about";
    }
}
