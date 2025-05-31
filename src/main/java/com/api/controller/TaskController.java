package com.api.controller;

import com.api.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.api.repository.TaskRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller for managing tasks. */
@RestController
@RequestMapping("/tasks")
public class TaskController {

  private static final Logger log = LoggerFactory.getLogger(TaskController.class);
  private final TaskRepository taskRepository;

  /**
   * Constructs a TaskController with the given TaskRepository.
   *
   * @param taskRepository The repository for task data.
   */
  @Autowired // Optional if only one constructor
  public TaskController(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  /**
   * Creates a new task.
   *
   * @param task The task to create.
   * @return The created task with HTTP status 201, or 500 on error.
   */
  @PostMapping
  public ResponseEntity<Task> createTask(@RequestBody Task task) {
    log.info("Creating new task with name: {}", task.getName());
    try {
      Task newTask =
          taskRepository.save(new Task(task.getName(), task.getDescription(), task.isCompleted()));
      log.info("Task created successfully with ID: {}", newTask.getId());
      return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    } catch (Exception e) {
      log.error("Error creating task: {}", task.getName(), e);
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Retrieves all tasks.
   *
   * @return A list of all tasks with HTTP status 200, NO_CONTENT if none, or 500 on error.
   */
  @GetMapping
  public ResponseEntity<List<Task>> getAllTasks() {
    log.info("Attempting to retrieve all tasks");
    try {
      List<Task> tasks = taskRepository.findAll();
      if (tasks.isEmpty()) {
        log.info("No tasks found.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      log.info("Retrieved {} tasks.", tasks.size());
      return new ResponseEntity<>(tasks, HttpStatus.OK);
    } catch (Exception e) {
      log.error("Error retrieving all tasks", e);
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Retrieves a task by its ID.
   *
   * @param id The ID of the task to retrieve.
   * @return The task with HTTP status 200, NOT_FOUND if not present.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Task> getTaskById(@PathVariable("id") long id) {
    log.info("Attempting to retrieve task with ID: {}", id);
    Optional<Task> taskData = taskRepository.findById(id);

    if (taskData.isPresent()) {
      log.info("Task found with ID: {}", id);
      return new ResponseEntity<>(taskData.get(), HttpStatus.OK);
    } else {
      log.warn("Task not found with ID: {}", id);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  /**
   * Updates an existing task.
   *
   * @param id The ID of the task to update.
   * @param task The task data to update.
   * @return The updated task with HTTP status 200, NOT_FOUND if not present, or 500 on error.
   */
  @PutMapping("/{id}")
  public ResponseEntity<Task> updateTask(@PathVariable("id") long id, @RequestBody Task task) {
    log.info("Attempting to update task with ID: {}", id);
    Optional<Task> taskData = taskRepository.findById(id);

    if (taskData.isPresent()) {
      Task existingTask = taskData.get();
      existingTask.setName(task.getName());
      existingTask.setDescription(task.getDescription());
      existingTask.setCompleted(task.isCompleted());
      try {
        taskRepository.save(existingTask);
        log.info("Task updated successfully with ID: {}", existingTask.getId());
        return new ResponseEntity<>(existingTask, HttpStatus.OK);
      } catch (Exception e) {
        log.error("Error updating task with ID: {}", id, e);
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    } else {
      log.warn("Task not found for update with ID: {}", id);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  /**
   * Deletes a task by its ID.
   *
   * @param id The ID of the task to delete.
   * @return HTTP status NO_CONTENT on success, or 500 on error.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteTask(@PathVariable("id") long id) {
    log.info("Attempting to delete task with ID: {}", id);
    try {
      taskRepository.deleteById(id);
      log.info("Task deleted successfully with ID: {}", id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      log.error("Error deleting task with ID: {}", id, e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Deletes all tasks.
   *
   * @return HTTP status NO_CONTENT on success, or 500 on error.
   */
  @DeleteMapping
  public ResponseEntity<HttpStatus> deleteAllTasks() {
    log.info("Attempting to delete all tasks");
    try {
      taskRepository.deleteAll();
      log.info("All tasks deleted successfully.");
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      log.error("Error deleting all tasks", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
