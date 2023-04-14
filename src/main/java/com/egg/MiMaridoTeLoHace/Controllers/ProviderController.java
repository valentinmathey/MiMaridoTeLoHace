package com.egg.MiMaridoTeLoHace.Controllers;

import com.egg.MiMaridoTeLoHace.Entities.Provider;
import com.egg.MiMaridoTeLoHace.Services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class ProviderController {
    @Autowired
    ProviderService PS;

    @GetMapping("/getProviders")
    public List<Provider> getAll() throws Exception {
        return PS.getAll();
    }

    @GetMapping(value = "/search")
    public List<Provider> searchLocationAndProfession(@RequestParam String location, @RequestParam String profession){
        try {
            return PS.searchLocationAndProfession(location, profession);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
