package com.example.demojpacache.service;

import com.example.demojpacache.Entity.User;

import java.util.List;

public interface UserService {
    void createUser(User user);

    List<User> findAllUser();

    void deleteUser(Long id);
}
