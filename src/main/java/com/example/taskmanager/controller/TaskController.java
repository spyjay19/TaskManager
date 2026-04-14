package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository repo;

    public TaskController(TaskRepository repo){
        this.repo = repo;
    }

    @PostMapping("")
    public Task addTask(@RequestBody Task task){
        return repo.save(task);
    }

    @GetMapping("")
    public List<Task> getTasks(){
        return repo.findAll();
    }

    @DeleteMapping("{id}")
    public void deleteTask(@PathVariable int id){
        repo.deleteById(id);
    }

    @PutMapping("/{id}")
    public Task UpdateTask(@PathVariable int id, @RequestBody Task updatedTask){
        Task task = repo.findById(id).orElse(null);
        if (task != null){
            return repo.save(updatedTask);
        }

        return null;
    }

    @PutMapping("/{id}/complete")
    public Task markComplete(@PathVariable int id){
        Task task = repo.findById(id).orElse(null);
        if (task != null){
            task.setCompleted(true);
            return repo.save(task);
        }

        return null;
    }

    @GetMapping("/completed")
    public List<Task> getCompleted(){
        return repo.findAll().stream().filter(Task:: isCompleted).toList();
    }

    @GetMapping("/incomplete")
    public List<Task> getIncomplete(){
        return repo.findAll().stream().filter(Task -> !Task.isCompleted()).toList();
    }
}
