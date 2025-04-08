package org.example.service;

import org.example.dto.ActivityLogDto;
import org.example.dto.DogProfileDto;
import org.example.model.ActivityLog;
import org.example.model.Dog;
import org.example.model.Owner;
import org.example.repository.ActivityLogRepository;
import org.example.repository.DogProfileRepository;
import org.example.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityLogService {

    private final ActivityLogRepository logRepo;
    private final DogProfileRepository dogRepo;
    private final OwnerRepository ownerRepo;

    @Autowired
    public ActivityLogService(ActivityLogRepository logRepo,
                              DogProfileRepository dogRepo,
                              OwnerRepository ownerRepo) {
        this.logRepo = logRepo;
        this.dogRepo = dogRepo;
        this.ownerRepo = ownerRepo;
    }

    public void log(String type, Dog actor, Dog target, String action) {
        ActivityLogDto log = new ActivityLogDto();
        log.setType(type); // "INVITE" или "ACCEPT"
        log.setActorDog(actor);
        log.setTargetDog(target);
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());
        logRepo.save(log);
    }

    public List<ActivityLogDto> getLogsForDog(Long dogId) {
        Dog dog = dogRepo.findById(dogId).orElseThrow();
        Owner current = getCurrentOwner();

        if (!dog.getOwner().getId().equals(current.getId())) {
            throw new RuntimeException("Access denied: not your dog");
        }

        return logRepo.findAll().stream()
                .filter(log -> log.getActorDog().getId().equals(dogId)
                        || (log.getTargetDog() != null && log.getTargetDog().getId().equals(dogId)))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ActivityLogDto toDto(ActivityLog log) {
        ActivityLogDto dto = new ActivityLogDto();
        dto.id = log.getId();
        dto.type = log.getActionType();
        dto.actorDogName = log.getActorDog().getName();
        dto.targetDogName = log.getTargetDog() != null ? log.getTargetDog().getName() : null;
        dto.action = log.getActionType();
        dto.timestamp = log.getTimestamp();
        return dto;
    }

    private Owner getCurrentOwner() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return ownerRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("Dog not found"));
    }
}

