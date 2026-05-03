package com.TaskManager.TaskManager.Dto;



import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddProjectMemberRequestDto {

    @NotNull
    private Long userId;
}