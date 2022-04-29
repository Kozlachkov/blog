package com.kozlachkov.controllers;

import com.kozlachkov.dao.PersonDao;
import com.kozlachkov.models.Person;
import com.kozlachkov.models.UserDB;
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

    @PostMapping("/{id}/createPost") //форма написания поста
    public String createUserPost(@PathVariable("id") int id, ModelMap modelMap) {
        modelMap.addAttribute("person", personDao.getPersonById(id));
        modelMap.addAttribute("userDB", personDao.getUsrById(id));
        modelMap.addAttribute("webPost", new WebPost());
        return "/people/createPost";
    }

    @GetMapping("/{id}/createPost") //форма написания поста
    public String createUserPost2(@PathVariable("id") int id, ModelMap modelMap) {
        modelMap.addAttribute("person", personDao.getPersonById(id));
        modelMap.addAttribute("userDB", personDao.getUsrById(id));
        modelMap.addAttribute("webPost", new WebPost());
        return "/people/createPost";
    }

    @GetMapping("/{id}/edit") //форма редактирования person
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDao.getPersonById(id));
        return "/people/edit";
    }

    @PatchMapping("/{id}") // сохранение редактированного person
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if(bindingResult.hasErrors()) return "people/edit";
        personDao.update(id, person);
        return "redirect:/people";
    }

    @PostMapping("/{id}") //создаём пост
    public String postUserPost(@ModelAttribute("webPost") @Valid WebPost webPost, BindingResult bindingResult,
                               @PathVariable("id") int id,
                               ModelMap modelMap, Map<String, Object> model) {
        String str2 = "redirect:/people/"+id;
        String str3 = "/people/createPost/"+id;
        if (bindingResult.hasErrors()) {
            modelMap.addAttribute("person", personDao.getPersonById(id));
            modelMap.addAttribute("userDB", personDao.getUsrById(id));
            modelMap.addAttribute("webPost", new WebPost());
            model.put("message2", "Поля не могут быть пустыми. Заголовок не длиннее 50 символов");
            return "/people/createPost";
        }
        webPost.setId(id);
        int num_post;
        if (personDao.blogTableEmpty(id))
            num_post = personDao.getMaxNoteFromUsr(id)+1;
        else num_post=0;
        webPost.setId_note(num_post);
        webPost.setData_pub(new Date());
        personDao.recordNote(webPost);
        return  str2;
    }

        @DeleteMapping("/{id}")
        public String delete (@PathVariable("id") int id){
            personDao.delete(id);
            return "redirect:/people";
        }

        @GetMapping("/{id}/nik")
        public String edit2(ModelMap modelMap, @PathVariable("id") int id) {
            modelMap.addAttribute("person", personDao.getPersonById(id));
            modelMap.addAttribute("userDB", personDao.getUsrById(id));
            return "/people/edit2";
        }

        @PatchMapping("/{id}/nik")
        public String updateNik(@ModelAttribute("userDB") @Valid UserDB userDB,
                             BindingResult bindingResult,
                             @PathVariable("id") int id, Map<String, Object> model, ModelMap modelMap) {
            if(bindingResult.hasErrors())
                return "people/edit2";
            if (!userDB.getPassword().equals(userDB.getCheck_pass())) {
                modelMap.addAttribute("person", personDao.getPersonById(id));
                modelMap.addAttribute("userDB", personDao.getUsrById(id));
                model.put("message2", "Повторный пароль не совпадает с оригиналом");
                return "/people/edit2";
            }
            personDao.updateNik(id, userDB);
            return "redirect:/people";
        }

        @GetMapping("/{id}/{id_note}")
        public String editPost(ModelMap modelMap, @PathVariable("id") int id, @PathVariable("id_note") int id_note) {
            modelMap.addAttribute("webPost", personDao.getPostById(id,id_note));
            return "/people/editPost";
        }

        @PatchMapping("/{id}/{id_note}")
        public String updatePost(@ModelAttribute("webPost") @Valid WebPost webPost,
                             BindingResult bindingResult,
                             @PathVariable("id") int id, @PathVariable("id_note") int id_note) {
            if(bindingResult.hasErrors()) return "people/edit";
            personDao.updatePost(id, id_note, webPost);
            return "redirect:/people/{id}";
        }



}

