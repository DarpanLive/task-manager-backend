package com.TaskManager.TaskManager.Dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ProjectRequestDto {

    @NotBlank
    private String name;

    private String description;

    private Set<Long> memberIds = new HashSet<>();
}