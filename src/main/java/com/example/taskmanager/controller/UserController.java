package com.example.taskmanager.controller;

import com.example.taskmanager.model.User;
import com.example.taskmanager.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
       this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping("/{username}")
    public Optional<User> findUserByUsername(@PathVariable String username){
        return userService.findUserByUsername(username);
    }


}
