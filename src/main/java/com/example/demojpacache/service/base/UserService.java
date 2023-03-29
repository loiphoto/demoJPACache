package com.example.demojpacache.service.base;

import com.example.demojpacache.Entity.User;
import com.example.demojpacache.dto.request.CreateUserRequest;
import com.example.demojpacache.exception.RoleNotFoundException;
import com.example.demojpacache.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    void createUser(User user);

    List<User> findAllUser();

    void deleteUser(Long id);

    User findById(Long id) throws UserNotFoundException;

    void deleteAllUser();

    User updateUser(Long id, User user);

    void createUserWithRole(CreateUserRequest createUserRequest) throws RoleNotFoundException;
}
