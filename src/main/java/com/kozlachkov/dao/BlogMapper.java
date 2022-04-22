package com.kozlachkov.dao;
import com.kozlachkov.models.Roles;
import com.kozlachkov.models.UserDB;
import com.kozlachkov.models.WebPost;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BlogMapper implements RowMapper<WebPost> {
    @Override
    public WebPost mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        WebPost webPost = new WebPost();
        webPost.setId(resultSet.getInt("id"));
        webPost.setId_note(resultSet.getInt("id_note"));
        webPost.setTitle(resultSet.getString("title"));
        webPost.setText(resultSet.getString("text"));
        webPost.setData_pub(resultSet.getDate("date"));
        return webPost;
    }
}
