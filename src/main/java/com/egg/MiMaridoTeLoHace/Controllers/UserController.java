package com.egg.MiMaridoTeLoHace.Controllers;

import com.egg.MiMaridoTeLoHace.Entities.Image;
import com.egg.MiMaridoTeLoHace.Entities.User;
import com.egg.MiMaridoTeLoHace.Enums.Professions;
import com.egg.MiMaridoTeLoHace.Enums.Roles;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Services.ImageService;
import com.egg.MiMaridoTeLoHace.Services.UserService;
import com.egg.MiMaridoTeLoHace.converters.ImageConverter;

import ch.qos.logback.core.joran.conditional.ElseAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
    public String user(@RequestParam("role") String role, Model model) {

        model.addAttribute("role", role);
        model.addAttribute("user", new User());
        model.addAttribute("professions", Professions.values());

        return "newFormUser";
    }

    @PostMapping("/register")
    public String userRegister(@ModelAttribute User user) throws MiException {

        userService.createUser(user);

        return "home";
    }

    @GetMapping("/select")
    public String userSelect() {
        return "userSelect";
    }

    @GetMapping("/perfil/{id}")
    public String user(@PathVariable("id") String id, ModelMap model) throws MiException {
        User user = userService.getById(id);
        if(user != null){
            model.addAttribute("usuarioActual", user);
            model.addAttribute("professions", Professions.values());
            return "myProfile";
        } else {
            model.addAttribute("Exeption", "Usuario no encontrado");
        }
        return "redirect:/user";

    }
    @Transactional
    @PostMapping("/perfil/{id}/mod")
    public String edit(@PathVariable("id") String id, @ModelAttribute User user, ModelMap model){
        try {
            userService.modifyUser(id, user);
            model.addAttribute("OK", "el usuario fue modificado con exito");
        } catch (Exception e) {
            model.addAttribute("Exeption", "Error al modificar el usuario");
        }
        return "redirect:/#";
    }

    @Transactional
    @PostMapping("/perfil/{id}/del")
    public String delete(@PathVariable("id") String id, ModelMap model) throws MiException {
        //se podrian agregar mas controles a futuro
        userService.deleteUser(id);
        model.addAttribute("OK", "el usuario fue eliminado con exito");
        return "redirect:/";
    }

    @GetMapping("/list")
    public String listUsers(ModelMap modelo) throws MiException {
        List<User> users = userService.userList();
        modelo.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("/providers")
    public String showProviders(@RequestParam String profession, ModelMap model) throws MiException {

        Optional<User> searchReturn = userService.searchByProfession(profession);
        if (searchReturn.isPresent()) {
            model.addAttribute("searchReturn", searchReturn);
        } else {
            model.addAttribute("Exeption", "El Servicio se encuentra sin trabajadores actualmente");
            return "redirect:/";
        }
        return "providers";
    }
}
