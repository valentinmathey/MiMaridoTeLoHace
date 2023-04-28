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
    public String userRegister(@ModelAttribute User user, Model model) throws MiException {

        if (userService.validateEmail(user)==false) {
            userService.createUser(user);
            return "home";
        } else {
            String mssg = "EL EMAIL INGRESADO YA SE ENCUENTRA REGISTRADO";
            model.addAttribute("mssg", mssg);
            return "userSelect";
        }
    }

    @GetMapping("/select")
    public String userSelect() {
        return "userSelect";
    }

    /*
     * @GetMapping("/register")
     * public String create(ModelMap model){
     * model.addAttribute("user", new User());
     * model.addAttribute("customerRole", Roles.CUSTOMER);
     * model.addAttribute("providerRole", Roles.PROVIDER);
     * model.addAttribute("professions", Professions.values());
     * return "formUser";
     * }
     * 
     * @Transactional
     * 
     * @PostMapping(value = "/register", consumes = "multipart/form-data")
     * public String createCheck(@ModelAttribute User
     * user, @RequestParam("password2") String password2, ModelMap model) throws
     * IOException, MiException {
     * int confirmaciones = 0;
     * User userModel = user;
     * String errorA = "", errorB = "", errorC = "";
     * Image image = null;
     * // dependiendo el rol es la imagen de default
     * if(userModel.getRole().name().equals("PROVIDER")){
     * image = imageService.GetByName("provider-avatar.png");
     * } else if(userModel.getRole().name().equals("CUSTOMER")){
     * image = imageService.GetByName("customer-avatar.png");
     * }
     * // }
     * imageService.Save(image);
     * 
     * //minusias para cada clase
     * if (user.getRole().name().equals("CUSTOMER")){
     * user.setProfession(null);
     * }
     * 
     * //validacion de email
     * if (userService.getByEmail(user.getEmail()) == null){
     * confirmaciones++;
     * } else {
     * errorB = "El Mail ya esta registrado";
     * userModel.setEmail("");
     * }
     * //validacion de contraceña
     * if(user.getPassword().equals(password2)){
     * confirmaciones++;
     * } else {
     * errorC = "Las Contraseñas no coinciden";
     * userModel.setPassword("");
     * }
     * 
     * if(confirmaciones == 2){
     * // crear usuario (se envia con image para asignarsele el id en service)
     * userService.createUser(userModel, image);
     * model.addAttribute("OK", "Usuario creado con exito");
     * return "redirect:/home";
     * } else {
     * model.addAttribute("Exeption", errorA + "\n" + errorB + "\n" + errorC);
     * }
     * model.addAttribute("user", userModel);
     * return "redirect:/user/register";
     * }
     */

    @GetMapping("/perfil/id")
    public String user(@PathVariable("id") String id, ModelMap model) throws MiException {
        User user = userService.getById(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "user";
        } else {
            model.addAttribute("Exeption", "Usuario no encontrado");
        }
        return "redirect:/user";

    }

    @Transactional
    @PutMapping("/perfil/id")
    public String edit(@PathVariable("id") String id, @ModelAttribute User user, ModelMap model) {
        // hacer
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
        // se podrian agregar mas controles a futuro
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
