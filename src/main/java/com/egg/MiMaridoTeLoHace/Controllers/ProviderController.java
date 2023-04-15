package com.egg.MiMaridoTeLoHace.Controllers;

import com.egg.MiMaridoTeLoHace.Entities.Provider;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/")
public class ProviderController {
    @Autowired
    ProviderService providerService;

    @GetMapping("/getProviders")
    public List<Provider> getAll() throws Exception {
        return providerService.getAll();
    }

    @GetMapping(value = "/search")
    public List<Provider> searchLocationAndProfession(@RequestParam String location, @RequestParam String profession){
        try {
            return providerService.searchLocationAndProfession(location, profession);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @DeleteMapping("{id}")
    public String eliminar(@PathVariable("id") String id, ModelMap modelo) throws MiException{
        try {
            providerService.deleteProvider(id);
        
            return "index.html";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
    
}
