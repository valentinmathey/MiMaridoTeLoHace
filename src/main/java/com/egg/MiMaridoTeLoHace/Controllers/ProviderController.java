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
@RequestMapping("/provider")
public class ProviderController {
    @Autowired
    ProviderService PS;

    @GetMapping("/list")
    public List<Provider> getAll() throws Exception {
        return PS.getAll();
    }

    @GetMapping("/{mail}")
    public Provider getByEmail(@PathVariable("mail") String mail) {
        try {
            return PS.getEmail(mail);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    // @GetMapping("/{id}")
    // public Provider getById(@PathVariable("id") String id) {
    // try {
    // return PS.getId(id);
    // } catch (Exception e) {
    // // TODO: handle exception
    // }
    // }

    @GetMapping("/search")
    public List<Provider> search(@RequestParam String location, @RequestParam String profession) {
        try {
            // los ifs estan hecho por las dudas, no se si enviarle un valor nulo va a
            // permitir traerlos que si traen valor
            if (!location.isEmpty() && !profession.isEmpty()) {
                return PS.searchLocationAndProfession(location, profession);
            }
            if (!location.isEmpty() && profession.isEmpty()) {
                return PS.searchLocation(location);
            }
            if (location.isEmpty() && !profession.isEmpty()) {
                return PS.searchProfession(profession);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
