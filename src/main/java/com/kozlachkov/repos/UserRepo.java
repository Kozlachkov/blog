package com.kozlachkov.repos;

import com.kozlachkov.models.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserDB, Long> {

    //метод который возвращает пользователя
    public UserDB fingByUsername (String username);
}
