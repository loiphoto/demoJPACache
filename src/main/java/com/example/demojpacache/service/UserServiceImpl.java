package com.example.demojpacache.service;

import com.example.demojpacache.Entity.User;
import com.example.demojpacache.exception.UserNotFoundException;
import com.example.demojpacache.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

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
        simulateSlowService();
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not found"));
    }

    @CacheEvict(value = "user", allEntries = true)
    @Override
    public void deleteAllUser() {
        userRepository.deleteAll();
    }

    @CachePut(value = "user", key = "#id")
    @Override
    public User updateUser(Long id, User user) {
        simulateSlowService();
        user.setId(id);
        return userRepository.save(user);
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
