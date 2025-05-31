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

import gtics.lab8_20223209.Entity.CrewMember;
import gtics.lab8_20223209.repository.CrewMemberRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/crew")
public class CrewMemberController {
    @Autowired
    CrewMemberRepository crewMemberRepository;

    @GetMapping("")
    public String listCrew(Model model) {
        model.addAttribute("crewList", crewMemberRepository.findAll());
        return "crew_list";
    }

    @GetMapping("/new")
    public String newCrewForm(Model model) {
        model.addAttribute("crewMember", new CrewMember());
        return "crew_form";
    }

    @PostMapping("/save")
    public String saveCrew(@ModelAttribute("crewMember") @Valid CrewMember crewMember, BindingResult result) {
        if (result.hasErrors()) {
            return "crew_form";
        }
        crewMemberRepository.save(crewMember);
        return "redirect:/crew";
    }

    @GetMapping("/edit/{id}")
    public String editCrew(@PathVariable("id") Long id, Model model) {
        model.addAttribute("crewMember", crewMemberRepository.findById(id).orElse(null));
        return "crew_form";
    }

    @GetMapping("/delete/{id}")
    public String deleteCrew(@PathVariable("id") Long id) {
        crewMemberRepository.deleteById(id);
        return "redirect:/crew";
    }

    @GetMapping("/details/{id}")
    public String detailsCrew(@PathVariable("id") Long id, Model model) {
        model.addAttribute("crewMember", crewMemberRepository.findById(id).orElse(null));
        return "crew_details";
    }
}
