package com.kozlachkov.dao;

import com.kozlachkov.models.UserDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UsrDao {
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public UsrDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static UserDB getUsrByName(String name){
        return jdbcTemplate.query("SELECT* FROM usr WHERE username=?", new Object[]{name}, new UsrMapper())
                .stream().findFirst().orElse(null);
    }

    public static UserDB getUsrById (int id){
        return jdbcTemplate.query("SELECT* FROM usr WHERE id=?", new Object[]{id}, new UsrMapper())
                .stream().findFirst().orElse(null);
    }
}
