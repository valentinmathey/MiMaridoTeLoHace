package com.egg.MiMaridoTeLoHace.Controllers;

import com.egg.MiMaridoTeLoHace.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AdminController {
    @Autowired
    AdminService adminService;
}
