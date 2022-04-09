package com.kozlachkov.controllers;

import com.kozlachkov.dao.PersonDao;
import com.kozlachkov.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleControllers {

    private PersonDao personDao;
    @Autowired
    public PeopleControllers(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GetMapping() //выше есть "/people"
    public  String index(Model model){
        //получим всех людей из ДАО и отправим их в отображение.
        model.addAttribute("people", personDao.index());
        return ("people/index"); //папка где лежит
    }

    @GetMapping("/{id}") //id из переданных парам-в (продолжение адреса) передастся в метод
    public String show (@PathVariable ("id") int id, Model model){ //id - обрати внимание, целое число
        //получим одного человека из ДАО и передадим его на отображение
        model.addAttribute("person", personDao.show(id));
        return ("people/show");
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());
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

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDao.show(id));
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
