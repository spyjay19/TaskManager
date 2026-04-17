package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    public Task CreateTask(Task task){
        return repo.save(task);
    }

    public List<Task> GetAllTasks(){
        return repo.findAll();
    }

    public Optional<Task> GetTaskByID(int id){
        return repo.findById(id);
    }

    public void DeleteTask(int id){
        repo.deleteById(id);
    }

    public Task Save(Task task){
        return repo.save(task);
    }
}
