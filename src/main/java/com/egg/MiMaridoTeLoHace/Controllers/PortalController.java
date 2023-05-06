package com.egg.MiMaridoTeLoHace.Controllers;

import com.egg.MiMaridoTeLoHace.Entities.User;
import com.egg.MiMaridoTeLoHace.Enums.Professions;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class PortalController {
    @Autowired
    UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_PROVIDER', 'ROLE_ADMIN')")
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {

        User logued = (User) session.getAttribute("userSession");

        if (logued.getRole().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }

        return "home";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {

        if (error != null) {
            String mssg = "Usuario o contraseña invalidos 🚫";
            model.addAttribute("mssg", mssg);
        }

        return "login";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/search")
    public String showProviders(@RequestParam("profession") String profession,
            @RequestParam(name = "st", required = false) String search, ModelMap model)
            throws MiException {

        List<User> searchReturn = null;
        if (profession == "" && search == "") {
            searchReturn = userService.AllProviderAlta();

        } else if (profession == "" && search != "") {
            searchReturn = userService.AllAltaFiltro(search);

        } else if (profession != "" && search == "") {
            for (Professions professions : Professions.values()) {
                if (professions.name().equals(profession)) {
                    System.out.println("hubo coincidencias con la profecion: " + professions.name());
                    searchReturn = userService.ProfessionAlta(professions);
                    break;
                }
            }

        } else if (profession != "" && search != "") {
            for (Professions professions : Professions.values()) {
                if (professions.name().equals(profession)) {
                    searchReturn = userService.AllProfessionAltaFiltro(professions, search);
                    System.out.println("profession: " + professions + "\nsearch: " + search);
                    break;
                }
            }
        }
        model.addAttribute("professions", Professions.values());
        model.addAttribute("searchReturn", searchReturn);
        return "provider";
    }
}
