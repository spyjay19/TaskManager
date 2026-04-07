package com.example.taskmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayController {

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }

    @PostMapping("/echo")
    public String echo(@RequestBody String message){
        return "You said: " + message;
    }
}
