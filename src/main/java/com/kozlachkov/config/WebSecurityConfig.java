package com.kozlachkov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity //создание объекта WebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/people").permitAll() //на эту страницу всем доступно
                    .anyRequest().authenticated() //остальные страницы только по авторизации
                    .and()
                .formLogin()//включили форму логина
                    .loginPage("/people/login")//указали где форма логина находится
                    .permitAll()
                    .and()
                .logout()//включили логаут
                    .permitAll(); //разрешили всем
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("u")
                        .password("u")
                        .roles("USER")
                        .build();
        //автоматом создается менеджер, кот. это обслуживает
        return new InMemoryUserDetailsManager(user);
    }
}

