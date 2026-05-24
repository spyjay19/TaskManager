package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task CreateTaskForUser(Long userId, Task task){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
            task.setUser(user);

            return taskRepository.save(task);
    }

    public List<Task> GetAllTasks(){
        return taskRepository.findAll();
    }

    public Optional<Task> GetTaskByID(int id){
        return taskRepository.findById(id);
    }

    public List<Task> getTasksByUser(Long userId){
        return taskRepository.findByUserId(userId);
    }

    public void DeleteTask(int id){
        taskRepository.deleteById(id);
    }

    public Task Save(Task task){
        return taskRepository.save(task);
    }
}
