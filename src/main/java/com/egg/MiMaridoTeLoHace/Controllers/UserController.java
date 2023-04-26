package com.egg.MiMaridoTeLoHace.Controllers;

import com.egg.MiMaridoTeLoHace.Entities.Image;
import com.egg.MiMaridoTeLoHace.Entities.User;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
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
import java.util.List;

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
        model.addAttribute("user", new User());
        return "formUser";
    }
    @Transactional
    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public String createCheck(@ModelAttribute User user, @RequestParam("password2") String password2, @RequestParam("img") MultipartFile archivo, ModelMap model) throws IOException, MiException {
        int confirmaciones = 0;
        User userModel = user;
        String errorA = "", errorB = "", errorC = "";

        //si vamos a usar imagenes se descomenta esto
        Image image = null;
        long maxFileSize = 5242880; //eric: 5MB es el limite a guardar, puede ser modificado
        if(!archivo.isEmpty() && archivo.getSize() < maxFileSize){
            image = imageConverter.convert(archivo);
        }
        else {
            errorA = "Error la imagen es demasiado pesada limite 5MB, se le asignara una de default";
            //si salta que solo dejamos registrar con default usar esto
            // dependiendo el rol es la imagen de default
            if(userModel.getRole().name().equals("PROVIDER")){
                image = imageService.GetByName("provider-avatar.png");
            } else if(userModel.getRole().name().equals("CUSTOMER")){
                image = imageService.GetByName("customer-avatar.png");
            }
        }
        imageService.Save(image);


        //validacion de email
        if (userService.getByEmail(user.getEmail()) == null){
            confirmaciones++;
        } else {
            errorB = "El Mail ya esta registrado";
            userModel.setEmail("");
        }
        //validacion de contraceña
        if(user.getPassword().equals(password2)){
            confirmaciones++;
        } else {
            errorC = "Las Contraseñas no coinciden";
            userModel.setPassword("");
        }

        if(confirmaciones == 2){
            // crear usuario (se envia con image para asignarsele el id en service)
            userService.createUser(userModel, image);
            model.addAttribute("OK", "Usuario creado con exito");
            return "redirect:/home";
        } else {
            model.addAttribute("Exeption", errorA + "\n" + errorB + "\n" + errorC);
        }
        model.addAttribute("user", userModel);
        return "redirect:/user/register";
    }

    @GetMapping("/perfil/id")
    public String user(@PathVariable("id") String id, ModelMap model) throws MiException {
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
    public String edit(@PathVariable("id") String id, @ModelAttribute User user, ModelMap model){
        //hacer
        try {
            userService.modifyUser(id, user);
            model.addAttribute("OK", "el usuario fue modificado con exito");
        } catch (Exception e) {
            model.addAttribute("Exeption", "Error al modificar el usuario");
        }
        return "redirect:/#";
    }

    @Transactional
    @DeleteMapping("/perfil/id")
    public String delete(@PathVariable("id") String id, ModelMap model) throws MiException {
        //se podrian agregar mas controles a futuro
        userService.deleteUser(id);
        model.addAttribute("OK", "el usuario fue eliminado con exito");
        return "redirect:/";
    }

    @GetMapping("/list")
    public String listUsers(ModelMap modelo) throws MiException{
        
        List <User> users = userService.userList();
        
        modelo.addAttribute("users", users);
        
        return "userList";
    }
}
