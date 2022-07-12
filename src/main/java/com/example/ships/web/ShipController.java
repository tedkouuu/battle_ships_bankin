package com.example.ships.web;

import com.example.ships.models.dto.CreateShipDTO;
import com.example.ships.models.dto.StartBattleDTO;
import com.example.ships.services.ShipService;
import com.example.ships.session.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ShipController {

    private final ShipService shipService;
    private final LoggedUser loggedUser;

    public ShipController(ShipService shipService, LoggedUser loggedUser) {
        this.shipService = shipService;
        this.loggedUser = loggedUser;
    }

    @ModelAttribute("createShipDTO")
    public CreateShipDTO initCreateShipDTO() {
        return new CreateShipDTO();
    }

    @GetMapping("/ships/add")
    public String ships() {

        if (loggedUser.getId() == 0) {
            return "redirect:/";
        }

        return "ship-add";
    }

    @PostMapping("/ships/add")
    public String ships(@Valid CreateShipDTO createShipDTO,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes) {

        if (loggedUser.getId() == 0) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors() || !this.shipService.create(createShipDTO)) {
            redirectAttributes.addFlashAttribute("createShipDTO", createShipDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.createShipDTO",
                    bindingResult);

            return "redirect:/ships/add";
        }

        this.shipService.create(createShipDTO);

        return "redirect:/users/home";
    }

    @PostMapping("/ships/battle")
    public String battle(@Valid StartBattleDTO startBattleDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        return "redirect:/users/home";
    }

}
