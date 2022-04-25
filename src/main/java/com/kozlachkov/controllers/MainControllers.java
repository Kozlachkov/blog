package com.kozlachkov.controllers;

import com.kozlachkov.dao.PersonDao;
import com.kozlachkov.models.Person;
import com.kozlachkov.models.WebPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;
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
    public String showBlog (@PathVariable ("id") int id, ModelMap modelMap){
        int qty1 = modelMap.size();
        modelMap.addAttribute("person", personDao.getPersonById(id));
        modelMap.addAttribute("userDB", personDao.getUsrById(id));
        modelMap.addAttribute("webPosts", personDao.getAllPosts(id));
        return ("/people/blog");
    }

    @GetMapping("/{id}/createPost") //форма написания поста
    public String createUserPost(@PathVariable("id") int id, ModelMap modelMap) {
        modelMap.addAttribute("person", personDao.getPersonById(id));
        modelMap.addAttribute("userDB", personDao.getUsrById(id));
        modelMap.addAttribute("webPost", new WebPost());
        return "/people/createPost";
    }

    @PostMapping("/{id}") //создаём пост
    public String postUserPost(@ModelAttribute("webPost") @Valid WebPost webPost, BindingResult bindingResult,
                               @PathVariable("id") int id,
                               Map<String, Object> model) {
        String str1 = "redirect:/people/"+id+"/createPost";
        String str2 = "redirect:/people/"+id;
        String str3 = "/people/"+id+"/edit";
        if (bindingResult.hasErrors()) {
            model.put("message", "Поля не могут быть пустыми. Заголовок не длиннее 50 символов");
            return str3;
        }
        webPost.setId(id);
        int num_post;
        if (personDao.blogTableEmpty(id))
            num_post = personDao.getMaxNoteFromUsr(id).getId_note() +1;
        else num_post=0;
        webPost.setId_note(num_post);
        webPost.setData_pub(new Date());
        personDao.recordNote(webPost);
        return  str2; //"people/test";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDao.getPersonById(id));
        return "/people/edit";
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

}

