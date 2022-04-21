package com.kozlachkov.controllers;

import com.kozlachkov.dao.PersonDao;
import com.kozlachkov.models.WebPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/people")
public class MainControllers {

    private PersonDao personDao;
    @Autowired
    public MainControllers(PersonDao personDao) {
        this.personDao = personDao;
    }


    @GetMapping("/{id}") //отображение странички блога конкретного человека
    public String showBlog (@PathVariable ("id") int id, ModelMap modelMap1){ //id - обрати внимание, целое число
        //получим одного человека из ДАО и передадим его на отображение
        modelMap1.addAttribute("person", personDao.getPersonById(id));
        modelMap1.addAttribute("userDB", personDao.getUsrById(id));
        modelMap1.addAttribute("webPost", new WebPost());
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
    public String createUserPost(@PathVariable("id") int id, ModelMap modelMap) {
        modelMap.addAttribute("person", personDao.getPersonById(id));
        modelMap.addAttribute("userDB", personDao.getUsrById(id));
        modelMap.addAttribute("webPost", new WebPost());
        return "people/createPost";
    }

    @PostMapping("/{id}") //создаём пост
    public String postUserPost(@ModelAttribute("webPost") @Valid WebPost webPost, BindingResult bindingResult,
                               @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "people/{id}/createPost";
        webPost.setId_note(1);
        webPost.setData_pub(new Date());
        return  "redirect:/people/{id}"; //"people/test";
    }

}

