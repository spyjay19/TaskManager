package com.example.taskmanager.dto;

public class LoginResponse {
    private Long id;
    private String username;

    public LoginResponse(){

    }

    public LoginResponse(Long id, String username){
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }
}
