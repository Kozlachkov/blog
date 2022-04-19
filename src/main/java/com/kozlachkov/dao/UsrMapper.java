package com.kozlachkov.dao;

import com.kozlachkov.models.Person;
import com.kozlachkov.models.Roles;
import com.kozlachkov.models.UserDB;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsrMapper implements RowMapper<UserDB> {
    @Override
    public UserDB mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        UserDB userDB = new UserDB();
        userDB.setId(resultSet.getInt("id"));
        userDB.setUsername(resultSet.getString("username"));
        userDB.setPassword(resultSet.getString("password"));
        userDB.setCheck_pass(resultSet.getString("check_pass"));
        userDB.setRole(Roles.valueOf(resultSet.getString("role")));
        userDB.setActive(resultSet.getBoolean("active"));
        return userDB;
    }
}
