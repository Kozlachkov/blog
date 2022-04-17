package com.kozlachkov.controllers;

import com.kozlachkov.dao.PersonDao;
import com.kozlachkov.models.Person;
import com.kozlachkov.models.Roles;
import com.kozlachkov.models.UserDB;
//import com.kozlachkov.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
    public String create(@ModelAttribute("userDB") UserDB userDB) {
        //personDao.save(person);
        return "redirect:/people/new";
    }

    //@PostMapping("/registration")
   // public String addUser(@ModelAttribute("userDB") UserDB userDB,) {
        /*UserDB userFromDB = userRepo.findByUsername(userDB.getUsername());
        if (userFromDB!=null){
            model.put("message", "user with such Username exists");
            return ("/people/registration");
            //если пользователь существует, сообщаем об этом
        }*/
        //System.out.println("user should be created");
        //personDao.createUser(userDB);
        //model.addAttribute("userDB", userDB);
        /*
        userDB.setActive(true);
        userDB.setRoles(Collections.singleton(Roles.USER));//Коллекция с одним значением, т.к. в ENUM одно
        userRepo.save(userDB);*/
        //return "redirect:/people/new";
    //}

    @GetMapping("/new")
    public String newPerson(Model model/*@ModelAttribute("userDB") UserDB userDB, ModelMap mapl*/) {
        model.addAttribute("person", new Person());
        /*Person person = new Person();
        person.setId(userDB.getId());
        mapl.addAttribute("person", person);
        mapl.addAttribute("userDB", userDB);*/
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/new";

        personDao.save(person);
        return "redirect:/people";
    }

}
