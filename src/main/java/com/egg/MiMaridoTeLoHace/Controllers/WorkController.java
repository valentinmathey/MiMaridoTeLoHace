package com.egg.MiMaridoTeLoHace.Controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.MiMaridoTeLoHace.Services.WorkService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.egg.MiMaridoTeLoHace.Entities.User;
import com.egg.MiMaridoTeLoHace.Entities.Work;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Repositories.UserRepository;

@Controller
@RequestMapping("/work")
public class WorkController {

    @Autowired
    WorkService workService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/create")
    public String create(@RequestParam(value = "applicationType", required = false) String applicationType, @RequestParam(value="idProvider") String idProvider, User user, ModelMap model) {
        model.addAttribute("applicationType", applicationType);
        model.addAttribute("idProvider", idProvider);
        model.addAttribute("user", user);
        model.addAttribute("work", new Work());
        return "registerWork";
    }

    @PostMapping("/create")
    public String createCheck(@ModelAttribute Work work, @RequestParam("idProvider") String idProvider, HttpSession customerSession) throws MiException {
        
        User user = (User) customerSession.getAttribute("userSession");
        Optional<User> customer = userRepository.findById(user.getId());
        work.setUserCustomerId(customer.get());

        Optional<User> provider = userRepository.findById(idProvider);
        work.setUserProviderId(provider.get());
        
        workService.createWork(work);
        
        return "redirect:/home";
    }

    @GetMapping("/works") // mostrar el trabajo
    public String showWorks(@RequestParam(value = "id", required = false) String id, ModelMap model) {
        //model.addAttribute("work", workService.getById(id));
        return "worksUser"; 
    }

    @PostMapping("/work/mod") // editarlo
    public String editWorkReview(@RequestParam("id") String id, ModelMap model) {
        // tanto los cambios realizados por el provider(start, finish) como los del
        // customer(rating, review) son realizados en editReview
        // seguramente se ajuste al traer datos del front
        workService.editReview(workService.getById(id));
        return "redirect:/home"; // el html todavio no existe?
    }

    @PostMapping("/work/del") // borrarlo
    public String deleteWorkReview(@RequestParam("id") String id) {
        workService.delete(id);
        return "redirect:/home";
    }
}
