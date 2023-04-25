package com.egg.MiMaridoTeLoHace.Controllers;

import com.egg.MiMaridoTeLoHace.Entities.Customer;
import com.egg.MiMaridoTeLoHace.Entities.Image;
import com.egg.MiMaridoTeLoHace.Entities.Provider;
import com.egg.MiMaridoTeLoHace.Enums.Locations;
import com.egg.MiMaridoTeLoHace.Enums.Professions;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Services.ImageService;
import com.egg.MiMaridoTeLoHace.Services.ProviderService;
import com.egg.MiMaridoTeLoHace.converters.ImageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequestMapping("/provider")
public class ProviderController {
    @Autowired
    ProviderService providerService;
    @Autowired
    ImageService imageService;
    @Autowired
    ImageConverter imageConverter;

    @GetMapping("user/{id}")
    public String perfil(@PathVariable("id") String id, ModelMap model){
        model.addAttribute("entityReturn", providerService.getById(id));
        return "User";
    }
    @GetMapping("/register")
    public String form(ModelMap model){
        model.addAttribute("provider", new Provider());
        model.addAttribute("professions", Professions.values());
        return "FormProvider";
    }

    @Transactional
    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public String create(@ModelAttribute Provider provider, @RequestParam("img") MultipartFile archivo) throws MiException {
        long maxFileSize = 5242880; //eric: 5MB es el limite a guardar, puede ser modificado
        Image image;
        try {
            if(!archivo.isEmpty() && archivo.getSize() < maxFileSize){
                image = imageConverter.convert(archivo);
                imageService.Save(image);
            } else {
                image = imageService.GetByName("provider-avatar.png");
                imageService.Save(image);
            }
            providerService.createProvider(provider, image);
        } catch (Exception e){
            throw new MiException("EL ERROR SE ENCUENTRA EN EL POST: " + e);
        }

        return "redirect:/home";
    }
    @GetMapping("/list")
    public String getAll(ModelMap model) throws Exception {
        model.addAttribute("searchReturn",providerService.getAll());
        return "index.html";
    }
    //codigo de eric optimizado usando IA
    @GetMapping("/search")
    public String search(@RequestParam String location, @RequestParam String profession, ModelMap model) throws Exception {
        List<Provider> searchReturn;
        if (!location.isEmpty() && !profession.isEmpty()) {
            searchReturn = providerService.searchLocationAndProfession(location, profession);
        } else {
            searchReturn = switch (!location.isEmpty() ? 1 : !profession.isEmpty() ? 2 : 0) {
                case 1 -> providerService.searchLocation(location);
                case 2 -> providerService.searchProfession(profession);
                default -> providerService.getAll();
            };
        }
        model.addAttribute("searchReturn", searchReturn);
        return "Providers";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") String id, ModelMap model) throws MiException{
        try {
            providerService.deleteProvider(id);
        
            return "index.html";
        } catch (Exception e) {
            throw new MiException("Provider no encontrado!");
        }
        
    }

}
