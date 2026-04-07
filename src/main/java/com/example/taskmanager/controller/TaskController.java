package com.example.taskmanager.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {

    @GetMapping("/hello")
    public String hello(){
        return "Task Manager API running! Part 2";
    }

    @GetMapping("/greet/{name}")
    public String greet(@PathVariable String name){
        return "Hello " + name;
    }

    @GetMapping("/sum")
    public int sum(@RequestParam int a, @RequestParam int b){
        return a + b;
    }

    private List<Task> tasks = new ArrayList<>();

    @PostMapping("/tasks")
    public Task addTask(@RequestBody Task task){
        tasks.add(task);
        return task;
    }

    @GetMapping("/tasks")
    public List<Task> getTasks(){
        return tasks;
    }

    @DeleteMapping("tasks/{id}")
    public void deleteTask(@PathVariable int id){
        tasks.removeIf(task -> task.getId() == id);
    }

    @PutMapping("tasks/{id}")
    public Task UpdateTask(@PathVariable int id, @RequestBody Task updatedTask){
        for (Task task : tasks){
            if (task.getId() == id){
                task.setTitle(updatedTask.getTitle());
                return task;
            }
        }

        return null;
    }
}
