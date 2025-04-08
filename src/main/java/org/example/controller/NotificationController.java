package org.example.controller;

import org.example.dto.DogInteractionRequest;
import org.example.service.NotificationService;
import org.springframework.web.bind.annotation.*;
import org.example.dto.NotificationDto;
import java.util.List;

@RestController
@RequestMapping("/api/notify")
public class NotificationController {
    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    // Собака зовёт другую собаку
    @PostMapping
    public void send(@RequestBody DogInteractionRequest request) {
        service.sendInteraction(request);
    }

    // Получить уведомления для текущего пользователя (собак, которых он владелец)
    @GetMapping
    public List<NotificationDto> getAll() {
        return service.getNotificationsForCurrentUser();
    }

    // Принять приглашение
    @PostMapping("/{id}/accept")
    public void accept(@PathVariable Long id, @RequestParam String actionType) {
        service.accept(id, actionType);
    }

    // Отклонить приглашение
    @PostMapping("/{id}/reject")
    public void reject(@PathVariable Long id) {
        service.reject(id);
    }
}
