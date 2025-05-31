package com.api.controller;

import com.api.model.Task;
import com.api.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository taskRepository;

    // Constructor injection for TaskRepository
    @Autowired // Optional if only one constructor
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Create a new task
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        try {
            Task newTask = taskRepository.save(new Task(task.getName(), task.getDescription(), task.isCompleted()));
            return new ResponseEntity<>(newTask, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all tasks
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        try {
            List<Task> tasks = taskRepository.findAll();
            if (tasks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get a task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") long id) {
        Optional<Task> taskData = taskRepository.findById(id);

        if (taskData.isPresent()) {
            return new ResponseEntity<>(taskData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update a task by ID
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") long id, @RequestBody Task task) {
        Optional<Task> taskData = taskRepository.findById(id);

        if (taskData.isPresent()) {
            Task existingTask = taskData.get();
            existingTask.setName(task.getName());
            existingTask.setDescription(task.getDescription());
            existingTask.setCompleted(task.isCompleted());
            try {
                return new ResponseEntity<>(taskRepository.save(existingTask), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a task by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable("id") long id) {
        try {
            taskRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete all tasks
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllTasks() {
        try {
            taskRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
