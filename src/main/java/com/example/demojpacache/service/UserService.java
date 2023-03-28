package com.example.demojpacache.service;

import com.example.demojpacache.Entity.User;
import com.example.demojpacache.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    void createUser(User user);

    List<User> findAllUser();

    void deleteUser(Long id);

    User findById(Long id) throws UserNotFoundException;

    void deleteAllUser();

    User updateUser(Long id, User user);
}
