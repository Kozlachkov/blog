package com.kozlachkov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/people")
public class LoginController {

    @GetMapping("/login")
    public String loginPage(Map<String, Object> message4) {
        message4.put("message4", "вы залогинены");
        return "/people";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "/people";
    }

}
