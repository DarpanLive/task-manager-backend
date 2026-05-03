package com.TaskManager.TaskManager.Service.Impl;

import com.TaskManager.TaskManager.Dto.ProjectRequestDto;
import com.TaskManager.TaskManager.Dto.ProjectResponseDto;
import com.TaskManager.TaskManager.Entity.Project;
import com.TaskManager.TaskManager.Entity.AppUser;
import com.TaskManager.TaskManager.Enum.Role;
import com.TaskManager.TaskManager.Exceptions.ForbiddenException;
import com.TaskManager.TaskManager.Exceptions.ResourceNotFoundException;
import com.TaskManager.TaskManager.Mapper.ProjectMapper;
import com.TaskManager.TaskManager.Repository.ProjectRepository;
import com.TaskManager.TaskManager.Service.ProjectService;
import com.TaskManager.TaskManager.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final ProjectMapper projectMapper;

    @Override
    @Transactional
    public ProjectResponseDto createProject(ProjectRequestDto request) {

        AppUser currentUser = userService.getCurrentUser();

        Set<AppUser> members = new HashSet<>();
        members.add(currentUser);

        if (request.getMemberIds() != null) {
            request.getMemberIds().forEach(id ->
                    members.add(userService.getUserEntity(id))
            );
        }

        Project project = projectMapper.toEntity(request);
        project.setName(request.getName().trim());
        project.setCreatedBy(currentUser);
        project.setMembers(members);

        return projectMapper.toResponseDto(projectRepository.save(project));
    }

    @Override
    public List<ProjectResponseDto> getProjects() {

        AppUser currentUser = userService.getCurrentUser();

        List<Project> projects = currentUser.getRole() == Role.ADMIN
                ? projectRepository.findAll()
                : projectRepository.findByMembers_Id(currentUser.getId());

        return projects.stream()
                .map(projectMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponseDto getProject(Long projectId) {

        Project project = getProjectEntity(projectId);
        ensureCanViewProject(project, userService.getCurrentUser());

        return projectMapper.toResponseDto(project);
    }

    @Override
    @Transactional
    public ProjectResponseDto updateProject(Long projectId, ProjectRequestDto request) {

        Project project = getProjectEntity(projectId);
        AppUser currentUser = userService.getCurrentUser();

        ensureCanManageProject(project, currentUser);

        // ✅ NAME UPDATE (optional)
        if (request.getName() != null) {
            project.setName(request.getName().trim());
        }

        // ✅ DESCRIPTION UPDATE
        if (request.getDescription() != null) {
            project.setDescription(request.getDescription());
        }

        // ✅ MEMBERS ADD (existing + new)
        if (request.getMemberIds() != null) {
            for (Long id : request.getMemberIds()) {
                AppUser user = userService.getUserEntity(id);
                project.getMembers().add(user);
            }
        }

        return projectMapper.toResponseDto(projectRepository.save(project));
    }

    @Override
    @Transactional
    public ProjectResponseDto addMember(Long projectId, Long userId) {

        Project project = getProjectEntity(projectId);
        ensureCanManageProject(project, userService.getCurrentUser());

        project.getMembers().add(userService.getUserEntity(userId));

        return projectMapper.toResponseDto(projectRepository.save(project));
    }

    @Override
    @Transactional
    public void deleteProject(Long projectId) {

        Project project = getProjectEntity(projectId);
        ensureCanManageProject(project, userService.getCurrentUser());

        projectRepository.delete(project);
    }

    @Override
    public Project getProjectEntity(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
    }

    @Override
    public void ensureCanViewProject(Project project, AppUser user) {

        if (user.getRole() == Role.ADMIN ||
                project.getCreatedBy().getId().equals(user.getId()) ||
                isMember(project, user)) {
            return;
        }

        throw new ForbiddenException("Access denied");
    }

    @Override
    public void ensureCanManageProject(Project project, AppUser user) {

        if (user.getRole() == Role.ADMIN ||
                project.getCreatedBy().getId().equals(user.getId())) {
            return;
        }

        throw new ForbiddenException("Only admin or creator allowed");
    }

    @Override
    public boolean isMember(Project project, AppUser user) {
        return project.getMembers().stream()
                .anyMatch(m -> m.getId().equals(user.getId()));
    }
}