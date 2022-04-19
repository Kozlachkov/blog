package com.kozlachkov.dao;

import com.kozlachkov.models.Person;
import com.kozlachkov.models.Roles;
import com.kozlachkov.models.UserDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class PersonDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index (){
        return jdbcTemplate.query("SELECT* FROM person", new PersonMapper());
    }

    public UserDB createUser (UserDB userDB){
        int id1;
        UserDB userDB1 = getMaxIdFromUsr();
        if(userDB1==null) id1=0;
        else id1=userDB1.getId()+1;
        jdbcTemplate.update("INSERT INTO usr VALUE(?,?,?,?,?,?)",
                id1, userDB.getUsername(), userDB.getPassword(), userDB.getCheck_pass(), Roles.USER.name(), true);
        return getUsrById(id1);
    }

    public UserDB getMaxIdFromUsr (){
        return jdbcTemplate.query("SELECT * FROM usr WHERE id = ( SELECT MAX(id) FROM usr )", new UsrMapper())
                .stream().findFirst().orElse(null);
    }

    public UserDB getUsrByName (String name){
        return jdbcTemplate.query("SELECT* FROM usr WHERE username=?", new Object[]{name}, new UsrMapper())
                .stream().findFirst().orElse(null);
    }
    public UserDB getUsrById (int id){
        return jdbcTemplate.query("SELECT* FROM usr WHERE id=?", new Object[]{id}, new UsrMapper())
                .stream().findFirst().orElse(null);
    }

    public Person getPersonById (int id){
        return jdbcTemplate.query("SELECT* FROM person WHERE id=?", new Object[]{id}, new PersonMapper())
                .stream().findAny().orElse(null);
    }

    public void save(Person person, UserDB userDB){
         jdbcTemplate.update("INSERT INTO person VALUE(?,?,?,?,?)",
                 userDB.getId(), person.getName(), person.getAge(), person.getEmail(), true);
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET name=?, age=?, email=? WHERE id=?",
                updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), id);
    }

    public void delete (int id){
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }
}
