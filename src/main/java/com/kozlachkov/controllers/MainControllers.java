package com.kozlachkov.controllers;

import com.kozlachkov.dao.PersonDao;
import com.kozlachkov.models.Person;
import com.kozlachkov.models.Post;
import com.kozlachkov.models.UserDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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


    @GetMapping("/{id}") //отображение странички блога конкретного человека
    public String showBlog (@PathVariable ("id") int id, ModelMap modelMap){ //id - обрати внимание, целое число
        //получим одного человека из ДАО и передадим его на отображение
        modelMap.addAttribute("person", personDao.getPersonById(id));
        modelMap.addAttribute("userDB", personDao.getUsrById(id));
        return ("people/blog");
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDao.getPersonById(id));
        return "people/edit";
    }
/*
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
*/
    @GetMapping("/login")
    public String loginPage() {
        return "people/login";
    }

    @GetMapping("/{id}/createPost") //форма написания поста
    public String createUserPost(ModelMap modelMap, @PathVariable("id") int id) {
        modelMap.addAttribute("post1", new Post());
        modelMap.addAttribute("person", personDao.getPersonById(id));
        modelMap.addAttribute("userDB", personDao.getUsrById(id));
        return "people/createPost";
    }

    @PostMapping("/{id}") //создаём пост
    public String postUserPost(@ModelAttribute("post1") @Valid Post post, BindingResult bindingResult,
                               @PathVariable("id") int id) {
       /*modelMap.addAttribute("person", personDao.getPersonById(id));
        modelMap.addAttribute("userDB", personDao.getUsrById(id));*/
        //String str1 = "people/${id}";
        return "people/${id}";
    }

}

