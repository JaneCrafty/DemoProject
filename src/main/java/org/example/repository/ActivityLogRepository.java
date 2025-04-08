package org.example.repository;

import org.example.model.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    // Поиск логов активности по ID собаки
    List<ActivityLog> findByDogId(Long dogId);

    // Пример пользовательского запроса для поиска активности по типу
    List<ActivityLog> findByActionType(String actionType);
}
