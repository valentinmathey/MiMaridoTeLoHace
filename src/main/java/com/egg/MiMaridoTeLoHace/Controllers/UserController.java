package com.egg.MiMaridoTeLoHace.Controllers;

import com.egg.MiMaridoTeLoHace.Entities.Image;
import com.egg.MiMaridoTeLoHace.Entities.User;
import com.egg.MiMaridoTeLoHace.Entities.Work;
import com.egg.MiMaridoTeLoHace.Enums.Professions;
import com.egg.MiMaridoTeLoHace.Enums.Roles;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Repositories.WorkRepository;
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
    @Autowired
    WorkRepository workRepository;

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
            model.addAttribute("mssg", "El email ingresado ya se encuentra registrado 🚫");
            model.addAttribute("professions", Professions.values());

            return "registerUser";
        }

    }

    @GetMapping("/perfil/{id}")
    public String user(@PathVariable("id") String id, ModelMap model, HttpSession session) throws MiException {
        
        User user = userService.getById(id);
        model.addAttribute("user", user);

        String check = "";
        List<Work> listReviews = workRepository.getWorkByUserProvider(user);
        if (listReviews.size() == 0) {
            check = "false";
        }
        model.addAttribute("check", check);
        model.addAttribute("listReviews", listReviews);

        User sessionUser = (User) session.getAttribute("userSession");
        if (user != null && sessionUser != null
                && (user.getId().equals(sessionUser.getId()) || sessionUser.getRole().equals(Roles.ADMIN))) {
            model.addAttribute("professions", Professions.values());
            return "myProfile";

        } else if (sessionUser != null && sessionUser.getRole().equals(Roles.CUSTOMER)) {
            return "otherProfile";
        } else if (sessionUser != null && sessionUser.getRole().equals(Roles.PROVIDER)) {
            return "redirect:/home";
        } else {
            return "redirect:/user/register";
        }

    }

    @Transactional
    @GetMapping(value = "/perfil/{id}/review")
    public String deleteReview(@PathVariable("id") String id, Model model) throws MiException{

        User user = userService.getById(id);
        model.addAttribute("user", user);

        String check = "";
        List<Work> listReviews = workRepository.getWorkByUserProvider(user);
        if (listReviews.size() == 0) {
            check = "false";
        }
        model.addAttribute("check", check);
        model.addAttribute("listReviews", listReviews);

        return "otherProfile";
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
                    session.setAttribute("userSession", userService.modifyUser(id, user, image, false));
                } else { // eric: solo modifica el user
                    userService.modifyUser(id, user, image, false);
                }
                if (sessionUser.getRole().equals(Roles.ADMIN)) {
                    return "redirect:/admin/dashboard";
                }
            }
        } catch (MiException e) {
            e.printStackTrace();
        }
        return "redirect:/home";
    }

    @Transactional
    @PostMapping(value = "/perfil/{id}/change", consumes = "multipart/form-data")
    public String editModify(@PathVariable("id") String id, @ModelAttribute User user,
            @RequestParam("img") MultipartFile archivo, ModelMap model, HttpSession session) throws MiException, IOException {

        try {
            Image defaultImage = imageService.GetByName("customer-avatar.png");
            User sessionUser = (User) session.getAttribute("userSession");
            if (user != null && sessionUser != null && (user.getId().equals(sessionUser.getId()))) {
                Image image = null;
                if (!archivo.isEmpty()) {
                    image = imageConverter.convert(archivo);
                }

                if(imageService.GetById(sessionUser.getImage()).getName().equals(defaultImage.getName())){
                    image = imageService.GetByName("provider-avatar.png");
                }
                
                sessionUser = userService.modifyUser(id, user, image, true);
                session.setAttribute("userSession", sessionUser);
            }
        } catch (MiException e) {
            e.printStackTrace();
        }
        return "redirect:/logout";
    }

    @Transactional
    @GetMapping("/perfil/{id}/role")
    public String editRole(@PathVariable("id") String id, ModelMap model, HttpSession session) throws MiException {
        User user = userService.getById(id);
        User sessionUser = (User) session.getAttribute("userSession");
        if (sessionUser != null && sessionUser.getRole().equals(Roles.ADMIN)) {
            userService.updateRole(user);
        }
        return "redirect:/admin/dashboard";
    }

    @Transactional
    @GetMapping("/perfil/{id}/alta")
    public String editAlta(@PathVariable("id") String id, ModelMap model, HttpSession session) throws MiException {
        User user = userService.getById(id);
        User sessionUser = (User) session.getAttribute("userSession");
        if (sessionUser != null && sessionUser.getRole().equals(Roles.ADMIN)) {
            userService.updateaAlta(user);
        }
        return "redirect:/admin/dashboard";
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
