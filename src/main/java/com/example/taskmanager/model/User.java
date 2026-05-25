package com.example.taskmanager.model;

import com.example.taskmanager.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.Entity;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Task> tasks;

    public User(){
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long Id){
        this.id = Id;
    }

}
