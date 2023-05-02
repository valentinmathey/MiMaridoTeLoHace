package com.egg.MiMaridoTeLoHace.Controllers;

import com.egg.MiMaridoTeLoHace.Entities.Image;
import com.egg.MiMaridoTeLoHace.Entities.User;
import com.egg.MiMaridoTeLoHace.Enums.Professions;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Services.ImageService;
import com.egg.MiMaridoTeLoHace.Services.UserService;
import com.egg.MiMaridoTeLoHace.converters.ImageConverter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
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
    public String user(Model model) {

        model.addAttribute("user", new User());
        model.addAttribute("professions", Professions.values());

        return "formUser";
    }

    @PostMapping("/register")
    public String userRegister(@ModelAttribute User user, Model model) throws MiException {
        //eric: simplificado
        if (!userService.validateEmail(user)) {
            userService.createUser(user);
            return "redirect:/login";
        } else {
            model.addAttribute("mssg", "EL EMAIL INGRESADO YA SE ENCUENTRA REGISTRADO");
            return "formUser";
        }

    }

    @GetMapping("/perfil/{id}")
    public String user(@PathVariable("id") String id, ModelMap model, HttpSession session) throws MiException {
        User user = userService.getById(id);
        if(user != null){
            model.addAttribute("usuarioActual", user);
            if (session.getAttribute("userSession") == null || !session.getAttribute("userSession").equals(user)){
                return "otherProfile";
            } else if (session.getAttribute("userSession").equals(userService.getById(id))) {
                model.addAttribute("professions", Professions.values());
                return "myProfile";
            }
        } else {
            model.addAttribute("mssg", "Usuario no encontrado");
        }
        return "redirect:/";

    }
    @Transactional
    @PostMapping(value = "/perfil/{id}/mod", consumes = "multipart/form-data")
    public String edit(@PathVariable("id") String id, @ModelAttribute User user,
                       @RequestParam("img") MultipartFile archivo, ModelMap model, HttpSession session) throws MiException {

        //hasta ahora no importaba si estaba iniciada la sesion igual se podia editar
        if (session.getAttribute("userSession") == null || !session.getAttribute("userSession").equals(userService.getById(id))){
            model.addAttribute("mssg", "no puede alterar una cuenta sin iniciar la sesion");
        } else if (session.getAttribute("userSession").equals(userService.getById(id))){
            try {
                Image image = null;
                if (!archivo.isEmpty()) {
                    image = imageConverter.convert(archivo);
                }
                session.setAttribute("userSession", userService.modifyUser(id, user, image));

                model.addAttribute("mssg", "Usuario editado con exito");
            } catch (MiException e) {
                e.printStackTrace();
            }
            return "redirect:#";
        }
        return "redirect:/";
    }

    @Transactional
    @PostMapping("/perfil/{id}/del")
    public String delete(@PathVariable("id") String id, ModelMap model, HttpSession session) throws MiException {
        //se podrian agregar mas controles a futuro
        if (session.getAttribute("userSession") == null || !session.getAttribute("userSession").equals(userService.getById(id))){
            model.addAttribute("mssg", "no puede alterar una cuenta sin iniciar la sesion");
        } else if (session.getAttribute("userSession").equals(userService.getById(id))){
            userService.deleteUser(id);
            model.addAttribute("mssg", "el usuario fue eliminado con exito");
            return "redirect:/logout";
        }
        return "redirect:/";
    }

    @GetMapping("/list")
    public String listUsers(ModelMap modelo) throws MiException {
        List<User> users = userService.userList();
        modelo.addAttribute("users", users);
        return "userList";
    }
}
