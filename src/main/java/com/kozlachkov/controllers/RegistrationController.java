package com.kozlachkov.controllers;

import com.kozlachkov.dao.PersonDao;
import com.kozlachkov.models.Person;
import com.kozlachkov.models.Roles;
import com.kozlachkov.models.UserDB;
//import com.kozlachkov.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;


@Controller
@RequestMapping("/people")
public class RegistrationController {

    private PersonDao personDao;
    @Autowired
    public RegistrationController(PersonDao personDao) {
        this.personDao = personDao;
    }
/*
    @Autowired
    private UserRepo userRepo;*/

    @GetMapping("/registration")
    public String loginPage(Model model) {
        model.addAttribute("userDB", new UserDB());
        return "people/registration";
    }


    @PostMapping("/new")
    public String addUser(@ModelAttribute("userDB") UserDB userDB, Model model) {
        /*UserDB userFromDB = userRepo.findByUsername(userDB.getUsername());
        if (userFromDB!=null){
            model.put("message", "user with such Username exists");
            return ("/people/registration");
            //если пользователь существует, сообщаем об этом
        }*/
        System.out.println("user should be created");
        personDao.createUser(userDB);
        model.addAttribute("userDB", userDB);
        /*
        userDB.setActive(true);
        userDB.setRoles(Collections.singleton(Roles.USER));//Коллекция с одним значением, т.к. в ENUM одно
        userRepo.save(userDB);*/
        return "redirect:/people/new";
    }

}
