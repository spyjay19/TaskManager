package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import jakarta.validation.Valid;
import com.example.taskmanager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service){
        this.service = service;
    }

    @PostMapping("")
    public Task AddTask(@Valid @RequestBody Task task){
        return service.CreateTask(task);
    }

    @GetMapping("")
    public List<Task> getTasks(){
        return service.GetAllTasks();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable int id){
        if (service.GetTaskByID(id).isPresent()){
            service.DeleteTask(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> UpdateTask(@PathVariable int id, @RequestBody Task updatedTask){

        return service.GetTaskByID(id)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setCompleted(updatedTask.isCompleted());
                    return ResponseEntity.ok(service.Save(task));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Task> markComplete(@PathVariable int id){

        return service.GetTaskByID(id)
                .map(task -> {
                    task.setCompleted(true);
                    return ResponseEntity.ok(service.Save(task));
                })

                .orElse(ResponseEntity.notFound().build());
    }
}
