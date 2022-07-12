package com.example.ships.web;

import com.example.ships.models.dto.LoginDTO;
import com.example.ships.models.dto.RegisterDTO;
import com.example.ships.services.UserService;
import com.example.ships.session.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;
    private final LoggedUser loggedUser;

    public UserController(UserService userService, LoggedUser loggedUser) {
        this.userService = userService;
        this.loggedUser = loggedUser;
    }


    @ModelAttribute("registerDTO")
    public RegisterDTO initUserRegisterModel() {
        return new RegisterDTO();
    }

    @ModelAttribute("loginDTO")
    public LoginDTO initUserLoginModel() {
        return new LoginDTO();
    }

    @GetMapping("/users/login")
    public String login() {

        if (loggedUser.getId() > 0) {
            return "redirect:/users/home";
        }

        return "login";
    }


    @GetMapping("/users/register")
    public String register() {


        if (loggedUser.getId() > 0) {
            return "redirect:/users/home";
        }

        return "register";
    }

    @PostMapping("users/register")
    public String register(@Valid RegisterDTO registerDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (loggedUser.getId() > 0) {
            return "redirect:/users/home";
        }


        if (bindingResult.hasErrors() || !this.userService.register(registerDTO)) {
            redirectAttributes.addFlashAttribute("registerDTO", registerDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerDTO",
                    bindingResult);

            return "redirect:/users/register";
        }

        this.userService.register(registerDTO);

        return "redirect:/users/login";
    }

    @PostMapping("/users/login")
    public String login(@Valid LoginDTO loginDTO,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes) {

        if (loggedUser.getId() > 0) {
            return "redirect:/users/home";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("loginDTO", loginDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginDTO",
                    bindingResult);

            return "redirect:/users/login";

        }

        if (!this.userService.login(loginDTO)) {
            redirectAttributes.addFlashAttribute("loginDTO", loginDTO);
            redirectAttributes.addFlashAttribute("badCredentials", true);

            return "redirect:/users/login";

        }

        return "redirect:/users/home";
    }

    @GetMapping("/logout")
    public String logout() {

        if (loggedUser.getId() == 0) {
            return "redirect:/";
        }

        this.userService.logout();

        return "redirect:/";
    }
}















