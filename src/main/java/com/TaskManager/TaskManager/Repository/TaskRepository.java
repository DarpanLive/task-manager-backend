package com.TaskManager.TaskManager.Repository;

import com.TaskManager.TaskManager.Entity.Task;
import com.TaskManager.TaskManager.Enum.TaskPriority;
import com.TaskManager.TaskManager.Enum.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    long countByStatus(TaskStatus status);

    long countByStatusNot(TaskStatus status);

    long countByDeadlineBeforeAndStatusNot(LocalDate date, TaskStatus status);

    long countByAssignedTo_Id(Long userId);

    long countByAssignedTo_IdAndStatus(Long userId, TaskStatus status);

    long countByAssignedTo_IdAndStatusNot(Long userId, TaskStatus status);

    long countByAssignedTo_IdAndDeadlineBeforeAndStatusNot(Long userId, LocalDate date, TaskStatus status);

    long countByPriority(TaskPriority priority);
}