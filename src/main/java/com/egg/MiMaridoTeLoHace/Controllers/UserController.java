package com.egg.MiMaridoTeLoHace.Controllers;

import com.egg.MiMaridoTeLoHace.Entities.Image;
import com.egg.MiMaridoTeLoHace.Entities.User;
import com.egg.MiMaridoTeLoHace.Services.ImageService;
import com.egg.MiMaridoTeLoHace.Services.UserService;
import com.egg.MiMaridoTeLoHace.converters.ImageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ImageService imageService;
    @Autowired
    ImageConverter imageConverter;
    @GetMapping("/register")
    public String create(ModelMap model){
        model.addAttribute("newUser", new User());
        return "FormCustomer";//nombre provicional
    }
    @Transactional
    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public String createCheck(@RequestBody User user, @RequestParam("password2") String password, @RequestParam("img") MultipartFile archivo, ModelMap model) throws IOException {
        int confirmaciones = 0;
        User userModel = user;

        //si vamos a usar imagenes se descomenta esto
//        long maxFileSize = 5242880; //eric: 5MB es el limite a guardar, puede ser modificado
        Image image;
//        if(!archivo.isEmpty() && archivo.getSize() < maxFileSize){
//            image = imageConverter.convert(archivo);
//            imageService.Save(image);
//        } else {
            //eric: si la imagen al crear la cuenta siempre es default con esto alcansa
            image = imageService.GetByName("customer-avatar.png");
            imageService.Save(image);
//        }


        String errorA = "", errorB = "";
        //validacion de email
        if (userService.searchByEmail(user.getEmail()) == null){
            confirmaciones++;
        } else {
            errorA = "El Mail ya esta registrado";
            userModel.setEmail("");
        }
        //validacion de contraceña
        if(user.getPassword().equals(password)){
            confirmaciones++;
        } else {
            errorB = "Las Contraseñas no coinciden";
            userModel.setPassword("");
        }

        if(confirmaciones == 2){
            // crear usuario (se envia con image para asignarsele el id en service)
            userService.create(userModel, image);
            return "redirect:/home";
        } else {
            model.addAttribute("Exeption", errorA + "\n" + errorB);
        }
        model.addAttribute("newUser", userModel);
        return "redirect:/register";
    }

    @GetMapping("/perfil/id")
    public String user(@PathVariable("id") String id, ModelMap model){
        User user = userService.getById(id);
        if(user != null){
            model.addAttribute("user", user);
            return "User";
        } else {
            model.addAttribute("Exeption", "Usuario no encontrado");
        }
        return "redirect:/";

    }

    @Transactional
    @PutMapping("/perfil/id")
    public String edit(@PathVariable("id") String id, @RequestBody User user, ModelMap model){
        //hacer
        try {
            userService.modify(id, user);
            model.addAttribute("OK", "el usuario fue modificado con exito");
        } catch (Exception e) {
            model.addAttribute("Exeption", "Error al modificar el usuario");
        }
        return "redirect:/#";
    }

    @Transactional
    @DeleteMapping("/perfil/id")
    public String delete(@PathVariable("id") String id, ModelMap model){
        //se podrian agregar mas controles a futuro
        userService.delete(id);
        model.addAttribute("OK", "el usuario fue eliminado con exito");
        return "redirect:/";
    }
}
