package com.egg.MiMaridoTeLoHace.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.MiMaridoTeLoHace.Services.WorkService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.egg.MiMaridoTeLoHace.Entities.Work;

@Controller
@RequestMapping("/work")
public class WorkController {

    @Autowired
    WorkService workService;

    @GetMapping("/create")
    public String create(@RequestParam(value = "applicationType", required = false) String applicationType, ModelMap model) {
        model.addAttribute("applicationType", applicationType);
        model.addAttribute("work", new Work());
        return "registerWork";
    }

    @PostMapping("/create")
    public String createCheck(@ModelAttribute Work work) {
        // eric: como se valida que no viaje vacio en el fron no es requerida una
        // validacion nueva
        workService.createWork(work);
        return "redirec:/home";
    }

    @GetMapping("/work") // mostrar el trabajo
    public String showWork(@RequestParam("id") String id, ModelMap model) {
        model.addAttribute("work", workService.getById(id));
        return "work.html"; // el html todavio no existe?
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
