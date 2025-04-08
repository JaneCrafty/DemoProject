package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.dto.ActivityLogDto;
import org.example.service.ActivityLogService;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    public ActivityLogController(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    @GetMapping("/{dogId}")
    public List<ActivityLogDto> getLogs(@PathVariable Long dogId) {
        return activityLogService.getLogsForDog(dogId);
    }
}
