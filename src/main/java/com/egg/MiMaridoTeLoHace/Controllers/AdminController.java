package com.egg.MiMaridoTeLoHace.Controllers;

import com.egg.MiMaridoTeLoHace.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @GetMapping("/list")
    public String getAll(ModelMap model) throws Exception {
        model.addAttribute("searchReturn",adminService.getAll());
        return "index.html";
    }

    
}
