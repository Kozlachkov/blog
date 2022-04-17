package com.kozlachkov.dao;

import com.kozlachkov.models.Person;
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
    private  static int usr_count = 0;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static Statement statement;

    public List<Person> index (){
        return jdbcTemplate.query("SELECT* FROM person", new PersonMapper());
    }

    public void createUser (UserDB userDB){
        jdbcTemplate.update("INSERT INTO usr VALUE(usr_count,?,?,?,true)",
                userDB.getUsername(), userDB.getPassword(), userDB.getCheck_pass());
        usr_count = usr_count+1;
    }

    public Person show (int id){
        return jdbcTemplate.query("SELECT* FROM person WHERE id=?", new Object[]{id}, new PersonMapper())
                .stream().findAny().orElse(null);
    }

    public void save(Person person){
         jdbcTemplate.update("INSERT INTO person VALUE(1,?,?,?)",
                 person.getName(), person.getAge(), person.getEmail());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET name=?, age=?, email=? WHERE id=?",
                updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), id);
    }

    public void delete (int id){
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }
}
