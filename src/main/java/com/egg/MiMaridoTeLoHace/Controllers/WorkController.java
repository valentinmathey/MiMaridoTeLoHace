package com.egg.MiMaridoTeLoHace.Controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.MiMaridoTeLoHace.Services.UserService;
import com.egg.MiMaridoTeLoHace.Services.WorkService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.egg.MiMaridoTeLoHace.Entities.User;
import com.egg.MiMaridoTeLoHace.Entities.Work;
import com.egg.MiMaridoTeLoHace.Enums.WorkStatus;
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
    @Autowired
    UserService userService;

    @GetMapping("/create")
    public String create(@RequestParam("applicationType") String applicationType,
            @RequestParam(value = "idProvider", required = false) String idProvider,
            @RequestParam(value = "idWork", required = false) String idWork, User user, ModelMap model) {

        if (applicationType.equals("review")) {
            Optional<Work> work = workRepository.findById(idWork);
            if (work.isPresent()) {
                Work workReview = work.get();
                model.addAttribute("workReview", workReview);
                model.addAttribute("applicationType", applicationType);
                model.addAttribute("idWork", workReview.getId());
                return "registerWork";
            }
        } else if (applicationType.equals("work")) {
            model.addAttribute("applicationType", applicationType);
            model.addAttribute("idProvider", idProvider);
            model.addAttribute("work", new Work());
            return "registerWork";
        }
        return null;
    }

    @PostMapping("/create")
    public String createCheck(@ModelAttribute Work work,
            @RequestParam(value = "idProvider", required = false) String idProvider,
            @RequestParam(value = "idWork", required = false) String idWork,
            HttpSession customerSession) throws MiException {

        if (idProvider == null && idWork != null) {
            Optional<Work> workConsult = workRepository.findById(idWork);
            if (workConsult.isPresent()) {
                Work workUpdate = workConsult.get();
                workUpdate.setReview(work.getReview());
                workUpdate.setRatingWork(work.getRatingWork());
                workUpdate.setWorkStatus(WorkStatus.REVIEWD);

                workRepository.save(workUpdate);

                userService.updateRating(workUpdate.getUserProviderId());
            }

            return "redirect:/home";
        } else {
            User user = (User) customerSession.getAttribute("userSession");
            Optional<User> customer = userRepository.findById(user.getId());
            work.setUserCustomerId(customer.get());

            Optional<User> provider = userRepository.findById(idProvider);
            work.setUserProviderId(provider.get());

            workService.createWork(work);

            return "redirect:/home";
        }

    }

    @GetMapping("/worksList")
    public String worksList(@RequestParam(value="idWork", required=false) String id, @RequestParam(value="wStat", required=false) String wStat, HttpSession session, ModelMap model) throws MiException {

        if (id != null && wStat != null) {
            workService.changeWorkStatus(id, wStat);
        }

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
        return "redirect:/home";
    }

    @GetMapping("/deteComment")
    public String deleteComment(@RequestParam (value = "idWork", required = false) String idWork) throws MiException {

        Optional<Work> consult = workRepository.findById(idWork);
        if (consult.isPresent()) {
            Work work = consult.get();
            work.setReview("🚫 Comentario censurado 🚫");
            workRepository.save(work);
        }

        return "dashboard";
    }
}
