package com.kozlachkov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/people")
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "/people";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "/people";
    }

}
