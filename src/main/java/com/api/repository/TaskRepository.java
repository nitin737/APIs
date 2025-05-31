package com.api.repository;

import com.api.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repository interface for {@link Task} entities. */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {}
