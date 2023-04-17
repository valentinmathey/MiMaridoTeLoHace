package com.egg.MiMaridoTeLoHace.Controllers;

import com.egg.MiMaridoTeLoHace.Entities.Provider;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequestMapping("/Provider")
public class ProviderController {
    @Autowired
    ProviderService providerService;

    @GetMapping("/list")
    public String getAll(ModelMap model) throws Exception {
        model.addAttribute("searchReturn",providerService.getAll());
        return "index.html";
    }
    //codigo de eric optimizado usando IA
    @GetMapping("/search")
    public String search(@RequestParam String location, @RequestParam String profession, ModelMap model) throws Exception {
        List<Provider> searchReturn;
        if (!StringUtils.isEmpty(location) && !StringUtils.isEmpty(profession)) {
            searchReturn = providerService.searchLocationAndProfession(location, profession);
        } else {
            switch (!StringUtils.isEmpty(location) ? 1 : !StringUtils.isEmpty(profession) ? 2 : 0) {
                case 1:
                    searchReturn = providerService.searchLocation(location);
                    break;
                case 2:
                    searchReturn = providerService.searchProfession(profession);
                    break;
                case 3:
                    searchReturn = providerService.getAll();
                    break;
                default:
                    searchReturn = providerService.getAll();
                    break;
            }
        }
        model.addAttribute("searchReturn", searchReturn);
        return "Providers";
    }

    /*codigo original de eric sin uso de IA*/
//    @GetMapping("/search")
//    public String search(@RequestParam String location, @RequestParam String profession, ModelMap model) {
//        try {
//            if (!location.isEmpty() && !profession.isEmpty()) {
//                model.addAttribute("searchReturn", providerService.searchLocationAndProfession(location, profession));
//            }else if (!location.isEmpty()) {
//                model.addAttribute("searchReturn", providerService.searchLocation(location));
//            }else if (!profession.isEmpty()) {
//                model.addAttribute("searchReturn", providerService.searchProfession(profession));
//            } else {
//                model.addAttribute("searchReturn", providerService.getAll());
//            }
//        } catch (Exception e) {
//            return "index";
//        }
//        return "Providers";
//    }
    
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
