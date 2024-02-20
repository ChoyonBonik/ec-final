package com.ecfingerprint.ecfingerprint.controller;

import com.ecfingerprint.ecfingerprint.entity.Info;
import com.ecfingerprint.ecfingerprint.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebMvc
@Controller
public class LoginController {


    @RequestMapping ("/")
    public String homePage() {
        return "home";
    }

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String passcode) {
        if (loginService.isValidUser(username, passcode)) {
            return "verify"; // Redirect to a success page
        } else {
            return "redirect:/login?error=true"; // Redirect to login page with an error parameter
        }
    }

}
