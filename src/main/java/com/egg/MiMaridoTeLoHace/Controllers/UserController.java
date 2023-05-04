package com.egg.MiMaridoTeLoHace.Controllers;

import com.egg.MiMaridoTeLoHace.Entities.Image;
import com.egg.MiMaridoTeLoHace.Entities.User;
import com.egg.MiMaridoTeLoHace.Enums.Professions;
import com.egg.MiMaridoTeLoHace.Enums.Roles;
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

        return "registerUser";
    }

    @PostMapping("/register")
    public String userRegister(@ModelAttribute User user, Model model) throws MiException {
        // eric: simplificado
        if (!userService.validateEmail(user)) {
            userService.createUser(user);
            return "redirect:/login";
        } else {
            model.addAttribute("mssg", "EL EMAIL INGRESADO YA SE ENCUENTRA REGISTRADO");
            model.addAttribute("professions", Professions.values());

            return "registerUser";
        }

    }

    @GetMapping("/perfil/{id}")
    public String user(@PathVariable("id") String id, ModelMap model, HttpSession session) throws MiException {
        User user = userService.getById(id);
        model.addAttribute("usuarioActual", user);

        User sessionUser = (User) session.getAttribute("userSession");
        if (user != null && sessionUser != null
                && (user.getId().equals(sessionUser.getId()) || sessionUser.getRole().equals(Roles.ADMIN))) {
            model.addAttribute("professions", Professions.values());
            return "myProfile";

        } else {
            return "otherProfile";
        }
    }

    @Transactional
    @PostMapping(value = "/perfil/{id}/mod", consumes = "multipart/form-data")
    public String edit(@PathVariable("id") String id, @ModelAttribute User user,
            @RequestParam("img") MultipartFile archivo, ModelMap model, HttpSession session) throws MiException {

        try {
            User sessionUser = (User) session.getAttribute("userSession");
            if (user != null && sessionUser != null
                    && (user.getId().equals(sessionUser.getId()) || sessionUser.getRole().equals(Roles.ADMIN))) {

                Image image = null;
                if (!archivo.isEmpty()) {
                    image = imageConverter.convert(archivo);
                }

                if (user.getId().equals(sessionUser.getId())) {
                    session.setAttribute("userSession", userService.modifyUser(id, user, image));
                } else { // eric: solo modifica el user
                    userService.modifyUser(id, user, image);
                }
            }
        } catch (MiException e) {
            e.printStackTrace();
        }
        return "redirect:/#";
    }

    @Transactional
    @PostMapping("/perfil/{id}/del")
    public String delete(@PathVariable("id") String id, ModelMap model, HttpSession session) throws MiException {
        // se podrian agregar mas controles a futuro
        User user = userService.getById(id);

        User sessionUser = (User) session.getAttribute("userSession");
        if (user != null && sessionUser != null
                && (user.getId().equals(sessionUser.getId()) || sessionUser.getRole().equals(Roles.ADMIN))) {
            userService.deleteUser(id);
            if (user.getId().equals(sessionUser.getId())) { // eric: solo deslogea si el session es igual al user
                return "redirect:/logout";
            } else if (sessionUser.getRole().equals(Roles.ADMIN)) { // eric: si es admin lo redirecciona a dashboard
                return "redirect:/admin/dashboard";
            }
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
