package com.TaskManager.TaskManager.Controller;

import com.TaskManager.TaskManager.Dto.ProjectRequestDto;
import com.TaskManager.TaskManager.Dto.ProjectResponseDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/projects")
public interface ProjectController {

    @PostMapping("/add")
    ProjectResponseDto create(@Valid @RequestBody ProjectRequestDto request);

    @GetMapping("/get-all")
    List<ProjectResponseDto> getAll();

    @GetMapping("/get-by/{id}")
    ProjectResponseDto get(@PathVariable Long id);

    @PutMapping("/update/{id}")
    ProjectResponseDto update(@PathVariable Long id,
                              @RequestBody ProjectRequestDto request);

    @PostMapping("/add/{id}/members/{userId}")
    ProjectResponseDto addMember(@PathVariable Long id,
                                 @PathVariable Long userId);

    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Long id);
}