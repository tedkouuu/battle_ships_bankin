package com.example.ships.web;

import com.example.ships.models.dto.ShipDTO;
import com.example.ships.services.ShipService;
import com.example.ships.session.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final ShipService shipService;
    private final LoggedUser loggedUser;

    public HomeController(ShipService shipService, LoggedUser loggedUser) {
        this.shipService = shipService;
        this.loggedUser = loggedUser;
    }

    @GetMapping("/")
    public String loggedOutIndex() {


        if (loggedUser.getId() > 0) {
            return "redirect:/users/home";
        }

        return "index";
    }

    @GetMapping("/users/home")
    public String loggedInIndex(Model model) {

        if (loggedUser.getId() == 0) {
            return "redirect:/";
        }

        List<ShipDTO> ownShips = this.shipService.getOwnedShips(this.loggedUser.getId());

        List<ShipDTO> enemyShips = this.shipService.getEnemyShips(this.loggedUser.getId());

        List<ShipDTO> sortedShips = this.shipService.getAllShipsSorted();

        model.addAttribute("ownShips", ownShips);
        model.addAttribute("enemyShips", enemyShips);
        model.addAttribute("sortedShips", sortedShips);


        return "home";
    }
}





















