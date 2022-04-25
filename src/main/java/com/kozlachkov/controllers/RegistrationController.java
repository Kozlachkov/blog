package com.kozlachkov.controllers;

import com.kozlachkov.dao.PersonDao;
import com.kozlachkov.models.Person;
import com.kozlachkov.models.Roles;
import com.kozlachkov.models.UserDB;
//import com.kozlachkov.repos.UserRepo;
import com.kozlachkov.models.WebPost;
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

    private UserDB currentUser;
/*
    @Autowired
    private UserRepo userRepo;*/

    @GetMapping()
    public  String index(ModelMap modelMap, Map<String, Object> message3){
        //если в таблице person есть хоть одна запись
        if (currentUser!=null && !personDao.PersonIsActive(currentUser)){
            return "redirect:new";
        }
        modelMap.addAttribute("people", personDao.index());
        modelMap.addAttribute("usersDB", personDao.indexUserDB());
        if (currentUser!=null)  message3.put("message3", currentUser.getUsername());
        else message3.put("message3", "нет залогиненного пользователя");
        return ("/people/index");
    }

    @GetMapping("/registration")
    public String loginPage(Model model) {
        model.addAttribute("userDB", new UserDB());
        return "/people/registration";
    }

    @PostMapping("/new")  //Постим в БД Ник, переходим к вводу данных: имя, мыло..
    public String createNik(@ModelAttribute("userDB") @Valid UserDB userDB, BindingResult bindingResult,
                         Map<String, Object> model, Model model2) {
        if (bindingResult.hasErrors())
            return "/people/registration";
        if (!userDB.getPassword().equals(userDB.getCheck_pass())) {
            model.put("message", "Повторный пароль не совпадает с оригиналом");
            return "/people/registration";
        }
        if (personDao.getUsrByName(userDB.getUsername())!=null){
            model.put("message", "Юзер с таким ником уже существует");
            return "/people/registration";
        }
        currentUser = personDao.createUser(userDB);
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
        /*
        userDB.setActive(true);
        userDB.setRoles(Collections.singleton(Roles.USER));//Коллекция с одним значением, т.к. в ENUM одно
        userRepo.save(userDB);*/
        //return "redirect:/people/new";
    //}

    @GetMapping("/new")
    public String createNewPerson(Model model, Map<String, Object> message1) {
        Person person = new Person();
        person.setId(currentUser.getId());
        model.addAttribute("person", person);
        message1.put("message1", currentUser.getUsername());
        return "/people/new";
    }

    @PostMapping()
    public String createNewPerson(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/people/new";

        personDao.save(person, currentUser);
        return "redirect:/people";
    }

    @GetMapping("/removeNik")
    public String deleteNik (){
        personDao.deleteRegisteredNik(currentUser.getId());
        currentUser = null;
        return "redirect:/people";
    }

    @GetMapping("/test")
    public String testPage (/*ModelMap modelMap2*/){
        /*modelMap2.addAttribute("person",new Person());
        modelMap2.addAttribute("userDB", new UserDB());
        modelMap2.addAttribute("post1", new WebPost());*/
        return "people/test";
    }
}
