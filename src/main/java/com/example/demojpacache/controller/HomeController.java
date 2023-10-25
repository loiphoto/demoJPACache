package com.example.demojpacache.controller;

import com.example.demojpacache.Entity.Role;
import com.example.demojpacache.Entity.User;
import com.example.demojpacache.dto.request.CreateUserRequest;
import com.example.demojpacache.exception.RoleNotFoundException;
import com.example.demojpacache.exception.UserNotFoundException;
import com.example.demojpacache.rabbitMQ.RabbitMQProducer;
import com.example.demojpacache.service.base.RoleService;
import com.example.demojpacache.service.base.UserService;
import com.example.demojpacache.service.impl.RedisService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
public class HomeController {

    private UserService userService;

    private RoleService roleService;

    private RabbitMQProducer rabbitMQProducer;

    private RedisService redisService;

    @PostMapping("/users/create")
    @PreAuthorize("hasAuthority('user:create')")
    public ResponseEntity createUser(@RequestBody CreateUserRequest user) throws RoleNotFoundException {
        userService.createUserWithRole(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('user:read')")
    public List<User> findAllUser() {
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
    public ResponseEntity deleteAllUsers() {
        userService.deleteAllUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") Long id) {
        return userService.updateUser(id, user);
    }

    //---------------------Role----------------------//

    @PostMapping("/roles/create")
    public ResponseEntity createUser(@RequestBody Role role) {
        roleService.createRole(role);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/roles/{id}")
    @ResponseBody
    public Role findRoleById(@PathVariable("id") Long id) throws RoleNotFoundException {
        return roleService.findById(id);
    }

    @GetMapping("/roles/test")
    public ResponseEntity Test() throws UserNotFoundException {
        User user = userService.findById(12L);
//        Role role = user.getRole();
//        role.setDescription("miumiu");
        userService.createUser(user);
        System.out.println(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/rabbitMQ")
    public ResponseEntity rabbitMQ(@RequestParam("message") String message) {
        rabbitMQProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent to RabbitMQ ...");
    }

    @GetMapping("/redis")
    public ResponseEntity redis() {
        return ResponseEntity.ok("All key redis ..." + redisService.getValue("2"));
    }



}
