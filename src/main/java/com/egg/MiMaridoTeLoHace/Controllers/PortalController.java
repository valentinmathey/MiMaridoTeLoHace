package com.egg.MiMaridoTeLoHace.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalController {

    @GetMapping("/home")
    public String portalHome(){
        return "home";
    }
}
