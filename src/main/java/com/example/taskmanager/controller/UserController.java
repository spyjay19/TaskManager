package com.example.taskmanager.controller;

import com.example.taskmanager.dto.LoginRequest;
import com.example.taskmanager.dto.LoginResponse;
import com.example.taskmanager.model.User;
import com.example.taskmanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginAttempt(@RequestBody LoginRequest login){
        Optional<User> user = userService.findUserByUsername(login.getUsername());

        if (user.isPresent()){
            User foundUser = user.get();

            if (login.getPassword().equals(foundUser.getPassword())){
                LoginResponse response = new LoginResponse();

                response.setId(foundUser.getId());
                response.setUsername(foundUser.getUsername());


                return ResponseEntity.ok(response);
            }
            else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
