package gtics.lab8_20223209.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import gtics.lab8_20223209.repository.CrewMemberRepository;
import gtics.lab8_20223209.repository.ExpeditionRepository;
import gtics.lab8_20223209.repository.PlanetRepository;

@Controller
public class MainController {
    @Autowired
    ExpeditionRepository expeditionRepository;
    @Autowired
    PlanetRepository planetRepository;
    @Autowired
    CrewMemberRepository crewMemberRepository;

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("expeditionList", expeditionRepository.findAll());
        model.addAttribute("planetList", planetRepository.findAll());
        return "index";
    }
}
