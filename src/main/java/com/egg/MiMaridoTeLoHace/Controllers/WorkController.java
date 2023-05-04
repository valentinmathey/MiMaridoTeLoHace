package com.egg.MiMaridoTeLoHace.Controllers;

import java.util.List;
import java.util.Optional;

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

import com.egg.MiMaridoTeLoHace.Entities.User;
import com.egg.MiMaridoTeLoHace.Entities.Work;
import com.egg.MiMaridoTeLoHace.Exceptions.MiException;
import com.egg.MiMaridoTeLoHace.Repositories.UserRepository;
import com.egg.MiMaridoTeLoHace.Repositories.WorkRepository;

@Controller
@RequestMapping("/work")
public class WorkController {

    @Autowired
    WorkService workService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    WorkRepository workRepository;

    @GetMapping("/create")
    public String create(@RequestParam(value = "applicationType", required = false) String applicationType,
            @RequestParam(value = "idProvider") String idProvider, User user, ModelMap model) {
        model.addAttribute("applicationType", applicationType);
        model.addAttribute("idProvider", idProvider);
        model.addAttribute("work", new Work());
        return "registerWork";
    }

    @PostMapping("/create")
    public String createCheck(@ModelAttribute Work work, @RequestParam("idProvider") String idProvider,
            HttpSession customerSession) throws MiException {

        User user = (User) customerSession.getAttribute("userSession");
        Optional<User> customer = userRepository.findById(user.getId());
        work.setUserCustomerId(customer.get());

        Optional<User> provider = userRepository.findById(idProvider);
        work.setUserProviderId(provider.get());

        workService.createWork(work);

        return "redirect:/home";
    }

    @GetMapping("/works")
    public String showWorks(HttpSession session, ModelMap model) {

        User user = (User) session.getAttribute("userSession");
        Optional<User> userSearch = userRepository.findById(user.getId());

        if (user.getRole().toString().equals("PROVIDER")) {
        List<Work> providersWorkList = workRepository.getWorkByUserProvider(userSearch.get());
        model.addAttribute("providerWorkList", providersWorkList);
        return "worksUser";
        } else if (user.getRole().toString().equals("CUSTOMER")) {

        List<Work> customersWorkList = workRepository.getWorkByUserCustomer(userSearch.get());
        model.addAttribute("customerWorkList", customersWorkList);
        return "worksUser";
        }
        return null;
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
