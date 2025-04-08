package org.example.repository;

import org.example.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Поиск уведомлений по ID собаки
    List<Notification> findByDogId(Long dogId);

    // Поиск уведомлений по статусу
    List<Notification> findByStatus(String status);
}
