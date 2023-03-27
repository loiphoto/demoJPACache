package com.example.demojpacache;

import com.example.demojpacache.Entity.User;
import com.example.demojpacache.repository.UserRepository;
import com.example.demojpacache.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<User> getAll() {
        return userService.findAllUser();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
