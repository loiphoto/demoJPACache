package com.example.demojpacache;

import com.example.demojpacache.Entity.User;
import com.example.demojpacache.exception.UserNotFoundException;
import com.example.demojpacache.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class HomeController {

    private UserService userService;

    @PostMapping("/users/create")
    public ResponseEntity createUser(@RequestBody User user) {
        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users")
    @ResponseBody
    public List<User> findById() {
        return userService.findAllUser();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    @ResponseBody
    public User findById(@PathVariable("id") Long id) throws UserNotFoundException {
        log.info("Get User by ID -------------- {}", id);
        return userService.findById(id);
    }

    @DeleteMapping("/users")
    public ResponseEntity deleteAllUsers(){
        userService.deleteAllUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") Long id){
        return userService.updateUser(id, user);
    }
}
