package com.TaskManager.TaskManager.Service.Impl;

import com.TaskManager.TaskManager.Dto.*;
import com.TaskManager.TaskManager.Entity.*;
import com.TaskManager.TaskManager.Enum.Role;
import com.TaskManager.TaskManager.Enum.TaskPriority;
import com.TaskManager.TaskManager.Enum.TaskStatus;
import com.TaskManager.TaskManager.Exceptions.BadRequestException;
import com.TaskManager.TaskManager.Exceptions.ForbiddenException;
import com.TaskManager.TaskManager.Exceptions.ResourceNotFoundException;
import com.TaskManager.TaskManager.Mapper.TaskMapper;
import com.TaskManager.TaskManager.Repository.TaskRepository;
import com.TaskManager.TaskManager.Service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ProjectService projectService;
    private final ActivityLogService activityLogService;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskResponseDto createTask(TaskRequestDto request) {

        AppUser currentUser = userService.getCurrentUser();

        Project project = projectService.getProjectEntity(request.getProjectId());
        projectService.ensureCanManageProject(project, currentUser);

        AppUser assignedUser = userService.getUserEntity(request.getAssignedToId());

        if (!projectService.isMember(project, assignedUser)) {
            throw new BadRequestException("Assigned user must be project member");
        }

        Task task = taskMapper.toEntity(request);
        task.setTitle(request.getTitle().trim());
        task.setStatus(request.getStatus() == null ? TaskStatus.TODO : request.getStatus());
        task.setProject(project);
        task.setAssignedTo(assignedUser);

        Task saved = taskRepository.save(task);

        activityLogService.log("Task created: " + saved.getTitle(), currentUser);
        activityLogService.log("Assigned to: " + assignedUser.getEmail(), currentUser);

        return taskMapper.toResponseDto(saved);
    }

    @Override
    public Page<TaskResponseDto> getTasks(TaskStatus status,
                                          TaskPriority priority,
                                          Long assignedUserId,
                                          Pageable pageable) {

        AppUser currentUser = userService.getCurrentUser();

        Specification<Task> spec = (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (status != null)
                predicates.add(cb.equal(root.get("status"), status));

            if (priority != null)
                predicates.add(cb.equal(root.get("priority"), priority));

            if (currentUser.getRole() == Role.ADMIN) {
                if (assignedUserId != null)
                    predicates.add(cb.equal(root.get("assignedTo").get("id"), assignedUserId));
            } else {
                predicates.add(cb.equal(root.get("assignedTo").get("id"), currentUser.getId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return taskRepository.findAll(spec, pageable)
                .map(taskMapper::toResponseDto);
    }

    @Override
    public TaskResponseDto getTask(Long taskId) {

        Task task = getTaskEntity(taskId);
        AppUser currentUser = userService.getCurrentUser();

        if (currentUser.getRole() != Role.ADMIN &&
                !task.getAssignedTo().getId().equals(currentUser.getId())) {
            throw new ForbiddenException("Access denied");
        }

        return taskMapper.toResponseDto(task);
    }

    @Override
    @Transactional
    public TaskResponseDto updateTask(Long taskId, TaskUpdateRequestDto request) {

        Task task = getTaskEntity(taskId);
        AppUser currentUser = userService.getCurrentUser();

        projectService.ensureCanManageProject(task.getProject(), currentUser);

        TaskStatus oldStatus = task.getStatus();

        taskMapper.updateEntity(request, task);

        if (StringUtils.hasText(request.getTitle())) {
            task.setTitle(request.getTitle().trim());
        }

        if (request.getAssignedToId() != null) {

            AppUser newUser = userService.getUserEntity(request.getAssignedToId());

            if (!projectService.isMember(task.getProject(), newUser)) {
                throw new BadRequestException("User must be project member");
            }

            task.setAssignedTo(newUser);
        }

        if (request.getStatus() != null && request.getStatus() != oldStatus) {
            activityLogService.log("Status updated: " + task.getTitle(), currentUser);
        }

        return taskMapper.toResponseDto(taskRepository.save(task));
    }

    @Override
    @Transactional
    public TaskResponseDto updateTaskStatus(Long taskId, TaskStatusUpdateRequestDto request) {

        Task task = getTaskEntity(taskId);
        AppUser currentUser = userService.getCurrentUser();

        boolean isAdmin = currentUser.getRole() == Role.ADMIN;
        boolean isAssigned = task.getAssignedTo().getId().equals(currentUser.getId());

        if (!isAdmin && !isAssigned) {
            throw new ForbiddenException("Not allowed");
        }

        task.setStatus(request.getStatus());

        return taskMapper.toResponseDto(taskRepository.save(task));
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId) {

        Task task = getTaskEntity(taskId);

        projectService.ensureCanManageProject(
                task.getProject(),
                userService.getCurrentUser()
        );

        taskRepository.delete(task);
    }

    @Override
    public List<TaskResponseDto> findOverdueTasksForCurrentUser() {

        AppUser currentUser = userService.getCurrentUser();

        return taskRepository.findAll().stream()
                .filter(task ->
                        task.getDeadline() != null &&
                                task.getDeadline().isBefore(LocalDate.now()) &&
                                task.getStatus() != TaskStatus.DONE &&
                                (currentUser.getRole() == Role.ADMIN ||
                                        (task.getAssignedTo() != null &&
                                                task.getAssignedTo().getId().equals(currentUser.getId())))
                )
                .map(taskMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public long countTasksForCurrentUser(TaskStatus status, boolean overdueOnly) {

        AppUser currentUser = userService.getCurrentUser();

        return taskRepository.findAll().stream()

                // ✅ current user filter
                .filter(task ->
                        currentUser.getRole() == Role.ADMIN ||
                                (task.getAssignedTo() != null &&
                                        task.getAssignedTo().getId().equals(currentUser.getId()))
                )

                // ✅ status filter
                .filter(task -> status == null || task.getStatus() == status)

                // ✅ overdue filter
                .filter(task -> {
                    if (!overdueOnly) return true;

                    return task.getDeadline() != null &&
                            task.getDeadline().isBefore(LocalDate.now()) &&
                            task.getStatus() != TaskStatus.DONE;
                })

                .count();
    }

    private Task getTaskEntity(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }
}