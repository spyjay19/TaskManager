package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService service){
        this.taskService = service;
    }

    @PostMapping("/user/{userId}")
    public Task AddTask(@PathVariable Long userId, @RequestBody Task task){
        return taskService.CreateTaskForUser(userId, task);
    }

    @GetMapping("/user/{userId}")
    public List<Task> getTasksByUser(@PathVariable Long userId){
        return taskService.getTasksByUser(userId);
    }

    @GetMapping("")
    public List<Task> getTasks(){
        return taskService.GetAllTasks();
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable int id){
        if (taskService.GetTaskByID(id).isPresent()){
            taskService.DeleteTask(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Task> UpdateTask(@PathVariable int id, @RequestBody Task updatedTask){

        return taskService.GetTaskByID(id)
                .map(task -> {
                    if (!Objects.equals(updatedTask.getTitle(), "")){
                        task.setTitle(updatedTask.getTitle());
                    }

                    if (!Objects.equals(updatedTask.getDescription(), "")){
                        task.setDescription(updatedTask.getDescription());
                    }

                    task.setDueDate(updatedTask.getDueDate());

                    task.setCompleted(updatedTask.isCompleted());
                    return ResponseEntity.ok(taskService.Save(task));
                })
                .orElse(ResponseEntity.notFound().<Task>build());
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Task> markComplete(@PathVariable int id){

        return taskService.GetTaskByID(id)
                .map(task -> {
                    task.setCompleted(true);
                    return ResponseEntity.ok(taskService.Save(task));
                })

                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/incomplete")
    public ResponseEntity<Task> markIncomplete(@PathVariable int id){

        return taskService.GetTaskByID(id)
                .map(task -> {
                    task.setCompleted(false);
                    return ResponseEntity.ok(taskService.Save(task));
                })

                .orElse(ResponseEntity.notFound().build());
    }
}
