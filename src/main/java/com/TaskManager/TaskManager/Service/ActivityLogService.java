package com.TaskManager.TaskManager.Service;

import com.TaskManager.TaskManager.Dto.ActivityLogResponseDto;
import com.TaskManager.TaskManager.Entity.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActivityLogService {

    void log(String action, AppUser user);

    Page<ActivityLogResponseDto> getLogs(Pageable pageable);
}