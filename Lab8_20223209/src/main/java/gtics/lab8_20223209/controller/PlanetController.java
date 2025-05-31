package gtics.lab8_20223209.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import gtics.lab8_20223209.Entity.Planet;
import gtics.lab8_20223209.repository.PlanetRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/planets")
public class PlanetController {
    @Autowired
    PlanetRepository planetRepository;

    @GetMapping("/new")
    public String newPlanetForm(Model model) {
        model.addAttribute("planet", new Planet());
        return "planet_form";
    }

    @PostMapping("/save")
    public String savePlanet(@ModelAttribute("planet") @Valid Planet planet, BindingResult result) {
        if (result.hasErrors()) {
            return "planet_form";
        }
        planetRepository.save(planet);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editPlanet(@PathVariable("id") Long id, Model model) {
        model.addAttribute("planet", planetRepository.findById(id).orElse(null));
        return "planet_form";
    }

    @GetMapping("/delete/{id}")
    public String deletePlanet(@PathVariable("id") Long id) {
        planetRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/details/{id}")
    public String detailsPlanet(@PathVariable("id") Long id, Model model) {
        model.addAttribute("planet", planetRepository.findById(id).orElse(null));
        return "planet_details";
    }
}
