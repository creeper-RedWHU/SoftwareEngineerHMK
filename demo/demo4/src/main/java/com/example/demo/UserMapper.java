package com.example.demo;


import java.util.List;

public interface UserMapper {
    List<User> findAll();
    void insertUser(User user);
    User find(long id);
    void del(long id);
}
