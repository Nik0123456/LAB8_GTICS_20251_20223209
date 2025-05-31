package gtics.lab8_20223209.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gtics.lab8_20223209.Entity.CrewMember;
import gtics.lab8_20223209.Entity.Expedition;
import gtics.lab8_20223209.Entity.ExpeditionCrew;
import gtics.lab8_20223209.Entity.ExpeditionCrewId;
import gtics.lab8_20223209.repository.CrewMemberRepository;
import gtics.lab8_20223209.repository.ExpeditionCrewRepository;
import gtics.lab8_20223209.repository.ExpeditionRepository;
import gtics.lab8_20223209.repository.PlanetRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/expeditions")
public class ExpeditionController {
    @Autowired
    ExpeditionRepository expeditionRepository;
    @Autowired
    PlanetRepository planetRepository;
    @Autowired
    CrewMemberRepository crewMemberRepository;
    @Autowired
    ExpeditionCrewRepository expeditionCrewRepository;

    @GetMapping("/new")
    public String newExpeditionForm(Model model, @ModelAttribute("expedition") Expedition expedition) {
        model.addAttribute("expedition", new Expedition());
        model.addAttribute("planets", planetRepository.findAll());
        model.addAttribute("crewMembers", crewMemberRepository.findAll());
        return "expedition_form";
    }

    @PostMapping("/save")
    public String saveExpedition(@ModelAttribute("expedition") @Valid Expedition expedition, BindingResult result, @RequestParam(required = false) List<Long> crewIds, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("planets", planetRepository.findAll());
            model.addAttribute("crewMembers", crewMemberRepository.findAll());
            return "expedition_form";
        }
        // Validación: Al menos un Piloto y un Científico
        boolean tienePiloto = false, tieneCientifico = false;
        if (crewIds != null) {
            for (Long crewId : crewIds) {
                CrewMember member = crewMemberRepository.findById(crewId).orElse(null);
                if (member != null) {
                    if (member.getEspecialidad() != null && member.getEspecialidad().trim().equalsIgnoreCase("Piloto")) tienePiloto = true;
                    if (member.getEspecialidad() != null && (member.getEspecialidad().trim().equalsIgnoreCase("Cientifico") || member.getEspecialidad().trim().equalsIgnoreCase("Científico"))) tieneCientifico = true;
                }
            }
        }
        if (!tienePiloto || !tieneCientifico) {
            result.rejectValue("estado", null, "Debe asignar al menos un Piloto y un Científico a la expedición.");
            model.addAttribute("planets", planetRepository.findAll());
            model.addAttribute("crewMembers", crewMemberRepository.findAll());
            if (expedition.getId() != null) {
                List<ExpeditionCrew> asignados = expeditionCrewRepository.findByExpeditionId(expedition.getId());
                model.addAttribute("asignados", asignados.stream().map(ec -> ec.getCrewMember().getId()).toList());
            }
            return "expedition_form";
        }
        // Validación: No doble asignación a expediciones activas
        for (Long crewId : crewIds) {
            var asignaciones = expeditionCrewRepository.findActiveAssignmentsByCrewId(crewId);
            boolean yaAsignado = asignaciones.stream().anyMatch(ec -> ec.getExpedition().getId() != null && !ec.getExpedition().getId().equals(expedition.getId()));
            if (yaAsignado) {
                CrewMember member = crewMemberRepository.findById(crewId).orElse(null);
                result.rejectValue("estado", null, "El miembro " + (member != null ? member.getNombreCompleto() : crewId) + " ya está asignado a otra expedición activa.");
                model.addAttribute("planets", planetRepository.findAll());
                model.addAttribute("crewMembers", crewMemberRepository.findAll());
                return "expedition_form";
            }
        }
        // Guardar expedición
        expeditionRepository.save(expedition);
        // Guardar asignaciones de tripulación
        // Primero eliminar asignaciones previas si es edición
        if (expedition.getId() != null) {
            List<ExpeditionCrew> prev = expeditionCrewRepository.findByExpeditionId(expedition.getId());
            expeditionCrewRepository.deleteAll(prev);
        }
        for (Long crewId : crewIds) {
            CrewMember member = crewMemberRepository.findById(crewId).orElse(null);
            if (member != null) {
                ExpeditionCrew ec = new ExpeditionCrew();
                ec.setId(new ExpeditionCrewId(expedition.getId(), crewId));
                ec.setExpedition(expedition);
                ec.setCrewMember(member);
                expeditionCrewRepository.save(ec);
            }
        }
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editExpedition(@PathVariable("id") Long id, Model model) {
        Expedition expedition = expeditionRepository.findById(id).orElse(null);
        model.addAttribute("expedition", expedition);
        model.addAttribute("planets", planetRepository.findAll());
        model.addAttribute("crewMembers", crewMemberRepository.findAll());
        // Preseleccionar miembros asignados
        List<ExpeditionCrew> asignados = expeditionCrewRepository.findByExpeditionId(id);
        model.addAttribute("asignados", asignados.stream().map(ec -> ec.getCrewMember().getId()).toList());
        return "expedition_form";
    }

    @GetMapping("/delete/{id}")
    public String deleteExpedition(@PathVariable("id") Long id) {
        expeditionRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/details/{id}")
    public String detailsExpedition(@PathVariable("id") Long id, Model model) {
        Expedition expedition = expeditionRepository.findById(id).orElse(null);
        model.addAttribute("expedition", expedition);
        List<ExpeditionCrew> asignados = expeditionCrewRepository.findByExpeditionId(id);
        model.addAttribute("asignados", asignados);
        return "expedition_details";
    }
}
