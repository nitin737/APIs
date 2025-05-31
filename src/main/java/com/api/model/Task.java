package com.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Represents a task entity. */
@Entity
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotBlank(message = "Name is mandatory")
  @Size(max = 100, message = "Name must be at most 100 characters")
  private String name;

  @Size(max = 255, message = "Description must be at most 255 characters")
  private String description;

  private boolean completed;

  // Constructors
  public Task() {}

  /**
   * Constructs a new Task.
   *
   * @param name The name of the task.
   * @param description The description of the task.
   * @param completed Whether the task is completed.
   */
  public Task(String name, String description, boolean completed) {
    this.name = name;
    this.description = description;
    this.completed = completed;
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  @Override
  public String toString() {
    return "Task{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", description='"
        + description
        + '\''
        + ", completed="
        + completed
        + '}';
  }
}
