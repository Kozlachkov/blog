package com.kozlachkov.services;


import com.kozlachkov.dao.PersonDao;
import com.kozlachkov.dao.UsrDao;
import com.kozlachkov.dao.UsrMapper;
import com.kozlachkov.models.UserDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

import static com.kozlachkov.dao.PersonDao.*;

public class MyUserDetails implements UserDetails {
    private String username;
    private UserDB userDB;
    public MyUserDetails(String username) {
        this.username = username;
        this.userDB = UsrDao.getUsrByName(username);
    }
    public MyUserDetails() {  }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }
    @Override
    public String getPassword() {
        return userDB.getPassword();
    }
    @Override
    public String getUsername() { return username;    }
    @Override
    public boolean isAccountNonExpired() {  return true;    }
    @Override
    public boolean isAccountNonLocked() {  return true;    }
    @Override
    public boolean isCredentialsNonExpired() {  return true;    }
    @Override
    public boolean isEnabled() {  return true;   }
}


