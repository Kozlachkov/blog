package com.kozlachkov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;
import java.util.Arrays;

@SpringBootApplication
@ComponentScan("com.kozlachkov")

public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private final ApplicationContext applicationContext;
    @Autowired
    private Environment env;

    @Autowired
    public Application(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean //фильтр для форм _method
    public FilterRegistrationBean HiddenHttpMethodFilter (){
        FilterRegistrationBean filterRegBean = new FilterRegistrationBean(new HiddenHttpMethodFilter());
        filterRegBean.setUrlPatterns(Arrays.asList("/*"));
        return filterRegBean;
    }

    @Bean //данные для БД
    public DataSource datasSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        return dataSource;
    }

    @Bean //шаблон с данными БД - лекция 27
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(datasSource());
    }
}
