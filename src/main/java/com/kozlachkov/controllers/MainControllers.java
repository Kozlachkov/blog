package com.kozlachkov.controllers;

import com.kozlachkov.dao.PersonDao;
import com.kozlachkov.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/people")
public class MainControllers {

    private PersonDao personDao;
    @Autowired
    public MainControllers(PersonDao personDao) {
        this.personDao = personDao;
    }


    @GetMapping("/{id}") //id из переданных парам-в (продолжение адреса) передастся в метод
    public String show (@PathVariable ("id") int id, Model model){ //id - обрати внимание, целое число
        //получим одного человека из ДАО и передадим его на отображение
        model.addAttribute("person", personDao.getPersonById(id));
        return ("people/show");
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDao.getPersonById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if(bindingResult.hasErrors()) return "people/edit";
        personDao.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete (@PathVariable("id") int id){
        personDao.delete(id);
        return "redirect:/people";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "people/login";
    }
}
