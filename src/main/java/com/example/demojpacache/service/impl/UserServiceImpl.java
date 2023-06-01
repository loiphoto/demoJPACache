package com.example.demojpacache.service.impl;

import com.example.demojpacache.Entity.Role;
import com.example.demojpacache.Entity.User;
import com.example.demojpacache.dto.request.CreateUserRequest;
import com.example.demojpacache.exception.RoleNotFoundException;
import com.example.demojpacache.exception.UserNotFoundException;
import com.example.demojpacache.repository.RoleRepository;
import com.example.demojpacache.repository.UserRepository;
import com.example.demojpacache.service.base.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private ModelMapper mapper;

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @CacheEvict("user")
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Cacheable("user")
    @Override
    public User findById(Long id) throws UserNotFoundException {
//        simulateSlowService();
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not found"));
    }
//  Test

    @CacheEvict(value = "user", allEntries = true)
    @Override
    public void deleteAllUser() {
        userRepository.deleteAll();
    }

    @CachePut(value = "user", key = "#id")
    @Override
    public User updateUser(Long id, User user) {
//        simulateSlowService();
        user.setId(id);
        return userRepository.save(user);
    }

    public void createUserWithRole(CreateUserRequest createUserRequest) throws RoleNotFoundException {
        User user = mapper.map(createUserRequest, User.class);
        Role role = roleRepository.findById(createUserRequest.getRoleId()).orElseThrow(() -> new RoleNotFoundException("Role not found!"));
        user.setRole(role);
        userRepository.save(user);
    }

    private void simulateSlowService() {
        try {
            System.out.println("~~Slow~~");
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

}
