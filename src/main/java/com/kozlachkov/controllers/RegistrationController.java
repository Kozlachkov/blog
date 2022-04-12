package com.kozlachkov.controllers;

import com.kozlachkov.models.Person;
import com.kozlachkov.models.Roles;
import com.kozlachkov.models.UserDB;
import com.kozlachkov.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping("/people")
public class RegistrationController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String loginPage() {
        return "people/registration";
    }

    @PostMapping("/registration")
    public String addUser(UserDB userDB, Map<String, Object> model) {
        UserDB userFromDB = userRepo.fingByUsername(userDB.getUsername());
        if (userFromDB!=null){
            model.put("message", "user exists");
            return ("/people/registration");
            //если пользователь существует, сообщаем об этом
        }

        userDB.setActive(true);
        userDB.setRoles(Collections.singleton(Roles.USER));//Коллекция с одним значением, т.к. в ENUM одно
        userRepo.save(userDB);
        return "redirect:/people/login";
    }

}
